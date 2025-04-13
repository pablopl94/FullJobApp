package com.fulljob.api.services;

import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;

import java.util.List;


public interface IEmpresaService extends IGenericCrud<Empresa, Integer>{
	
    List<Empresa> buscarPorNombre(String nombre);
    
    Empresa buscarPorEmail(String email);
    
	EmpresaResponseDto eliminarEmpresa(Usuario usuario);
	
	public Empresa save(Empresa empresa);
	    
	
}
