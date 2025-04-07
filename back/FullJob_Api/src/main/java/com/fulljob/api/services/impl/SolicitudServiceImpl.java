package com.fulljob.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.EstadoSolicitud;
import com.fulljob.api.models.entities.EstadoVacante;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.ISolicitudService;

@Service
public class SolicitudServiceImpl extends GenericCrudServiceImpl<Solicitud, Integer> implements ISolicitudService {

	@Autowired
    private ISolicitudRepository solicitudRepo;
	
	@Autowired
    private IVacanteRepository vacanteRepo;

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
    public List<SolicitudResponseDto> findByVacante(int idVacante) {
    	
    	//Buscamos y guardamos la vacante
    	Vacante vacante = vacanteRepo.findById(idVacante)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacante no encontrada"));

		//Guardamos la empresa
		Empresa empresa = vacante.getEmpresa();
		
		
		// Obtener todas las solicitudes relacionadas con la vacante
		List<Solicitud> listaSolicitudes = solicitudRepo.findByVacante_idVacante(vacante.getIdVacante());
		
		if(listaSolicitudes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay solicitudes de la vacante ");
		}


		// Convertir las entidades a DTOs y asegurar se meten los atributos de las relaciones 
		List<SolicitudResponseDto> listaDto = listaSolicitudes.stream()
				.map(solicitud -> { 
					
					SolicitudResponseDto dto = mapper.map(solicitud, SolicitudResponseDto.class);
				
					dto.setNombreEmpresa(empresa.getNombreEmpresa());
					
				return dto;
				})
				.collect(Collectors.toList());

		return listaDto;
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

    
    //METODO PARA QUE EMPRESA ASIGNE LAS VACANTES A TRAVES DE LA SOLICITUDES
	@Override
	public void asignarVacante(int idSolicitud) {
		
		//Buscamos y guardamos la solicitud 
		Solicitud solicitud = solicitudRepo.findById(idSolicitud)
				.orElseThrow(() -> new RuntimeException("La solicitud no puede ser nula"));
		
		//Guardamos la vacante y comprobamos que no sea nula
		Vacante vacante = solicitud.getVacante();
		
		if(vacante == null) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La solicitud no peude ser nula");
		}
		
		//Le cambiamos los estados a la solicitud y a la vacante
		solicitud.setEstado(EstadoSolicitud.ADJUDICADA);
		vacante.setEstatus(EstadoVacante.CUBIERTA);
		
		//Comprobamos que existen para actualizar
		if(solicitudRepo.existsById(solicitud.getIdSolicitud())){
			
			solicitudRepo.save(solicitud);
			
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La solicitud no existe");
		}

		if(vacanteRepo.existsById(vacante.getIdVacante())) {
			vacanteRepo.save(vacante);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La solicitud no existe");
		}
		
		
	}

    
    

}
