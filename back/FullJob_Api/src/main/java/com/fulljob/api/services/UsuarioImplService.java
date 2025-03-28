package com.fulljob.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;

@Service
public class UsuarioImplService extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepo;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Usuario, String> getRepository() {
		return usuarioRepo;
	}



}
