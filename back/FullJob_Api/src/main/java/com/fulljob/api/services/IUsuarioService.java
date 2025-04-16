package com.fulljob.api.services;

import com.fulljob.api.models.dto.AltaClienteAdminRequestDto;
import com.fulljob.api.models.dto.AltaClienteAdminResponseDto;
import com.fulljob.api.models.entities.Usuario;

public interface IUsuarioService extends IGenericCrud<Usuario, String>{

	AltaClienteAdminResponseDto actualizarDatosCliente(String email, AltaClienteAdminRequestDto clienteDto);

	void cambiarEstadoUsuario(String email, Integer estadoBaja);
	
	void eliminarPorEmail(String email);
}

