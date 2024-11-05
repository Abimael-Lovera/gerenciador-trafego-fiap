package com.fiap.sensorTrafego.service;

import com.fiap.semaforo.exception.SemaforoNaoEncontradoException;
import com.fiap.semaforo.model.Semaforo;
import com.fiap.semaforo.repository.SemaforoRepository;
import com.fiap.sensorTrafego.dto.SensorTrafegoCreateDTO;
import com.fiap.sensorTrafego.dto.SensorTrafegoUpdateDTO;
import com.fiap.sensorTrafego.dto.SensorTrafegoViewDTO;
import com.fiap.sensorTrafego.exception.SensorTrafegoNaoEncontradoException;
import com.fiap.sensorTrafego.model.SensorTrafego;
import com.fiap.sensorTrafego.repository.SensorTrafegoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorTrafegoService {

    private static final Logger log = LoggerFactory.getLogger(SensorTrafegoService.class);

    private final SensorTrafegoRepository sensorTrafegoRepository;
    private final SemaforoRepository semaforoRepository;

    public SensorTrafegoService(SensorTrafegoRepository sensorTrafegoRepository, SemaforoRepository semaforoRepository) {
        this.sensorTrafegoRepository = sensorTrafegoRepository;
        this.semaforoRepository = semaforoRepository;
    }

    public SensorTrafegoViewDTO save(SensorTrafegoCreateDTO sensorTrafegoCreateDTO) {
        var sensorTrafego = new SensorTrafego();
        BeanUtils.copyProperties(sensorTrafegoCreateDTO, sensorTrafego);
        // Validamos se o semaforo existe
        log.info("Buscando semaforo com id: {}", sensorTrafegoCreateDTO.semaforoId());
        Semaforo semaforo = semaforoRepository.findById(sensorTrafegoCreateDTO.semaforoId())
                .orElseThrow(() -> new SemaforoNaoEncontradoException(sensorTrafegoCreateDTO.semaforoId()));
        sensorTrafego.setSemaforo(semaforo);
        log.info("SensorTrafego salvo: {}", sensorTrafego);

        alteraDuracaoDoSemaforo(sensorTrafego, semaforo);

        return new SensorTrafegoViewDTO(sensorTrafegoRepository.save(sensorTrafego));
    }

    public SensorTrafegoViewDTO findById(Integer id) {
        var sensorTrafego = sensorTrafegoRepository.findById(id).orElseThrow(() -> new SensorTrafegoNaoEncontradoException(id));
        return new SensorTrafegoViewDTO(sensorTrafego);
    }

    public Page<SensorTrafegoViewDTO> findAll(Pageable paginacao) {
        return sensorTrafegoRepository.findAll(paginacao).map(SensorTrafegoViewDTO::new);
    }

    public SensorTrafegoViewDTO updateById(Integer id, SensorTrafegoUpdateDTO sensorTrafegoUpdateDTO) {
        log.info("Atualizando sensorTrafego com id: {}", id);
        log.info("Atualizando sensorTrafego: {}", sensorTrafegoUpdateDTO);
        Optional<SensorTrafego> sensorTrafego = sensorTrafegoRepository.findById(id);
        if (sensorTrafego.isEmpty()) {
            throw new SensorTrafegoNaoEncontradoException(id);
        }
        // Validamos se o semaforo existe
        log.info("Buscando semaforo com id: {}", sensorTrafegoUpdateDTO.semaforoId());
        Semaforo semaforo = semaforoRepository.findById(sensorTrafegoUpdateDTO.semaforoId())
                .orElseThrow(() -> new SemaforoNaoEncontradoException(sensorTrafegoUpdateDTO.semaforoId()));
        log.info("Semaforo encontrado: {}", semaforo);

        sensorTrafego.get().setSemaforo(semaforo);
        BeanUtils.copyProperties(sensorTrafegoUpdateDTO, sensorTrafego.get());
        log.info("SensorTrafego atualizado: {}", sensorTrafego);
        return new SensorTrafegoViewDTO(sensorTrafegoRepository.save(sensorTrafego.get()));
    }

    public SensorTrafegoViewDTO deleteById(Integer id) {
        var sensorTrafego = sensorTrafegoRepository.findById(id).orElseThrow(() -> new SensorTrafegoNaoEncontradoException(id));
        sensorTrafegoRepository.delete(sensorTrafego);
        return new SensorTrafegoViewDTO(sensorTrafego);
    }

    private void alteraDuracaoDoSemaforo(SensorTrafego sensorTrafego, Semaforo semaforo) {
        if (sensorTrafego.getQtFluxoVeiculos() <= 100 ) {
            switch (semaforo.getDsEstado()) {
                case "verde", "vermelho":
                    semaforo.setNrDuracaoEstado(30);
                    break;
            }
        } else {
            switch (semaforo.getDsEstado()) {
                case "verde":
                    semaforo.setNrDuracaoEstado(120);
                    break;
                case "vermelho":
                    semaforo.setNrDuracaoEstado(30);
                    break;
            }
        }
    }

}
