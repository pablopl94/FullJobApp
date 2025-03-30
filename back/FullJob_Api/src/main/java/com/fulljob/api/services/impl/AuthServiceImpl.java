package com.fulljob.api.services.impl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.auth.JwtUtils;
import com.fulljob.api.models.dto.AltaClienteRequestDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.dto.LoginRequestDto;
import com.fulljob.api.models.dto.LoginResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.services.IAuthService;
import com.fulljob.api.utils.PasswordGenerator;

/**
 * En esta clase que implementa IAuthService queremos agrupar toda la logica de login y altas
 */
@Service
public class AuthServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IAuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Autowired
    private IEmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private ModelMapper mapper;

    // Especificamos qué repositorio se usa para el CRUD genérico
    @Override
    protected JpaRepository<Usuario, String> getRepository() {
        return usuarioRepository;
    }

    /**
     * METODO DE LOGIN DE USUARIO 
     * ( AUTENTICAMOS CON SPRING SECURITY + GENERAMOS TOKEN + DEVOLVEMOS RESPUESTA )
     */
    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        try {
        	//Autenticamos al usuario con el metodo de SpringSecurity 
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Buscamos el usuario con el email que nos llega en el dto y lo guardamos 
            Usuario usuario = usuarioRepository.findById(loginDto.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            //Como en la entidad usuario ya tenemos puesto que por defecto sea enabled = 1 
            //no hace falta comprobar otra vez que este activo el usuario
            
            //Generamos el token con el usuario que hemos guardado
            String token = jwtUtils.generateToken(usuario);
            
            //Se lo metemos al Dto de respuesta con los datos del usuario y lo devolvemos para sacarlo por el controller
            return new LoginResponseDto(usuario.getEmail(),usuario.getNombre(),usuario.getRol(),token);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email o contraseña incorrectos");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado en el login: " + e.getMessage()
            );
        }
    }

    /**
     * ALTA DE UN CLIENTE
     *( CREAMOS USUARIO NUEVO 
     */
    @Override
    public AltaClienteResponseDto altaCandidato(AltaClienteRequestDto dto) {
        try {
        	//comprobamos que no exista el usuario
            if (usuarioRepository.existsById(dto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado.");
            }

            
            //Creamos un usuario nuevo con builder y guardamos:
            //Le añadimos el rol CLIENTE
            //Encriptamos la contraseña con el metodo que tenemos en springsecurityconfg
            //Le metemos la fecha de hoy a la fecha registro
            Usuario nuevoUsuario = Usuario.builder()
                    .email(dto.getEmail())
                    .nombre(dto.getNombre())
                    .apellidos(dto.getApellidos())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .enabled(1)
                    .fechaRegistro(LocalDate.now())
                    .rol("CLIENTE")
                    .build();

            usuarioRepository.save(nuevoUsuario);
            
            //Generamos el token con el usuario que hemos creado
            //Aqui generamos el token por si nada mas regisrarnos queremos
            //logearnos y entrar en la app
            String token = jwtUtils.generateToken(nuevoUsuario);
          
            AltaClienteResponseDto respuesta = mapper.map(nuevoUsuario,AltaClienteResponseDto.class);
            respuesta.setToken(token);
            
            //Devolvemos una respuesta en dto ( mas seguro )
            return respuesta;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al registrar al cliente: " + e.getMessage()
            );
        }
    }
    
	@Override
	@Transactional
	public UsuarioPasswordResponseDto  altaEmpresa(AltaEmpresaRequestDto dto) {
        if (usuarioRepository.existsById(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya esta dado de alta.");
        }
        
        String contraseña = PasswordGenerator.generarPasswordAleatoria(10);
		
        Usuario cliente = Usuario.builder()
                .email(dto.getEmail())
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .password(passwordEncoder.encode(contraseña))
                .enabled(1)
                .fechaRegistro(LocalDate.now())
                .rol("EMPRESA")
                .build();

       		usuarioRepository.save(cliente);
		
	    Empresa empresa = Empresa.builder()
	            .cif(dto.getCif())
	            .nombreEmpresa(dto.getNombreEmpresa())
	            .direccionFiscal(dto.getDireccionFiscal())
	            .pais(dto.getPais())
	            .usuario(cliente)
	            .build();
	    
	     empresaRepository.save(empresa);
	     
	     	return UsuarioPasswordResponseDto .builder()
	                .usuario(cliente)
	                .passwordGenerada(contraseña)
	                .build();
	}
}
