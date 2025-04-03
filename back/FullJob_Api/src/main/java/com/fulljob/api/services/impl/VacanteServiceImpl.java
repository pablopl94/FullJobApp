package com.fulljob.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.VacanteResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.IVacanteService;

@Service
public class VacanteServiceImpl extends GenericCrudServiceImpl<Vacante, Integer> implements IVacanteService {

	@Autowired
	private IVacanteRepository vacanteRepo;
	
	@Autowired
	private IEmpresaRepository empresaRepo;
	
	@Autowired
	private ModelMapper mapper;;

	@Override
	protected IVacanteRepository getRepository() {
		return vacanteRepo;
	}

	//Metodo para ver todas las vancantes activas
	@Override
	public List<VacanteResponseDto> findAllVacanteActivas() {
		
		//Obtenemos tododas las vacantes
		List<Vacante> listaVacantes = vacanteRepo.findAll();
		
		//Devolvemos la lista filtrada por las que tienen el estado creada
		//Mapeamos vancate a dto de respuesta
		//Lo volvemos a meter en una lista
		return listaVacantes.stream() 
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
		            VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
		            
		            // Añadimos los valores de las relaciones al dto
		            dto.setNombreCategoria(vacante.getCategoria().getNombre());
		            dto.setNombreEmpresa(vacante.getEmpresa().getNombreEmpresa());			
		            
		            return dto;
		        })
		        .collect(Collectors.toList());
	}

	
	//Metodo para filtrar vacantes por nombre de la empresa
	@Override
	public List<VacanteResponseDto> filtrarVacantesEmpresa(String empresa) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_NombreEmpresa(empresa);
		
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	//Metodo para filtrar vacantes por categoria
	@Override
	public List<VacanteResponseDto> filtrarVacantesCategoria(int idCategoria) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByCategoria_idCategoria(idCategoria);
		
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}

	
	//Metodo para filtrar vacantes por TIPO DE CONTRATO = detalles
	@Override
	public List<VacanteResponseDto> filtrarVacantesTipoContrato(String detalles) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByDetalles(detalles);
		
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	
	//Metodo para ver las vacantes de una empresa
	@Override
	public List<VacanteResponseDto> obtenerVacantesDeEmpresa(Usuario usuario) {
		
		//Obtenemos la empresa del usuario que nos llega por paramentro(obtenido de la sesion)
		Empresa empresa = empresaRepo.findByUsuario(usuario);
		if (empresa == null) {
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la empresa asociada al usuario");
		}

		//Con la empresa sacamos una lista de sus vacantes
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_IdEmpresa(empresa.getIdEmpresa());
		
		return listaVacantes.stream()
				//Mapeamos la entidades vacantes que hay en  lista
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	

}
