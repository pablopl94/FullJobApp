package com.fulljob.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.repository.IEmpresaRepository;

@Service
public class EmpresaImplService extends GenericCrudServiceImpl<Empresa, Integer> implements IEmpresaService {

	@Autowired
	private IEmpresaRepository empresaRepo;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Empresa, Integer> getRepository() {
		return empresaRepo;
	}

}
