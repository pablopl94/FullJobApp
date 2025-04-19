package com.fulljob.api.services;

import com.fulljob.api.models.dto.AltaClienteRequestDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.dto.AltaEmpresaResponseDto;
import com.fulljob.api.models.dto.LoginRequestDto;
import com.fulljob.api.models.dto.LoginResponseDto;
import com.fulljob.api.models.entities.Usuario;


public interface IAuthService  extends IGenericCrud<Usuario, String> {

	LoginResponseDto login(LoginRequestDto loginDto);
	
    AltaClienteResponseDto altaCandidato(AltaClienteRequestDto dto);
    
    AltaEmpresaResponseDto altaEmpresa(AltaEmpresaRequestDto dto);

}