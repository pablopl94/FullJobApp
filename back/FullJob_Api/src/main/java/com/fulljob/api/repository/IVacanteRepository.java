package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Vacante;



public interface IVacanteRepository extends JpaRepository<Vacante, Integer>{

}
