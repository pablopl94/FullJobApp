package com.fulljob.api.services;

import com.fulljob.api.models.dto.EmpresaRegistroRequestDto;
import com.fulljob.api.models.dto.UsuarioPasswordResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;

public interface IEmpresaService extends IGenericCrud<Empresa, Integer>{

	UsuarioPasswordResponseDto  altaEmpresa(EmpresaRegistroRequestDto dto, Usuario usuario);
	
	Empresa findByUsuario(Usuario usuario);

}
