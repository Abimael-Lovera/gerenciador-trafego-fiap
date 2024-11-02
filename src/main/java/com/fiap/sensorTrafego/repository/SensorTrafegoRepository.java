package com.fiap.sensorTrafego.repository;

import com.fiap.sensorTrafego.model.SensorTrafego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorTrafegoRepository extends JpaRepository<SensorTrafego, Integer> {
}
