package com.fulljob.api.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.ISolicitudService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/solicitudes")
@EnableMethodSecurity(prePostEnabled = true)
public class SolicitudRestController {


	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ISolicitudService solicitudService;

	
	
	//ENDPOINT PARA OBTENER TODAS LAS SOLICITUDES DEL USUARIO AUTENTICADO
	//GET    /solicitudes/missolicitudes .......... [ROLE_CLIENTE]
	@GetMapping("/missolicitudes")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<List<SolicitudResponseDto>> listaSolicitudesUsuario(@AuthenticationPrincipal Usuario usuario) {
		
		// Buscar todas las solicitudes del usuario
		List<Solicitud> solicitudes = solicitudService.findByUsuarioEmail(usuario.getEmail());

		// Convertir las entidades en DTOs y devolver la respuesta
		List<SolicitudResponseDto> response = solicitudes.stream()
				.map(solicitud -> modelMapper.map(solicitud, SolicitudResponseDto.class)).collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}


	//ENDPOINT PARA QUE EL CLIENTE CANCELE SU SOLICITUD
	//PUT    /solicitudes/cancelar/{id} ............ [ROLE_CLIENTE]
	@PutMapping("/cancelar/{id}")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<SolicitudResponseDto> eliminarSolicitud(@PathVariable Integer id) {

		SolicitudResponseDto respuestaDto = solicitudService.eliminarSolicitud(id);
		
		return ResponseEntity.ok(respuestaDto);
	}
	

	//ENDPOINT PARA VER TODAS LAS SOLITUDES DE UNA VACANTE
	//GET    /solicitudes/vacante/{id} ............. [ROLE_EMPRESA] 
	@GetMapping("/vacante/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudesPorVacante(@PathVariable Integer id) {

		List<SolicitudResponseDto> listaDto = solicitudService.findByVacante(id);

		return ResponseEntity.ok(listaDto);
	}

	
	//ENDPOINT PARA VER DETALLES DE UNA SOLICITUD
	//GET    /solicitudes/vacante/{id} ............. [ROLE_EMPRESA]
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<SolicitudResponseDto> detallesSolicitud(@PathVariable Integer id) {
		
		Solicitud solicitud = solicitudService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

		SolicitudResponseDto respuestaDto = modelMapper.map(solicitud, SolicitudResponseDto.class);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	//ENDPOINT PARA ASIGNAR UNA VACANTE A UN USUARIO, CAMBIA EL ESTADO DE LA SOLICITUD
	//PUT    /solicitudes/asignar/{id} ............. [ROLE_EMPRESA] 
	@PutMapping("/asignar/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<String> asignarVacanteACandidato(@PathVariable Integer id) {

		 solicitudService.asignarVacante(id);

		// Devolver un código de estado 200 con el mensaje
		return ResponseEntity.ok("Solicitud asignada");
	}

}
