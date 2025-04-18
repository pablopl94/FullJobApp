package com.fulljob.api.restcontroller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.AltaClienteAdminRequestDto;
import com.fulljob.api.models.dto.AltaClienteAdminResponseDto;
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

	// ENDPOINT PARA MODIFICAR USUARIOS
	// PUT /categorias/{id} ...................... [ROLE_ADMON]
	@PutMapping("modificar/{id}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<AltaClienteAdminResponseDto> updateUsuario(
	        @PathVariable String id,
	        @RequestBody @Valid AltaClienteAdminRequestDto clienteDto) {

	    System.out.println("🔧 ID recibido (email): " + id);
	    System.out.println("📦 Datos recibidos: " + clienteDto.toString());

	    AltaClienteAdminResponseDto respuestaDto = iUsuarioService.actualizarDatosCliente(id, clienteDto);
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
	
	@DeleteMapping("/cliente/{email}")
	@PreAuthorize("hasRole('ADMON')")
	public ResponseEntity<?> eliminarUsuario(@PathVariable String email) {
	    iUsuarioService.eliminarPorEmail(email); // asegúrate de tener este método en tu servicio
	    return ResponseEntity.noContent().build();
	}
	
	  //ENDOPOINT PARA ADMINISTRADOR REGISTRE UN USUARIO CON ROL ADMIN
    //POST /auth/alta/administrador ...................... [ROLE_ADMON] 
    @PostMapping("/alta/admin")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<AltaClienteAdminResponseDto> altaAdministrador(@RequestBody @Valid AltaClienteAdminRequestDto clienteDto) {
    	
    	AltaClienteAdminResponseDto respuesta = iUsuarioService.altaAdministrador(clienteDto);
    	
    	
    	return ResponseEntity.ok(respuesta);
    	
    }
	
	
}
