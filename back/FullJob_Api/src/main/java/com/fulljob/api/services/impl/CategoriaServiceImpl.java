package com.fulljob.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.ICategoriaRepository;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.ICategoriaService;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServiceImpl extends GenericCrudServiceImpl<Categoria, Integer> implements ICategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Autowired
	private IVacanteRepository vacanteRepository;
	
	@Autowired
	private ISolicitudRepository solicitudRepository;
	
	@Override
	protected JpaRepository<Categoria, Integer> getRepository() {

		return categoriaRepository;
	}


	//METODO PARA QUE EL ADMINISTRADOR ELIMINE UNA CATEGORIA, SUS VACANTES Y SOLICITUDES RELACIONADAS
	//¡ Hay que poner un aviso en el front a la de eliminar !
	@Transactional
	@Override
	public void eliminarCategoria(int idCategoria) {
	    try {
	        Categoria categoria = categoriaRepository.findById(idCategoria)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría no existe"));

	        // Obtener todas las vacantes asociadas a esta categoría
	        List<Vacante> vacantes = vacanteRepository.findByCategoria(categoria);

	        // Eliminar todas las solicitudes de cada vacante (usando ID directamente)
	        for (Vacante vacante : vacantes) {
	            try {
	                solicitudRepository.deleteByIdVacante(vacante.getIdVacante());
	            } catch (Exception e) {
	                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
	                        "Error al eliminar solicitudes de la vacante con ID: " + vacante.getIdVacante(), e);
	            }
	        }

	        // Eliminar todas las vacantes asociadas
	        vacanteRepository.deleteAll(vacantes);

	        // Finalmente, eliminar la categoría
	        categoriaRepository.delete(categoria);

	    } catch (ResponseStatusException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
	                "Error inesperado al eliminar la categoría", e);
	    }
	}
}
