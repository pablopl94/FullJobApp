package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Vacante;

@Repository
public interface IVacanteRepository extends JpaRepository<Vacante, Integer> {

}
