package com.fulljob.api.services;

import java.util.List;

import com.fulljob.api.models.dto.SolicitudRequestDto;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.dto.VacanteRequestDto;
import com.fulljob.api.models.dto.VacanteResponseDto;
import com.fulljob.api.models.entities.TipoDeContrato;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;

public interface IVacanteService extends IGenericCrud<Vacante, Integer> {

	 List<VacanteResponseDto> findAllVacanteActivas ();
	 
	 List<VacanteResponseDto> filtrarVacantesEmpresa(String empres);
	 
	 List<VacanteResponseDto> filtrarVacantesCategoria(int idCategoria);
	 
	 List<VacanteResponseDto> filtrarVacantesTipoContrato(TipoDeContrato detalles);

	 List<VacanteResponseDto> obtenerVacantesDeEmpresaAsignadas(Usuario usuario);
	 
	 List<VacanteResponseDto> obtenerVacantesDeEmpresaCreadas(Usuario usuario);
	 
	 List<VacanteResponseDto> obtenerVacantesDeEmpresaCanceladas(Usuario usuario);

	
	 VacanteResponseDto publicarVacante(VacanteRequestDto vacanteDto, Usuario usuario);

	 VacanteResponseDto editarVacante(int idVacante, VacanteRequestDto vacanteDto);

	 void cancelarVacante(int idVacante);

	 SolicitudResponseDto inscribirseVacante(int idVacante, Usuario usuario, SolicitudRequestDto solicitudDto);

	

}
