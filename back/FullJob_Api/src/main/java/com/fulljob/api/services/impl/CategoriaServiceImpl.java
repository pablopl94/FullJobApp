package com.fulljob.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.repository.ICategoriaRepository;
import com.fulljob.api.services.ICategoriaService;

@Service
public class CategoriaServiceImpl extends GenericCrudServiceImpl<Categoria, Integer> implements ICategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Override
	protected JpaRepository<Categoria, Integer> getRepository() {

		return categoriaRepository;
	}


	@Override
	public void eliminarCategoria(int idCategoria) {

		Categoria categoria = categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new RuntimeException("La categoria no puede ser null"));
				
	    if (categoriaRepository.existsById(categoria.getIdCategoria())) {
	        try {
	            categoriaRepository.delete(categoria);
	        } catch (DataIntegrityViolationException e) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría contiene vacantes y no se puede eliminar");
	        }
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría no existe");
	    }
	}
}
