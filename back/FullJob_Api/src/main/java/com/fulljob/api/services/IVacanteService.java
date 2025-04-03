package com.fulljob.api.services;

import java.util.List;

import com.fulljob.api.models.dto.VacanteResponseDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;

public interface IVacanteService extends IGenericCrud<Vacante, Integer> {

	 List<VacanteResponseDto> findAllVacanteActivas ();
	 
	 List<VacanteResponseDto> filtrarVacantesEmpresa(String empres);
	 
	 List<VacanteResponseDto> filtrarVacantesCategoria(int idCategoria);
	 
	 List<VacanteResponseDto> filtrarVacantesTipoContrato(String detalles);

	List<VacanteResponseDto> obtenerVacantesDeEmpresa(Usuario usuario);
}
