package com.fulljob.api.services.impl;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;

/**
 * Esta clase permite que Spring Security cargue los datos de un usuario cuando
 * alguien intenta iniciar sesión.
 * 
 * Implementa la interfaz UserDetailsService, que es la que Spring usa por
 * defecto para buscar usuarios en la base de datos cuando llega un login.
 * 
 * Lo que hacemos aquí paso a paso es:
 * 
 * 1. Buscar en la base de datos un usuario que tenga el email recibido. 2. Si
 * no existe, se lanza una excepción para avisar que no se ha encontrado. 3. Si
 * el usuario existe pero está desactivado (enabled = 0), también se lanza una
 * excepción. 4. Si todo está bien, se obtiene su rol (por ejemplo: CLIENTE,
 * EMPRESA, ADMON). 5. Se crea un objeto `User` de Spring Security con el email,
 * la contraseña y el rol con prefijo `ROLE_`.
 * 
 * Este objeto es el que Spring Security usa para verificar credenciales y dar
 * acceso.
 */
@Service
public class UsuarioDetallesServiceImpl implements UserDetailsService{

	@Autowired
	private IUsuarioRepository usuarioRepository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    // Buscamos el usuario en base de datos por su email
	    Usuario usuario = usuarioRepository.findById(email)
	            .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));

	    // Verificamos que no este deshabilitado el usuario
	    if (usuario.getEnabled() == null || usuario.getEnabled() == 0) {
	        throw new UsernameNotFoundException("El usuario esta deshabilitado ");
	    }

	    // Creamos una autoridad con el rol del usuario 
	    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toUpperCase());


	    // Asignamos las authorities manualmente al objeto Usuario
	    usuario.setAuthorities(Collections.singletonList(authority));

	    // Devolvemos el propio objeto Usuario, que implementa UserDetails
	    return usuario;
	}

}