package com.fulljob.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fulljob.api.auth.JwtUtils;
import com.fulljob.api.models.dto.RegistroRequestDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String>
        implements IUsuarioService {

	@Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected JpaRepository<Usuario, String> getRepository() {
        return usuarioRepository;
    }

    /**
     * METODO AUTENTIFICACION DEL USUARIO
     * Verifica si el usuario existe , si está habilitado y si la contraseña es correcta.
     * Si todo esto es valido , genera un token JWT que el usuario usara para la solicitudes.
     */
    public String auth(String email, String password) {
        Usuario usuario = usuarioRepository.findById(email)
                .filter(u -> u.getEnabled() != null && u.getEnabled() == 1)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas o usuario no habilitado"));

        return jwtUtils.generateToken(usuario);

    }


    /**
     * METODO ALTA DE CLIENTE
     * Registra un nuevo candidato desde el formulario de la zona que hemos dicho que es PÚBLICA.
     * Se valida que el email no exista ya, se codifica la contraseña y se guarda el usuario con rol CLIENTE.
     */
    @Override
    public Usuario altaCandidato(RegistroRequestDto dto) {
        if (usuarioRepository.existsById(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya esta dado de alta.");
        }

        String passwordCodificada = passwordEncoder.encode(dto.getPassword());

        Usuario nuevoUsuario = Usuario.builder()
                .email(dto.getEmail())
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .password(passwordCodificada)
                .enabled(1)
                .fechaRegistro(LocalDate.now())
                .rol("CLIENTE")
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }

}
