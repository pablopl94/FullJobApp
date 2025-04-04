package com.fulljob.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.TipoDeContrato;
import com.fulljob.api.models.entities.Vacante;


@Repository
public interface IVacanteRepository extends JpaRepository<Vacante, Integer> {

	List<Vacante> findByEmpresa_IdEmpresa(int idEmpresa);
	
	List<Vacante> findByEmpresa_NombreEmpresa(String empresa);
	
	List<Vacante> findByCategoria_idCategoria(int idCategoria);
	
	List<Vacante> findByDetalles(TipoDeContrato detalles);
	
	
	
	
}
