package com.fulljob.api.restcontroller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.fulljob.api.models.dto.*;
import com.fulljob.api.services.IAuthService;


import jakarta.validation.Valid;

/**
 * Este controlador lo hemos creado para gestionar todo lo relacionado con la autenticación:
 * iniciar sesión, cerrar sesión, registrar un nuevo candidato y obtener la información del usuario logueado.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@EnableMethodSecurity(prePostEnabled = true)
public class AthenticationController {

    @Autowired
    private IAuthService authService;


    //ENDPOINT PARA INICIAR SESION
    //POST /auth/login ............................. [permitAll]
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginSesion(@RequestBody @Valid LoginRequestDto loginDto) {
    	
    	//Hacemos login con el metodo del service y guardamos las respuesta Dto que devuelve
        LoginResponseDto response = authService.login(loginDto);
        
        return ResponseEntity.ok(response);
    }
    
    
    //ENDPOINT PARA CERRAR SESION
    //POST /auth/logout ...................... [permitAll]
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> cerrarSesion() {
    	
    	// Con este metodo de Spring Security borramos la informacion de usuario autenticado
        SecurityContextHolder.clearContext();
        
        //IMPORTANTE: Hay que borrar el token en Front ya que se podria volver a usar
        //para recuperar la sesion
        
        return ResponseEntity.ok(Map.of("mensaje", "Se ha cerrado sesión correctamente"));
    }

    
    //ENDPOINT PARA DAR DE ALTA UN CLIENTE
    //POST /auth/alta/cliente ...................... [permitAll]
    @PostMapping("/alta/cliente")
    public ResponseEntity<AltaClienteResponseDto> altaCliente(@RequestBody @Valid AltaClienteRequestDto clienteDto) {
    	
    	AltaClienteResponseDto respuesta = authService.altaCandidato(clienteDto);
    	
    	
    	return ResponseEntity.ok(respuesta);
    	
    }
    
    
    //ENDOPOINT PARA REGISTRAR UNA EMPRESA + USUARIO
    //POST /auth/alta/empresa ...................... [ROLE_ADMON] 
    @PostMapping("/alta/empresa")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<AltaEmpresaResponseDto> altaEmpresa(@RequestBody @Valid AltaEmpresaRequestDto empresaDto) {
    	
    	AltaEmpresaResponseDto respuesta = authService.altaEmpresa(empresaDto);
    		
    	return ResponseEntity.ok(respuesta);
    	
    }
    

}
