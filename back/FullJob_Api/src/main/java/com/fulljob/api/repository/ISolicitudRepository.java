package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Solicitud;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {

}
