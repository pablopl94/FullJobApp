package com.fulljob.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.services.ISolicitudService;

@Service
public class SolicitudServiceImpl extends GenericCrudServiceImpl<Solicitud, Integer> implements ISolicitudService {

	@Autowired
	private ISolicitudRepository solicitudRepo;
	
	@Autowired
	private ModelMapper mapper;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected ISolicitudRepository getRepository() {
		return solicitudRepo;
	}
	
	
	@Override
	public List<SolicitudResponseDto> listarSolicitudesUsuario(Usuario usuario) {
	
		//Comprobamos que el usuario que nos ha llegado de la sesion no sea nulo
		if(usuario == null) {
			throw new IllegalArgumentException("El Usuario es nulo");
		}
		
		List<Solicitud> listaSolicitudes = solicitudRepo.findByUsuario_email(usuario.getEmail());
		
		return listaSolicitudes.stream()
				.map(solicitud -> {
					SolicitudResponseDto dto = mapper.map(solicitud,SolicitudResponseDto.class);
					
					//Le añadimos datos que no mapea automaticamente
					dto.setNombreUsuario(usuario.getNombre());
					dto.setApellidosUsuario(usuario.getApellidos());
					return dto;
				})
				.collect(Collectors.toList());

	}

}
