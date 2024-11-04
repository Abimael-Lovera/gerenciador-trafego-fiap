package com.fiap.acidente.service;

import com.fiap.acidente.dto.AcidenteCreateDTO;
import com.fiap.acidente.dto.AcidenteUpdateDTO;
import com.fiap.acidente.dto.AcidenteViewDTO;
import com.fiap.acidente.dto.RelatorioAcidentePorDataDTO;
import com.fiap.acidente.exception.AcidenteNaoEncontradoException;
import com.fiap.acidente.model.Acidente;
import com.fiap.acidente.model.Gravidade;
import com.fiap.acidente.repository.AcidenteRepository;
import com.fiap.gateway.NotificacaoService;
import com.fiap.semaforo.exception.SemaforoNaoEncontradoException;
import com.fiap.semaforo.repository.SemaforoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcidenteService {

    private static final Logger log = LoggerFactory.getLogger(AcidenteService.class);
    private final AcidenteRepository acidenteRepository;
    private final SemaforoRepository semaforoRepository;
    private final NotificacaoService notificacaoService;

    public AcidenteService(AcidenteRepository acidenteRepository, SemaforoRepository semaforoRepository, NotificacaoService notificacaoService) {
        this.acidenteRepository = acidenteRepository;
        this.semaforoRepository = semaforoRepository;
        this.notificacaoService = notificacaoService;
    }

    public AcidenteViewDTO save(AcidenteCreateDTO acidenteCreateDTO) {
        // Validamos se existe um semáforo com o id informado
        var semaforo = semaforoRepository.findById(acidenteCreateDTO.semaforoId()).orElseThrow(() -> new SemaforoNaoEncontradoException(acidenteCreateDTO.semaforoId()));
        Acidente acidente = new Acidente();
        BeanUtils.copyProperties(acidenteCreateDTO, acidente);
        acidente.setSemaforo(semaforo);
        log.info("Acidente salvo: {}", acidente);

        if (acidente.getGravidade().equals(Gravidade.GRAVE)) {
            notificacaoService.enviaNotificacaoDeAcidenteGrave(acidente);
        }

        return new AcidenteViewDTO(acidenteRepository.save(acidente));
    }

    public Page<AcidenteViewDTO> findAll(Pageable paginacao) {
        return acidenteRepository.findAll(paginacao).map(AcidenteViewDTO::new);
    }

    public AcidenteViewDTO findById(Integer id) {
        Optional<Acidente> acidente = acidenteRepository.findById(id);
        if (acidente.isEmpty()) {
            throw new AcidenteNaoEncontradoException(id);
        }
        return new AcidenteViewDTO(acidente.get());
    }

    public AcidenteViewDTO updateById(Integer id, AcidenteUpdateDTO acidenteUpdateDTO) {
        log.info("Atualizando acidente com id: {}", id);
        Acidente acidente = acidenteRepository.findById(id).
                orElseThrow(() -> new AcidenteNaoEncontradoException(id));
        BeanUtils.copyProperties(acidenteUpdateDTO, acidente);
        // Recuperamos o semáforo atualizado
        var semaforo = semaforoRepository.findById(acidenteUpdateDTO.semaforoId()).orElseThrow(() -> new SemaforoNaoEncontradoException(acidenteUpdateDTO.semaforoId()));
        acidente.setSemaforo(semaforo);
        log.info("Acidente atualizado: {}", acidente);
        return new AcidenteViewDTO(acidenteRepository.save(acidente));
    }

    public AcidenteViewDTO deleteById(Integer id) {
        Acidente acidente = acidenteRepository.findById(id).
                orElseThrow(() -> new AcidenteNaoEncontradoException(id));
        acidenteRepository.delete(acidente);
        return new AcidenteViewDTO(acidente);
    }

    public RelatorioAcidentePorDataDTO getRelatioPorData(LocalDate data) {
        int qtdAcidentesGraves = 0;
        int qtdAcidentesModerados = 0;
        int qtdAcidentesLeves = 0;

        var acidentes = acidenteRepository.findByDataAcidente(data);

        for (Acidente acidente : acidentes) {
            if (acidente.getGravidade().equals(Gravidade.GRAVE)) {
                qtdAcidentesGraves++;
            }
            if (acidente.getGravidade().equals(Gravidade.MODERADO)) {
                qtdAcidentesModerados++;
            }
            if (acidente.getGravidade().equals(Gravidade.LEVE)) {
                qtdAcidentesLeves++;
            }
        }

        return new RelatorioAcidentePorDataDTO(qtdAcidentesLeves, qtdAcidentesModerados, qtdAcidentesGraves);
    }

    public List<AcidenteViewDTO> findByDataEGravidade(LocalDate data, String gravidade) {
        List<Acidente> acidentes = acidenteRepository.findByDataAcidenteAndGravidade(data, Gravidade.valueOf(gravidade.toUpperCase()));
        List<AcidenteViewDTO> acidenteDTOs = new ArrayList<>();

        for (Acidente acidente : acidentes) {
            acidenteDTOs.add(new AcidenteViewDTO(acidente));
        }

        return acidenteDTOs;
    }

}

