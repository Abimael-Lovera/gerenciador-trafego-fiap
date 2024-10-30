package com.fiap.service;

import com.fiap.dto.ClimaCreateDTO;
import com.fiap.dto.ClimaUpdateDTO;
import com.fiap.dto.ClimaViewDTO;
import com.fiap.exceptions.ClimaNaoEncontradaException;
import com.fiap.model.Clima;
import com.fiap.repository.ClimaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClimaService {
    private static final Logger log = LoggerFactory.getLogger(ClimaService.class);
    private final ClimaRepository climaRepository;

    public ClimaService(ClimaRepository climaRepository) {
        this.climaRepository = climaRepository;
    }

    public ClimaViewDTO save(ClimaCreateDTO climaCreateDTO) {
        Clima clima = new Clima();
        BeanUtils.copyProperties(climaCreateDTO, clima);
        Clima climaSalvo = climaRepository.save(clima);
        log.info("Clima salvo com sucesso");
        log.info("Clima salvo: {}", climaSalvo);
        return new ClimaViewDTO(climaSalvo);
    }

    public ClimaViewDTO findById(Long idClima) {
        Optional<Clima> clima = climaRepository.findById(idClima);
        if (clima.isEmpty()) {
            log.error("Não foi possivel encontrar o clima com id: " +  idClima);
            throw new ClimaNaoEncontradaException("Clima não encontrado");
        }
        return new ClimaViewDTO(clima.get());
    }

    public Page<ClimaViewDTO> findAll(Pageable pageable) {
        return climaRepository.findAll(pageable).map(ClimaViewDTO::new);
    }

    public ClimaViewDTO updateById(ClimaUpdateDTO climaUpdateDTO, Long idClima) {
        Optional<Clima> climaFindById = climaRepository.findById(idClima);
        log.info("climaFindById: {}", climaFindById);
        if (climaFindById.isEmpty()) {
            log.error("Não foi possivel encontrar o clima com id{}", idClima);
            throw new ClimaNaoEncontradaException("Clima não encontrado");
        }
        Clima climaAtualizado = new Clima();
        climaAtualizado.setIdClima(climaFindById.get().getIdClima());
        BeanUtils.copyProperties(climaUpdateDTO, climaAtualizado);
        log.info("climaAtualizado: {}", climaAtualizado);
        return new ClimaViewDTO(climaRepository.save(climaAtualizado));
    }

    public ClimaViewDTO deleteById(Long idClima) {
        Optional<Clima> clima = climaRepository.findById(idClima);
        if (clima.isEmpty()) {
            log.error("Não foi possivel encontrar o clima com id: {}", idClima);
            throw new ClimaNaoEncontradaException("Clima não encontrado");
        }
        climaRepository.deleteById(idClima);
        log.info("Clima deletado com sucesso");
        log.info("Clima deletado: {}", clima.get());
        return new ClimaViewDTO(clima.get());
    }
}
