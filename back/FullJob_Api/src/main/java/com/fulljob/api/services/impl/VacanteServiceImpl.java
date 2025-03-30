package com.fulljob.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.IVacanteService;

public class VacanteServiceImpl extends GenericCrudServiceImpl<Vacante, Integer> implements IVacanteService{

	@Autowired
	private IVacanteRepository vacanteRepo;
	
	
	@Override
	protected JpaRepository<Vacante, Integer> getRepository() {
		return vacanteRepo;
	}

}
