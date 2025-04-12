package com.fulljob.api.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.AltaClienteAdminRequestDto;
import com.fulljob.api.models.dto.AltaClienteAdminResponseDto;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.services.IUsuarioService;

@Service
public class IUsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	
	@Autowired
	private ModelMapper mapper;

	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepository;
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
