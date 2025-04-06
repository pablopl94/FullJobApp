package com.fulljob.api.services;

import java.util.List;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;

public interface ISolicitudService extends IGenericCrud<Solicitud, Integer> {

	List<SolicitudResponseDto> listarSolicitudesUsuario(Usuario usuario);


}
