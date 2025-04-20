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
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.IEmpresaRepository;
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
    private IEmpresaRepository empresaRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    protected ISolicitudRepository getRepository() {
        return solicitudRepo;
    }

    
    @Override
    public List<SolicitudResponseDto> buscarSolicitudes(Usuario usuario) {
    	
    	//Comprobamos que el usuario que llega no sea null
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario que llega de la sesión no llega correctamente");
        }

        //Creamos una lista de solicitudes que dependiendo del rol se le añadira
        //las solicitudes del usuario autenticado como cliente o
        //las solicitudes de que le han llegado a las vacante de la empresa
        List<Solicitud> solicitudes;

        //Si es rol cliente filtramos por las solicitudes que tiene como usuario
        if (usuario.getRol().equals("CLIENTE")) {
            solicitudes = solicitudRepo.findByUsuarioEmail(usuario.getEmail());
            
        //Si es rol EMPRESA filtramos por las solicitudes que tiene las vacantes dela empresa
        } else if (usuario.getRol().equals("EMPRESA")) {
            // Buscar la empresa vinculada al usuario
            Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())
                    .orElseThrow(() -> new RuntimeException("No se encontró la empresa del usuario"));

            // Buscar todas las solicitudes que se han hecho a sus vacantes
            solicitudes = solicitudRepo.findByVacante_Empresa_IdEmpresa(empresa.getIdEmpresa());
        
        //Si el rol es otro mandamos una excepcion
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol no autorizado");
        }

        //Devolvemos la respuesta como dto
        return solicitudes.stream().map(solicitud -> {
        	
            SolicitudResponseDto dto = mapper.map(solicitud, SolicitudResponseDto.class);
            
            //Buscamos la empresa de cada solicitud para poder añadirles los datos al dto
            Empresa empresa = empresaRepo.findByUsuario_Email(solicitud.getVacante().getEmpresa().getUsuario().getEmail())
                    .orElseThrow(() -> new RuntimeException("La empresa no puede ser null"));
            
            dto.setNombreEmpresa(empresa.getNombreEmpresa());
            dto.setNombreCategoria(solicitud.getVacante().getCategoria().getNombre());
            dto.setIdVacante(solicitud.getVacante().getIdVacante());
            dto.setSalario(solicitud.getVacante().getSalario());
            return dto;
            
        }).collect(Collectors.toList());
    }
    
    
    
    //METODO PARA BUSCAR SOLICITUDES DE UNA VACANTE
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
					dto.setNombreCategoria(solicitud.getVacante().getCategoria().getNombre());
					dto.setIdVacante(solicitud.getVacante().getIdVacante());
					dto.setSalario(solicitud.getVacante().getSalario());
					
				return dto;
				})
				.collect(Collectors.toList());

		return listaDto;
    }
    
    //METODO PARA ELIMINAR SOLICITUD
    @Override
    public SolicitudResponseDto eliminarSolicitud (int idSolicitud) {
    	
		//Buscamos la solicitud que nos llega
		Solicitud solicitud = solicitudRepo.findById(idSolicitud)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
		
		//La eliminamos " YA QUE NO SE PUEDE CANCELAR SOLO EXISTE ESTADO ADJUDICADA O PRESENTADA"
		solicitudRepo.deleteById(solicitud.getIdSolicitud());
		
		//Devolvemos la solicitud eliminada como un dto y le añadimos los datos extra de empresa para la respuesta
		SolicitudResponseDto respuestaDto =  mapper.map(solicitud, SolicitudResponseDto.class);
		respuestaDto.setNombreEmpresa(solicitud.getVacante().getEmpresa().getNombreEmpresa());
		respuestaDto.setNombreCategoria(solicitud.getVacante().getCategoria().getNombre());
		respuestaDto.setIdVacante(solicitud.getVacante().getIdVacante());
		respuestaDto.setSalario(solicitud.getVacante().getSalario());
		
		return respuestaDto;
    }
    

    
    //METODO PARA QUE EMPRESA ASIGNE LA SOLICITUD A UNA VACANTE
	@Override
	public void asignarVacante(int idSolicitud) {
		
		//Buscamos y guardamos la solicitud 
		Solicitud solicitud = solicitudRepo.findById(idSolicitud)
				.orElseThrow(() -> new RuntimeException("La solicitud no puede ser nula"));
		
		//Guardamos la vacante y comprobamos que no sea nula
		Vacante vacante = solicitud.getVacante();
		
		if(vacante == null) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La solicitud no peude ser nula");
		} else if("CUBIERTA".equals(vacante.getEstatus().name())){
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "La solicitud ya esta asginada");
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


	@Override
	public List<SolicitudResponseDto> buscarUltimasSolicitudes(Usuario usuario) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario que llega de la sesión no llega correctamente");
        }

        List<Solicitud> solicitudes;

        if (usuario.getRol().equals("CLIENTE")) {
            solicitudes = solicitudRepo.findTop5ByUsuarioEmailOrderByFechaDesc(usuario.getEmail());

        } else if (usuario.getRol().equals("EMPRESA")) {
            Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())
                    .orElseThrow(() -> new RuntimeException("No se encontró la empresa del usuario"));

            solicitudes = solicitudRepo.findTop5ByVacante_Empresa_IdEmpresaOrderByFechaDesc(empresa.getIdEmpresa());

        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Rol no autorizado");
        }

        //Devolvemos la respuesta como dto
        return solicitudes.stream().map(solicitud -> {
        	
            SolicitudResponseDto dto = mapper.map(solicitud, SolicitudResponseDto.class);
            
            //Buscamos la empresa de cada solicitud para poder añadirles los datos al dto
            Empresa empresa = empresaRepo.findByUsuario_Email(solicitud.getVacante().getEmpresa().getUsuario().getEmail())
                    .orElseThrow(() -> new RuntimeException("La empresa no puede ser null"));
            
            dto.setNombreEmpresa(empresa.getNombreEmpresa());
            dto.setNombreCategoria(solicitud.getVacante().getCategoria().getNombre());
            dto.setIdVacante(solicitud.getVacante().getIdVacante());
            dto.setSalario(solicitud.getVacante().getSalario());
            return dto;
            
        }).collect(Collectors.toList());
    }
	
	
	
}
