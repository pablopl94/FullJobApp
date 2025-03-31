package com.fulljob.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.services.IEmpresaService;

@Service
public class EmpresaImplService extends GenericCrudServiceImpl<Empresa, Integer> implements IEmpresaService {

	@Autowired
	private IEmpresaRepository empresaRepository;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Empresa, Integer> getRepository() {
		return empresaRepository;
	}

}
