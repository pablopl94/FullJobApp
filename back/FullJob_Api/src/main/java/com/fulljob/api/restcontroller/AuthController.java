package com.fulljob.api.restcontroller;

import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fulljob.api.models.dto.*;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.IUsuarioService;

import jakarta.validation.Valid;

/**
 * Este controlador lo hemos creado para gestionar todo lo relacionado con la autenticación:
 * iniciar sesión, cerrar sesión, registrar un nuevo candidato y obtener la información del usuario logueado.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método que hemos creado sirve para iniciar sesión.
     *
     * Lo que hace es lo siguiente:
     * 1. Recibe el email y la contraseña del usuario.
     * 2. Si los datos son correctos, genera un token JWT (que es como una "llave digital").
     * 3. Busca los datos del usuario en la base de datos.
     * 4. Devuelve tanto el token como los datos del usuario en la respuesta.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> iniciarSesion(@RequestBody @Valid LoginRequestDto loginDto) {
        String token = usuarioService.auth(loginDto.getEmail(), loginDto.getPassword());
        Optional<Usuario> usuario = usuarioService.findById(loginDto.getEmail());

        LoginResponseDto respuesta = modelMapper.map(usuario, LoginResponseDto.class);
        respuesta.setToken(token);

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Este método lo hemos creado para cerrar sesión.
     *
     * Lo que hace es:
     * 1. Limpia el contexto de seguridad de Spring, que es donde se guarda el usuario que está logueado.
     * 2. Devuelve un mensaje diciendo que la sesión se ha cerrado correctamente.
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("mensaje", "Se ha cerrado sesión correctamente"));
    }

    /**
     * Este método lo hemos creado para registrar un nuevo candidato en la aplicación.
     *
     * Lo que hace es:
     * 1. Recibe los datos del nuevo usuario (nombre, apellidos, email, etc.).
     * 2. Crea el usuario con el rol "CLIENTE".
     * 3. Guarda ese usuario en la base de datos.
     * 4. Devuelve los datos del usuario que se acaba de registrar.
     */
    @PostMapping("/alta/candidato")
    public ResponseEntity<LoginResponseDto> registrarUsuario(@RequestBody @Valid RegistroRequestDto registroDto) {
        Usuario nuevoUsuario = usuarioService.altaCandidato(registroDto);
        LoginResponseDto respuesta = modelMapper.map(nuevoUsuario, LoginResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * Este método lo hemos creado para obtener los datos del usuario que está logueado en ese momento.
     *
     * Lo que hace es:
     * 1. Accede al contexto de seguridad, donde está guardado el usuario autenticado.
     * 2. Extrae sus datos (nombre, email, rol...).
     * 3. Los devuelve como respuesta.
     */
    @GetMapping("/obtenerusuario")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuario() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UsuarioResponseDto dto = modelMapper.map(usuario, UsuarioResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
