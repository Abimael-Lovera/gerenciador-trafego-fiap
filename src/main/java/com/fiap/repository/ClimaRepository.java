package com.fiap.repository;

import com.fiap.model.Clima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimaRepository extends JpaRepository<Clima, Long> {
    Page<Clima> findAllByOrderByIdClimaDesc(Pageable paginacao);
    Page<Clima> findAll(Pageable paginacao);
}
