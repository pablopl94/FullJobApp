package com.fulljob.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.ISolicitudService;

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
	private ISolicitudService solicitudService;
	
	
	@GetMapping()
	public ResponseEntity<List<SolicitudResponseDto>> listaSolicitudesUsuario() {
		
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		List<SolicitudResponseDto> respuestaDto =  solicitudService.listarSolicitudesUsuario(usuario);
		
		return ResponseEntity.ok(respuestaDto);
	}
			
			
}
