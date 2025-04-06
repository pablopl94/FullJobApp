package com.fulljob.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Solicitud;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {

	List<Solicitud> findByUsuario_email(String email);
}
