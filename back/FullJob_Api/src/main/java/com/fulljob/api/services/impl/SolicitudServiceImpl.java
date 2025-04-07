package com.fulljob.api.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.services.ISolicitudService;

@Service
public class SolicitudServiceImpl extends GenericCrudServiceImpl<Solicitud, Integer> implements ISolicitudService {

	@Autowired
    private ISolicitudRepository solicitudRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    protected ISolicitudRepository getRepository() {
        return solicitudRepo;
    }

    @Override
    public List<Solicitud> findByUsuarioEmail(String email) {
        return solicitudRepo.findByUsuarioEmail(email);
    }

    @Override
    public Optional<Solicitud> findByVacanteAndUsuario(Vacante vacante, Usuario usuario) {
        return solicitudRepo.findByVacanteAndUsuario(vacante, usuario);
    }

    @Override
    public List<Solicitud> findByVacante(Vacante vacante) {
        return solicitudRepo.findByVacante_IdVacante(vacante.getIdVacante());
    }
    
    
    @Override
    public SolicitudResponseDto eliminarSolicitud (int idSolicitud) {
    	
		//Buscamos la solicitud que nos llega
		Solicitud solicitud = solicitudRepo.findById(idSolicitud)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
		
		//La eliminamos " YA QUE NO SE PUEDE CANCELAR SOLO EXISTE ESTADO ADJUDICADA O PRESENTADA"
		solicitudRepo.deleteById(solicitud.getIdSolicitud());
		
		//Devolvemos la solicitud eliminada como un dto
		return mapper.map(solicitud, SolicitudResponseDto.class);
    }


}
