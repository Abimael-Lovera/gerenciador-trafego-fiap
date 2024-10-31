package com.fiap.semaforo.repository;

import com.fiap.semaforo.model.Semaforo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemaforoRepository extends JpaRepository<Semaforo, Integer> {
}
