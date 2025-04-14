package com.fulljob.api.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.SolicitudRequestDto;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.dto.VacanteRequestDto;
import com.fulljob.api.models.dto.VacanteResponseDto;
import com.fulljob.api.models.entities.Categoria;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.EstadoVacante;
import com.fulljob.api.models.entities.Solicitud;
import com.fulljob.api.models.entities.TipoDeContrato;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.ICategoriaRepository;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.IVacanteService;

@Service
public class VacanteServiceImpl extends GenericCrudServiceImpl<Vacante, Integer> implements IVacanteService {

	@Autowired
	private IVacanteRepository vacanteRepo;
	
	@Autowired
	private IEmpresaRepository empresaRepo;
	
	@Autowired
	private ICategoriaRepository categoriaRepo;
	
	@Autowired
	private ISolicitudRepository solicitudRepo;
	
	@Autowired
	private ModelMapper mapper;;

	@Override
	protected IVacanteRepository getRepository() {
		return vacanteRepo;
	}

	//Metodo para ver todas las vancantes activas
	@Override
	public List<VacanteResponseDto> findAllVacanteActivas() {
		
		//Usamos el metodo del IGenericCrud porque ya tiene implementada las excepciones
		//Guardamos en una lista todas las vacantes
		List<Vacante> listaVacantes = findAll();
		
		//Devolvemos la lista filtrada por las que tienen el estado creada
		//Mapeamos vancate a dto de respuesta
		//Lo volvemos a meter en una lista
		return listaVacantes.stream() 
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
		            VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
		            
		            // Añadimos los valores de las relaciones al dto
		            dto.setNombreCategoria(vacante.getCategoria().getNombre());
		            dto.setNombreEmpresa(vacante.getEmpresa().getNombreEmpresa());			
		            
		            return dto;
		        })
		        .collect(Collectors.toList());
	}

	
	//Metodo para filtrar vacantes por nombre de la empresa
	@Override
	public List<VacanteResponseDto> filtrarVacantesEmpresa(String empresa) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_NombreEmpresa(empresa);
		if (listaVacantes == null || listaVacantes.isEmpty()) {
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La" + empresa + "no tiene vacantes");
		}
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	//Metodo para filtrar vacantes por categoria
	@Override
	public List<VacanteResponseDto> filtrarVacantesCategoria(int idCategoria) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByCategoria_idCategoria(idCategoria);
		if (listaVacantes == null || listaVacantes.isEmpty()) {
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay vacantes de la categoria " + idCategoria);
		}
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}

	
	//Metodo para filtrar vacantes por TIPO DE CONTRATO = detalles
	@Override
	public List<VacanteResponseDto> filtrarVacantesTipoContrato(TipoDeContrato detalles) {
		
		List<Vacante> listaVacantes =  vacanteRepo.findByDetalles(detalles);
		
	
		return listaVacantes.stream()
				//Aqui filtramos por empresa por las que tiene el estado activo
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	
	//Metodo para ver las vacantes de una empresa
	@Override
	public List<VacanteResponseDto> obtenerVacantesDeEmpresaCreadas(Usuario usuario) {
		
		//Obtenemos la empresa del usuario que nos llega por paramentro(obtenido de la sesion)
		Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())
				 .orElseThrow(() -> new RuntimeException("La empresa no existe"));
		
		//Con la empresa sacamos una lista de sus vacantes
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_IdEmpresa(empresa.getIdEmpresa());
		
		return listaVacantes.stream()
				//filtramos solo por las vacantes CREADAS
				.filter(vacante -> "CREADA".equals(vacante.getEstatus().name()))
				//Mapeamos la entidades vacantes que hay en  lista
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	
	//Metodo para ver las vacantes de una empresa
	@Override
	public List<VacanteResponseDto> obtenerVacantesDeEmpresaAsignadas(Usuario usuario) {
		
		//Obtenemos la empresa del usuario que nos llega por paramentro(obtenido de la sesion)
		Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())
				 .orElseThrow(() -> new RuntimeException("La empresa no existe"));
		
		//Con la empresa sacamos una lista de sus vacantes
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_IdEmpresa(empresa.getIdEmpresa());
		
		return listaVacantes.stream()
				//filtramos solo por las vacantes CREADAS
				.filter(vacante -> "CUBIERTA".equals(vacante.getEstatus().name()))
				//Mapeamos la entidades vacantes que hay en  lista
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	//Metodo para ver las vacantes de una empresa
	@Override
	public List<VacanteResponseDto> obtenerVacantesDeEmpresaCanceladas(Usuario usuario) {
		
		//Obtenemos la empresa del usuario que nos llega por paramentro(obtenido de la sesion)
		Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())
				 .orElseThrow(() -> new RuntimeException("La empresa no existe"));
		
		//Con la empresa sacamos una lista de sus vacantes
		List<Vacante> listaVacantes =  vacanteRepo.findByEmpresa_IdEmpresa(empresa.getIdEmpresa());
		
		return listaVacantes.stream()
				//filtramos solo por las vacantes CREADAS
				.filter(vacante -> "CANCELADA".equals(vacante.getEstatus().name()))
				//Mapeamos la entidades vacantes que hay en  lista
				.map(vacante -> {
					
					VacanteResponseDto dto = mapper.map(vacante, VacanteResponseDto.class);
					//Aqui  metemos este atributo a la relacion de vacante
					dto.setNombreCategoria(vacante.getCategoria().getNombre());
					
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	
	
	//Metodo para que las empresas publiquen vacantes
	@Override
	public VacanteResponseDto publicarVacante(VacanteRequestDto vacanteDto, Usuario usuario) {
	    
	    //Buscamos y guardamos los datos de la empresa autenticada en un objeto de su clase
	    Empresa empresa = empresaRepo.findByUsuario_Email(usuario.getEmail())       
	    			.orElseThrow(() -> new RuntimeException("La empresa no existe"));
	    
	    //Buscamos la categoria con el idCategoria que nos llega del dto
	    Categoria categoria = categoriaRepo.findById(vacanteDto.getIdCategoria())
	    		 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se ha encontrado"));

	    //Creamos un nuevo objeto de la clase Vacante - mapeando me trae el id y me da error 
	    Vacante nuevaVacante = Vacante.builder()
	    		.categoria(categoria)
	    		.empresa(empresa)
	    		.nombre(vacanteDto.getNombre())
	    		.descripcion(vacanteDto.getDescripcion())
	    		.detalles(vacanteDto.getDetalles())
	    		.salario(vacanteDto.getSalario())
	    		.fecha(LocalDate.now())
	    		.destacado(vacanteDto.getDestacado())
	    		.imagen(vacanteDto.getImagen())
	    		//El estado por defecto es CREADO
	    		.build();
	    		 		
	    //Comprobamos que no existe la vacante y si no existe la guardamos
	    if(vacanteRepo.existsById(nuevaVacante.getIdVacante())) {
	    	throw new ResponseStatusException(HttpStatus.CONFLICT, "La vacante ya existe con ese ID");
	    }else {
	    	vacanteRepo.save(nuevaVacante);
	    }	  
	    
	    //Creamos un dto de respuesta de vacante
	    VacanteResponseDto respuestaDto = mapper.map(nuevaVacante, VacanteResponseDto.class);

	    return respuestaDto;
	}
	
	//Metodo para que las empresas editen vacantes
	@Override
	public VacanteResponseDto editarVacante (int idVacante, VacanteRequestDto vacanteDto) {
		
		//Buscamos la vacante que se esta editando y la guardamos en un objeto de su clase
		Vacante vacanteExistente = findById(idVacante)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La vacante no se ha encontrado"));
		
		//Buscamos su categoria y la guardamos
	    Categoria categoria = categoriaRepo.findById(vacanteDto.getIdCategoria())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

	    //Le metemos todos los datos nuevos a la vacante
	    vacanteExistente.setNombre(vacanteDto.getNombre());
	    vacanteExistente.setDescripcion(vacanteDto.getDescripcion());
	    vacanteExistente.setSalario(vacanteDto.getSalario());
	    vacanteExistente.setDestacado(vacanteDto.getDestacado());
	    vacanteExistente.setDetalles(vacanteDto.getDetalles());
	    vacanteExistente.setImagen(vacanteDto.getImagen());
	    vacanteExistente.setCategoria(categoria);

	  
	   //Comprobamos que no existe la vacante y si es asi  la guardamos
		    if(vacanteRepo.existsById(vacanteExistente.getIdVacante())) {
		    	updateOne(vacanteExistente); //Usamos el metodo de IGenericCrud con validaciones extras
		    }else {
		    	throw new ResponseStatusException(HttpStatus.CONFLICT, "La vacante no existe");
		    }	
		        
	    //Devolvemos un dto de respuesta
	    return mapper.map(vacanteExistente,VacanteResponseDto.class);
	}

	
	@Override
	public void cancelarVacante (int idVacante) {
		 
		//Buscamos la vacante y la guardamos en un objeto
		Vacante vacante = findById(idVacante)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La vacante no existe"));
		
		//Comprobamos si existe o sino lanzamos excepciones
		try {
		    if(vacanteRepo.existsById(vacante.getIdVacante())) {
		    	//Si existe le cambiamos el estado y actualizamos la vacante
		    	vacante.setEstatus(EstadoVacante.CANCELADA);
		    	updateOne(vacante);
		    	
		    }else {
		    	throw new ResponseStatusException(HttpStatus.CONFLICT, "La vacante no existe");
		    }	
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al cancelar la vacante", e);
		}
	}
	
	
	@Override
	public SolicitudResponseDto inscribirseVacante (int idVacante, Usuario usuario, SolicitudRequestDto solicitudDto) {
		
		//Comprobamos que usuario no sea null
		if(usuario == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		
		//Buscamos la vacante a la que el usuario se va a inscribir y la guardamos
		Vacante nuevaVacante = vacanteRepo.findById(idVacante)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La vacante no existe"));
		
		//Creamos la nueva solicitud metiendo los datos que nos llegan del dto
		//El estado de la solicitud ya viene por defecto por pendiente por lo que no hay que añadirlo
		Solicitud nuevaSolicitud = Solicitud.builder()
				.usuario(usuario)
				.vacante(nuevaVacante)
				.archivo(solicitudDto.getArchivo())
				.curriculum(solicitudDto.getCurriculum())
				.comentarios(solicitudDto.getComentarios())
				.fecha(LocalDate.now())
				.build();
		
		//Comprobamos que no exista ya una solicitud de ese usuario a esta vacante
		if(!solicitudRepo.existsByVacanteIdVacanteAndUsuarioEmail(idVacante, usuario.getEmail())) {
			solicitudRepo.save(nuevaSolicitud);
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Ya hay una solicitud para esta vacante con este usuario");
		}
		
		//Ahora creamos el dto de respuesta y le añadimos datos extra como la empresa a la que es la vacante
		SolicitudResponseDto respuestaDto = mapper.map(nuevaSolicitud, SolicitudResponseDto.class);
		respuestaDto.setNombreEmpresa(nuevaVacante.getEmpresa().getNombreEmpresa());
		
		return respuestaDto;
		
	}
	
}
