package com.fulljob.api.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudRequestDto;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.EstadoSolicitud;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.services.ISolicitudService;
import com.fulljob.api.services.IVacanteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/solicitudes")
public class SolicitudRestController {

//POST   /solicitudes .......................... [ROLE_CLIENTE]
//GET    /solicitudes/ ..........[ROLE_CLIENTE]   ← seguimiento de solicitudes del usuario
//DELETE /solicitudes/{id} ..................... [ROLE_CLIENTE]
//GET    /solicitudes/vacante/{idVacante} ...... [ROLE_EMPRESA]
//PUT    /solicitudes/asignar/{id} ............. [ROLE_EMPRESA]   ← adjudica vacante a candidato y cambia estado de la solicitud

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ISolicitudService solicitudService;

	@Autowired
	private IVacanteService vacanteService;

	/**
	 * Endpoint para obtener las solicitudes del usuario autenticado.
	 */
	@GetMapping
	public ResponseEntity<List<SolicitudResponseDto>> listaSolicitudesUsuario() {
		// Obtener el usuario autenticado desde el contexto de seguridad
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Buscar todas las solicitudes del usuario
		List<Solicitud> solicitudes = solicitudService.findByUsuarioEmail(usuario.getEmail());

		// Convertir las entidades en DTOs y devolver la respuesta
		List<SolicitudResponseDto> response = solicitudes.stream()
				.map(solicitud -> modelMapper.map(solicitud, SolicitudResponseDto.class)).collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint para crear una nueva solicitud.
	 */
	@PostMapping
	public ResponseEntity<SolicitudResponseDto> crearSolicitud(
			@RequestBody @Valid SolicitudRequestDto solicitudRequestDto) {
		// Obtener el usuario autenticado desde el contexto de seguridad
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Crear un nuevo objeto Solicitud a partir del DTO recibido
		Solicitud solicitud = modelMapper.map(solicitudRequestDto, Solicitud.class);
		solicitud.setUsuario(usuario); // Asignar el usuario autenticado a la solicitud

		// Llamar al servicio para guardar la solicitud
		Solicitud solicitudGuardada = solicitudService.insertOne(solicitud);

		// Mapear la solicitud guardada a un DTO y devolverla en la respuesta
		SolicitudResponseDto response = modelMapper.map(solicitudGuardada, SolicitudResponseDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Endpoint para eliminar una solicitud.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> eliminarSolicitud(@PathVariable Integer id) {
		// Verificar si la solicitud existe
		Optional<Solicitud> solicitudOpt = solicitudService.findById(id);
		if (solicitudOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada");
		}

		// Eliminar la solicitud
		solicitudService.deleteOne(id);
		return ResponseEntity.ok(Map.of("message", "Solicitud eliminada correctamente"));
	}

	/**
	 * Endpoint para obtener las solicitudes de una vacante específica (para
	 * empresas).
	 */
	@GetMapping("/vacante/{idVacante}")
	public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudesPorVacante(@PathVariable Integer idVacante) {
		Vacante vacante = vacanteService.findById(idVacante)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacante no encontrada"));

		// Obtener todas las solicitudes relacionadas con la vacante
		List<Solicitud> solicitudes = solicitudService.findByVacante(vacante);

		// Convertir las entidades a DTOs
		List<SolicitudResponseDto> response = solicitudes.stream()
				.map(solicitud -> modelMapper.map(solicitud, SolicitudResponseDto.class)).collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	@PutMapping("/asignar/{id}")
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
