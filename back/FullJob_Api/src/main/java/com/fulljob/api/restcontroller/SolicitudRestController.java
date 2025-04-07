package com.fulljob.api.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.fulljob.api.models.entities.EstadoSolicitud;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.services.ISolicitudService;
import com.fulljob.api.services.IVacanteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/solicitudes")
@EnableMethodSecurity(prePostEnabled = true)
public class SolicitudRestController {


	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ISolicitudService solicitudService;

	@Autowired
	private IVacanteService vacanteService;
	
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
	//PUT    /solicitudes/cancelar/{id} ............ [ROLE_EMPRESA]
	@GetMapping("/vacante/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudesPorVacante(@PathVariable Integer id) {
		
		Vacante vacante = vacanteService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacante no encontrada"));

		// Obtener todas las solicitudes relacionadas con la vacante
		List<Solicitud> solicitudes = solicitudService.findByVacante(vacante);

		// Convertir las entidades a DTOs
		List<SolicitudResponseDto> response = solicitudes.stream()
				.map(solicitud -> modelMapper.map(solicitud, SolicitudResponseDto.class)).collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	//ENDPOINT PARA ASIGNAR UNA VACANTE A UN USUARIO, CAMBIA EL ESTADO DE LA SOLICITUD
	//PUT    /solicitudes/asignar/{id} ............. [ROLE_EMPRESA] 
	@PutMapping("/asignar/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<Map<String, String>> asignarVacanteACandidato(@PathVariable Integer id,
			@RequestBody Map<String, Integer> vacanteRequest) {

		// Buscar la solicitud por ID
		Solicitud solicitud = solicitudService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

		// Obtener el ID de la vacante desde el cuerpo de la solicitud
		Integer vacanteId = vacanteRequest.get("vacanteId");

		// Buscar la vacante por ID
		Vacante vacante = vacanteService.findById(vacanteId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacante no encontrada"));

		// Asignar la vacante a la solicitud y cambiar el estado
		solicitud.setVacante(vacante);
		solicitud.setEstado(EstadoSolicitud.ADJUDICADA); // Asumimos que el estado cambia a "ADJUDICADA"

		// Guardar la solicitud actualizada
		solicitudService.updateOne(solicitud);

		// Crear un mensaje de éxito
		Map<String, String> response = new HashMap<>();
		response.put("message", "Vacante asignada correctamente");

		// Devolver un código de estado 200 con el mensaje
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
