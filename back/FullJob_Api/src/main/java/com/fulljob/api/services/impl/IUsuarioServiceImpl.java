package com.fulljob.api.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.auth.JwtUtils;
import com.fulljob.api.models.dto.AltaClienteAdminRequestDto;
import com.fulljob.api.models.dto.AltaClienteAdminResponseDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.services.IUsuarioService;

@Service
public class IUsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepository;
	}

	
    
    /**
     * ALTA DE UN CLIENTE
     *( CREAMOS USUARIO NUEVO 
     */
    @Override
    public AltaClienteAdminResponseDto altaCandidatoConRol(AltaClienteAdminRequestDto dto) {
    	
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
                    .rol(dto.getRol())
                    .build();

            usuarioRepository.save(nuevoUsuario);
            
            //Le agregamos los permisos del rol de la clase de security userdetails
            String rolNormalizado = dto.getRol().toUpperCase();

            nuevoUsuario.setAuthorities(
                List.of(new SimpleGrantedAuthority("ROLE_" + rolNormalizado))
            );
            
          
            AltaClienteAdminResponseDto respuesta = mapper.map(nuevoUsuario,AltaClienteAdminResponseDto.class);
            
            //Devolvemos una respuesta en dto ( mas seguro )
            return respuesta;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error inesperado al registrar al cliente: " + e.getMessage()
            );
        }
    }
	
    
    @Override
    public AltaClienteAdminResponseDto actualizarDatosCliente (String email, AltaClienteAdminRequestDto clienteDto) {
    	
    	//Buscamos el usuario y lo guardamos
    	Usuario usuario = usuarioRepository.findById(email)
				.orElseThrow(() -> new RuntimeException("UsUario con email " + email + " no encontrado"));
    	
    	//pasamos del dto al usaurio los datos
    	mapper.map(clienteDto, usuario);
    	
    	//Comprobamos que existe y lo actualizamos
    	if(usuarioRepository.existsById(usuario.getEmail())) {
    		
    		usuarioRepository.save(usuario);
    	}else {
    		 throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario no existe");
    	}
    	
    	//devolvemos un dto como respuesta
    	return mapper.map(usuario, AltaClienteAdminResponseDto.class);
    }
    
    
	@Override
	public void cambiarEstadoUsuario(String email, Integer estadoBaja) {

		Usuario usuario = usuarioRepository.findById(email)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		
		usuario.setEnabled(estadoBaja);
        usuarioRepository.save(usuario);
	}


}
