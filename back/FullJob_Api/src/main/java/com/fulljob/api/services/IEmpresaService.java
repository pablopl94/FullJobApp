package com.fulljob.api.services;

import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.dto.UsuarioPasswordResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;

public interface IEmpresaService extends IGenericCrud<Empresa, Integer>{

	UsuarioPasswordResponseDto  altaEmpresa(AltaEmpresaRequestDto dto);
	
	Empresa findByUsuario(Usuario usuario);

}
