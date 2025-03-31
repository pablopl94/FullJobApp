package com.fulljob.api.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
public class UsuarioDetallesServiceImpl implements UserDetailsService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findById(email)
				.orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));

		if (usuario.getEnabled() == null || usuario.getEnabled() == 0) {
			throw new UsernameNotFoundException("El usuario está deshabilitado: " + email);
		}

		// Obtenemos el rol y lo adaptamos al formato que Spring Security
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().toUpperCase());

		// Retornamos una instancia de User (propia de Spring Security) con email,
		// contraseña y rol
		return new User(usuario.getEmail(), usuario.getPassword(), Collections.singletonList(authority));
	}
}
