package com.fulljob.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.repository.ICategoriaRepository;
import com.fulljob.api.services.ICategoriaService;

@Service
public class CategoriaServiceImpl extends GenericCrudServiceImpl<Categoria, Integer> 
		implements ICategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepo;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Categoria, Integer> getRepository() {
		return categoriaRepo;
	}

}
