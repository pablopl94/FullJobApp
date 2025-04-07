package com.fulljob.api.services;

import java.util.List;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;

public interface ISolicitudService extends IGenericCrud<Solicitud, Integer> {

	
    List<Solicitud> findByUsuarioEmail(String email);

	SolicitudResponseDto eliminarSolicitud(int idSolicitud);

	List<SolicitudResponseDto> findByVacante(int idVacante);

	void asignarVacante(int idSolicitud);

}
