package com.fulljob.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.services.IUsuarioService;

@Service
public class IUsuarioServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements IUsuarioService{

	@Autowired 
	private IUsuarioRepository usuarioRepo;
	
	@Override
	protected IUsuarioRepository getRepository() {
		return usuarioRepo;
	}

}
