package com.fulljob.api.services;

import java.util.List;
import java.util.Optional;

import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;

public interface ISolicitudService extends IGenericCrud<Solicitud, Integer> {

	
    List<Solicitud> findByUsuarioEmail(String email);

    Optional<Solicitud> findByVacanteAndUsuario(Vacante vacante, Usuario usuario);

    List<Solicitud> findByVacante(Vacante vacante);

}
