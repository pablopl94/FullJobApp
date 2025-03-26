package com.fulljob.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fulljob.api.entities.Usuario;
import com.fulljob.api.repository.IUsuarioRepository;

@Service
public class UsuarioImplService implements IUsuarioService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario insertOne(Usuario entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario updatetOne(Usuario entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOne(String id) {
		// TODO Auto-generated method stub
		return;
	}

}
