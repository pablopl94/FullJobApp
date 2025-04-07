package com.fulljob.api.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.auth.JwtUtils;
import com.fulljob.api.models.dto.AltaClienteRequestDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.dto.AltaEmpresaResponseDto;
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
            // Autenticamos al usuario con el método de Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            // Guardamos la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtenemos el usuario autenticado desde el contexto (ya cargado por Spring)
            Usuario usuario = (Usuario) authentication.getPrincipal();

            // Como en la entidad Usuario ya tenemos puesto que por defecto sea enabled = 1,
            // no hace falta comprobar otra vez que esté activo el usuario

            // Generamos el token con el usuario que hemos obtenido del contexto
            String token = jwtUtils.generateToken(usuario);

            // Se lo metemos al Dto de respuesta con los datos del usuario y lo devolvemos para sacarlo por el controller
            return new LoginResponseDto(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol(),
                token
            );

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
            
            //Le agregamos la autoridad rol
            nuevoUsuario.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));
            
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
    
    
    /**
     * ALTA DE UNA EMPRESA
     *( CREAMOS USUARIO NUEVO 
     */
    @Override
    @Transactional
    public AltaEmpresaResponseDto altaEmpresa(AltaEmpresaRequestDto dto) {
        try {
        	//comprobamos que no exista el usuario
            if (usuarioRepository.existsById(dto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado.");
            }

            //Generamos la contraseña y la encriptamos
            String contraseña = passwordEncoder.encode(PasswordGenerator.generarPasswordAleatoria(10));
            
            //Creamos un usuario nuevo con builder y guardamos:
            //Le añadimos el rol CLIENTE
            //Le añadimos la contraseña que acabamos de crear e incriptar
            //Le metemos la fecha de hoy a la fecha registro
            Usuario nuevoUsuario = Usuario.builder()
                    .email(dto.getEmail())
                    .nombre(dto.getNombre())
                    .apellidos(dto.getApellidos())
                    .password(contraseña)
                    .enabled(1)
                    .fechaRegistro(LocalDate.now())
                    .rol("EMPRESA")
                    .build();

            usuarioRepository.save(nuevoUsuario);
            
            //Creamos una empresa y la guardamos
            //Le añadimos el rol CLIENTE
            //Encriptamos la contraseña con el metodo que tenemos en springsecurityconfg
            //Le metemos la fecha de hoy a la fecha registro
            Empresa nuevaEmpresa = Empresa.builder()
            		.cif(dto.getCif())
            		.nombreEmpresa(dto.getNombreEmpresa())
            		.direccionFiscal(dto.getDireccionFiscal())
            		.pais(dto.getPais())
            		.usuario(nuevoUsuario)
                    .build();

            empresaRepository.save(nuevaEmpresa);     
            
            //Le añadimos la autoridad del rol
            nuevoUsuario.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_EMPRESA")));

            
            //Generamos el token con el usuario que hemos creado
            //Aqui generamos el token por si nada mas regisrarnos queremos
            //logearnos y entrar en la app
            String token = jwtUtils.generateToken(nuevoUsuario);
          
            //Creamos la respuesta Dto
            //Mapeamos los datos del cliente y los de empresa lo metemos manual + el token
            AltaEmpresaResponseDto respuesta= mapper.map(nuevoUsuario, AltaEmpresaResponseDto.class);
            respuesta.setCif(nuevaEmpresa.getCif());
            respuesta.setDireccionFiscal(nuevaEmpresa.getDireccionFiscal());
            respuesta.setNombreEmpresa(nuevaEmpresa.getNombreEmpresa());
            respuesta.setPais(nuevaEmpresa.getPais()); 
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

}
