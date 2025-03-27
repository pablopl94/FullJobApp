package com.fulljob.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.repository.ICategoriaRepository;

public class CategoriaServiceImpl extends GenericCrudServiceImpl<Categoria, Integer> {
	
	@Autowired
	private ICategoriaRepository categoriaRepo;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Categoria, Integer> getRepository() {
		return categoriaRepo;
	}
	
	
}
