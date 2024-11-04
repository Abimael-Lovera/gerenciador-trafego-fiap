package com.fiap.acidente.repository;

import com.fiap.acidente.model.Acidente;
import com.fiap.acidente.model.Gravidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcidenteRepository extends JpaRepository<Acidente, Integer> {

    List<Acidente> findByDataAcidente(LocalDate dataAcidente);
    List<Acidente> findByDataAcidenteAndGravidade(LocalDate dataAcidente, Gravidade gravidade);

}
