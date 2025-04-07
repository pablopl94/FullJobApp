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
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMON')")
	public ResponseEntity<List<AltaClienteResponseDto>> findAllUsuarios() {

		List<Usuario> usuarios = iUsuarioService.findAll();

		List<AltaClienteResponseDto> response = usuarios.stream()
				.map(usuario -> modelMapper.map(usuario, AltaClienteResponseDto.class)).toList();

		return ResponseEntity.ok(response);
	}

	// ENDPOINT PARA BUSCAR UN USUARIO POR ID
	@GetMapping("/{email}")
	public ResponseEntity<AltaClienteResponseDto> findById(@PathVariable String email) {
		Usuario usuario = iUsuarioService.findById(email)
				.orElseThrow(() -> new RuntimeException("Usuario con email " + email + " no encontrado"));

		AltaClienteResponseDto response = modelMapper.map(usuario, AltaClienteResponseDto.class);

		return ResponseEntity.status(200).body(response);
	}

	// ENDPOINT PARA CREAR USUARIOS
	@PostMapping
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteResponseDto> createUsuario(
			@RequestBody @Valid AltaClienteRequestDto altaClienteRequestDto) {

		Usuario nuevoUsuario = modelMapper.map(altaClienteRequestDto, Usuario.class);

		Usuario usuarioCreado = iUsuarioService.insertOne(nuevoUsuario);

		AltaClienteResponseDto response = modelMapper.map(usuarioCreado, AltaClienteResponseDto.class);

		return ResponseEntity.ok(response);
	}

	// ENDPOINT PARA MODIFICAR USUARIOS
	// PUT /categorias/{id} ...................... [ROLE_ADMON]
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteResponseDto> updateUsuario(@PathVariable String email,
			@RequestBody @Valid AltaClienteRequestDto altaClienteRequestDto) {

		Usuario usuario = iUsuarioService.findById(email)
				.orElseThrow(() -> new RuntimeException("UsUario con email " + email + " no encontrado"));

		Usuario.UsuarioBuilder builder = usuario.toBuilder()
                .nombre(altaClienteRequestDto.getNombre())
                .apellidos(altaClienteRequestDto.getApellidos());               
                //.rol(altaClienteRequestDto.getRol()); //Podemos añadir tambien el rol
		
		Usuario usuarioActualizado = builder.build();

		iUsuarioService.updateOne(usuarioActualizado);

		AltaClienteResponseDto response = modelMapper.map(usuarioActualizado, AltaClienteResponseDto.class);

		return ResponseEntity.ok(response);

	}

	// ENDPOINT PARA DESACTIVAR USUARIOS (NO BORRA AL USUARIO)
	@PutMapping("/desactivar_usuario/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMON')")
	public ResponseEntity<Map<String, String>> desactivarUsuario(@PathVariable String email) {
		iUsuarioService.darBajaUsuario(email, 0);
		return ResponseEntity.ok(Map.of("message", "Usuario desactivado por mal uso", "email", email));
	}

	// ENDPOINT PARA ACTIVAR USUARIOS
	@PutMapping("/activar_usuario/{email}")
	@PreAuthorize("hasAuthority('ROLE_ADMON')")
	public ResponseEntity<Map<String, String>> activarUsuario(@PathVariable String email) {
		iUsuarioService.darBajaUsuario(email, 1);
		return ResponseEntity.ok(Map.of("message", "Usuario activado correctamente", "email", email));
	}

}
