package com.fiap.acidente.repository;

import com.fiap.acidente.model.Acidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcidenteRepository extends JpaRepository<Acidente, Integer> {
}
