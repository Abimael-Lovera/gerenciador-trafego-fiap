package com.fiap.semaforo.service;

import com.fiap.clima.exceptions.ClimaNaoEncontradoException;
import com.fiap.clima.model.Clima;
import com.fiap.clima.repository.ClimaRepository;
import com.fiap.semaforo.dto.SemaforoCreateDTO;
import com.fiap.semaforo.dto.SemaforoUpdateDTO;
import com.fiap.semaforo.dto.SemaforoViewDTO;
import com.fiap.semaforo.exception.SemaforoNaoEncontradoException;
import com.fiap.semaforo.model.Semaforo;
import com.fiap.semaforo.repository.SemaforoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SemaforoService {

    private static final Logger log = LoggerFactory.getLogger(SemaforoService.class);
    private final SemaforoRepository semaforoRepository;
    private final ClimaRepository climaRepository;

    public SemaforoService(SemaforoRepository semaforoRepository, ClimaRepository climaRepository) {
        this.semaforoRepository = semaforoRepository;
        this.climaRepository = climaRepository;
    }


    public SemaforoViewDTO save(SemaforoCreateDTO semaforoCreateDTO) {
        Semaforo semaforo = new Semaforo();
        BeanUtils.copyProperties(semaforoCreateDTO, semaforo);
        //Validamos se o clima existe
        log.info("Buscando clima com id: {}", semaforoCreateDTO.climaId());
        Clima clima = climaRepository.findById(semaforoCreateDTO.climaId()).orElseThrow(
                () -> new ClimaNaoEncontradoException(semaforoCreateDTO.climaId())
        );

        semaforo.setClima(clima);
        log.info("Semaforo salvo: {}", semaforo);
        var semaforoSalvo = semaforoRepository.save(semaforo);
        return new SemaforoViewDTO(semaforoSalvo);
    }

    public Page<SemaforoViewDTO> findAll(Pageable paginacao){
        return semaforoRepository.findAll(paginacao).map(SemaforoViewDTO::new);
    }

    public SemaforoViewDTO findById(Integer id) {
        Optional<Semaforo> semaforo = semaforoRepository.findById(id);
        if (semaforo.isEmpty()) {
            throw new SemaforoNaoEncontradoException(id);
        }
        return new SemaforoViewDTO(semaforo.get());
    }


    public SemaforoViewDTO updateById(Integer id, SemaforoUpdateDTO semaforoUpdateDTO) {
        Semaforo semaforo = semaforoRepository.findById(id)
                .orElseThrow(() -> new SemaforoNaoEncontradoException(id));

        BeanUtils.copyProperties(semaforoUpdateDTO, semaforo);

        //Validamos se o clima existe
        log.info("Buscando clima com id: {}", semaforoUpdateDTO.climaId());
        Clima clima = climaRepository.findById(semaforoUpdateDTO.climaId())
                .orElseThrow(() -> new ClimaNaoEncontradoException(semaforoUpdateDTO.climaId()));
        semaforo.setClima(clima);

        return new SemaforoViewDTO(semaforoRepository.save(semaforo));
    }

    public SemaforoViewDTO deleteById(Integer id) {
        Semaforo semaforo = semaforoRepository.findById(id)
                .orElseThrow(() -> new SemaforoNaoEncontradoException(id));
        semaforoRepository.delete(semaforo);
        return new SemaforoViewDTO(semaforo);
    }

}
