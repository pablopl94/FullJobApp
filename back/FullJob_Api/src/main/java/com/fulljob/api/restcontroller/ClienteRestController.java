package com.fulljob.api.restcontroller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.AltaClienteAdminRequestDto;
import com.fulljob.api.models.dto.AltaClienteAdminResponseDto;
import com.fulljob.api.models.dto.AltaClienteRequestDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.IUsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
public class ClienteRestController {

	@Autowired
	private IUsuarioService iUsuarioService;

	@Autowired
	private ModelMapper modelMapper;

	// ENDPOINT PARA BUSCAR TODOS LOS USUARIOS
	//GET    /clientes/.............. [ROLE_ADMON] 
	@GetMapping
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<List<AltaClienteResponseDto>> findAllUsuarios() {

		List<Usuario> usuarios = iUsuarioService.findAll();

		List<AltaClienteResponseDto> response = usuarios.stream()
				.map(usuario -> modelMapper.map(usuario, AltaClienteResponseDto.class)).toList();

		return ResponseEntity.ok(response);
	}
	

	// ENDPOINT PARA VER DETALLES DE UN USUARIO POR ID
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteAdminResponseDto> findById(@PathVariable String id) {
		
		Usuario usuario = iUsuarioService.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario con email " + id + " no encontrado"));

		AltaClienteAdminResponseDto response = modelMapper.map(usuario, AltaClienteAdminResponseDto.class);

		return ResponseEntity.status(200).body(response);
	}

	// ENDPOINT PARA CREAR USUARIOS CON ROL "CLIENTE O ADMON "
	@PostMapping("/alta")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteAdminResponseDto> createUsuario(@RequestBody @Valid AltaClienteAdminRequestDto clienteDto) {

		AltaClienteAdminResponseDto respuestaDto = iUsuarioService.altaCandidatoConRol(clienteDto);

		return ResponseEntity.ok(respuestaDto);
	}

	// ENDPOINT PARA MODIFICAR USUARIOS
	// PUT /categorias/{id} ...................... [ROLE_ADMON]
	@PutMapping("modificar/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteAdminResponseDto> updateUsuario(@PathVariable String id ,@RequestBody @Valid AltaClienteAdminRequestDto clienteDto) {

		AltaClienteAdminResponseDto respuestaDto =  iUsuarioService.actualizarDatosCliente(id, clienteDto);

		return ResponseEntity.ok(respuestaDto);

	}

	// ENDPOINT PARA DESACTIVAR USUARIOS (NO BORRA AL USUARIO)
	@PutMapping("/desactivar/{email}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<Map<String, String>> desactivarUsuario(@PathVariable String email) {
		
		iUsuarioService.cambiarEstadoUsuario(email, 0);
		
		return ResponseEntity.ok(Map.of("message", "Usuario desactivado por mal uso", "email", email));
	}

	// ENDPOINT PARA ACTIVAR USUARIOS
	@PutMapping("/activar/{email}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<Map<String, String>> activarUsuario(@PathVariable String email) {
		
		iUsuarioService.cambiarEstadoUsuario(email, 1);
		
		return ResponseEntity.ok(Map.of("message", "Usuario activado correctamente", "email", email));
	}

}
