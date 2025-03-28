package com.fulljob.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.repository.ISolicitudRepository;

public class SolicitudServiceImpl extends GenericCrudServiceImpl<Solicitud, Integer> implements ISolicitudService{

	@Autowired
	private ISolicitudRepository solicitudRepo;
	
	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Solicitud, Integer> getRepository() {
		return solicitudRepo;
	}

}
