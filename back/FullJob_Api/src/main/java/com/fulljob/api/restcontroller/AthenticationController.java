package com.fulljob.api.restcontroller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class AthenticationController {

    @Autowired
    private IAuthService authService;


    //METODO CON RUTA PARA INICIAR SESION
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginSesion(@RequestBody @Valid LoginRequestDto loginDto) {
    	
    	//Hacemos login con el metodo del service y guardamos las respuesta Dto que devuelve
        LoginResponseDto response = authService.login(loginDto);
        
        return ResponseEntity.ok(response);
    }
    
    
    //METODO CON RUTA PARA CERRAR SESION
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> cerrarSesion() {
    	
    	// Con este metodo de Spring Security borramos la informacion de usuario autenticado
        SecurityContextHolder.clearContext();
        
        //IMPORTANTE: Hay que borrar el token en Front ya que se podria volver a usar
        //para recuperar la sesion
        
        return ResponseEntity.ok(Map.of("mensaje", "Se ha cerrado sesión correctamente"));
    }

    
    //METODO CON RUTA PARA REGISTRAR UN USUARIO
    @PostMapping("/registro")
    public ResponseEntity<AltaClienteResponseDto> registroUsuario(@RequestBody @Valid AltaClienteRequestDto registroDto) {
    	
    	//Damos de alta el usuario,los objetivos y guardamos la respuesta con el metodo del servicio
    	//Todas las excepciones se controlan en el service tambien
    	AltaClienteResponseDto respuesta = authService.altaCandidato(registroDto);
    	
    	
    	
    	return ResponseEntity.ok(respuesta);
    	
    }
}
