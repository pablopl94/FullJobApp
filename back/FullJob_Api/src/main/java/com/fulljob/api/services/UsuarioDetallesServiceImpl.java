package com.fulljob.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;

@Service
public class UsuarioDetallesServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    /**
     * METODO PARA CARGAR EL USUARIO DE SRPINGSECURITY - USERDETAILSSERVICE
     * Autentica el usuario por id en este caso EMAIL.
     * Se usa durante el login para cargar el usuario desde la base de datos.
     * Si no existe o no esta enable, lanza excepción.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));

        if (!Boolean.TRUE.equals(usuario.getEnabled() == 1)) {
            throw new UsernameNotFoundException("El usuario: " + email + " no está habilitado.");
        }

        return usuario;
    }

}