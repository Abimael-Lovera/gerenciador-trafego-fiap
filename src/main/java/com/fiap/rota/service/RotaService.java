package com.fiap.rota.service;

import com.fiap.acidente.exception.AcidenteNaoEncontradoException;
import com.fiap.acidente.repository.AcidenteRepository;
import com.fiap.rota.dto.RotaCreateDTO;
import com.fiap.rota.dto.RotaUpdateDTO;
import com.fiap.rota.dto.RotaViewDTO;
import com.fiap.rota.exception.RotaNaoEncontradoException;
import com.fiap.rota.model.Rota;
import com.fiap.rota.repository.RotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RotaService {

    private static final Logger log = LoggerFactory.getLogger(RotaService.class);
    private final RotaRepository rotaRepository;
    private final AcidenteRepository acidenteRepository;

    public RotaService(RotaRepository rotaRepository, AcidenteRepository acidenteRepository) {
        this.rotaRepository = rotaRepository;
        this.acidenteRepository = acidenteRepository;
    }

    public RotaViewDTO save(RotaCreateDTO rotaCreateDTO) {
        Rota rota = new Rota();
        // Validamos se o acidente existe com o id informado
        if (rotaCreateDTO.acidenteId() != null) {
            var acidente = acidenteRepository.findById(rotaCreateDTO.acidenteId()).orElseThrow(() -> new AcidenteNaoEncontradoException(rotaCreateDTO.acidenteId()));
            log.info("Acidente encontrado com id: {}", rotaCreateDTO.acidenteId());
            log.info("Acidente: {}", acidente);
            // Atualizamos o acidente no rota
            rota.setAcidente(acidente);
        } else {
            throw new RotaNaoEncontradoException(rotaCreateDTO.acidenteId());
        }

        BeanUtils.copyProperties(rotaCreateDTO, rota);
        log.info("Rota salvo: {}", rota);
        return new RotaViewDTO(rotaRepository.save(rota));
    }

    public Page<RotaViewDTO> findAll(Pageable paginacao) {
        return rotaRepository.findAll(paginacao).map(RotaViewDTO::new);
    }

    public RotaViewDTO findById(Integer id) {
        var rota = rotaRepository.findById(id).orElseThrow(() -> new AcidenteNaoEncontradoException(id));
        return new RotaViewDTO(rota);
    }

    public RotaViewDTO updateById(Integer id, RotaUpdateDTO rotaUpdateDTO) {

        log.info("Atualizando rota com id: {}", id);
        // Validando se existe a rota com o id informado
        var rota = rotaRepository.findById(id)
                .orElseThrow(() -> new RotaNaoEncontradoException(id));
        BeanUtils.copyProperties(rotaUpdateDTO, rota);
        // Validamos se na rota existe um acidente
        if (rotaUpdateDTO.acidenteId() != null) {
            var acidente = acidenteRepository.findById(rotaUpdateDTO.acidenteId()).orElseThrow(() -> new AcidenteNaoEncontradoException(rotaUpdateDTO.acidenteId()));
            log.info("Acidente encontrado: {}", acidente);
            log.info("Atualizando acidente no rota");
            rota.setAcidente(acidente);
        }

        log.info("Rota atualizado: {}", rota);
        return new RotaViewDTO(rotaRepository.save(rota));
    }

    public RotaViewDTO deleteById(Integer id) {
        var rota = rotaRepository.findById(id).orElseThrow(() -> new RotaNaoEncontradoException(id));
        rotaRepository.delete(rota);
        return new RotaViewDTO(rota);
    }

}
