package com.fulljob.api.services;

import java.util.List;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;

public interface ISolicitudService extends IGenericCrud<Solicitud, Integer> {

	SolicitudResponseDto eliminarSolicitud(int idSolicitud);

	List<SolicitudResponseDto> findByVacante(int idVacante);

	void asignarVacante(int idSolicitud);

	List<SolicitudResponseDto> buscarSolicitudes(Usuario usuario);

	List<SolicitudResponseDto> buscarUltimasSolicitudes(Usuario usuario);

}
