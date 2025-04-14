package com.fulljob.api.restcontroller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.SolicitudRequestDto;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.dto.VacanteRequestDto;
import com.fulljob.api.models.dto.VacanteResponseDto;
import com.fulljob.api.models.entities.TipoDeContrato;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.services.IVacanteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vacantes")
public class VacanteRestController {

	@Autowired 
	private IVacanteService vacanteService;
	
	@Autowired 
	private ModelMapper mapper;
	
	
	//METODO CON RUTA PARA VER TODAS LAS VACANTES ACTIVAS
	@GetMapping("")
	public ResponseEntity<List<VacanteResponseDto>> vacantesActivas() {
		
		List<VacanteResponseDto> respuestaDto =  vacanteService.findAllVacanteActivas();
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<VacanteResponseDto> buscarVacante (@PathVariable int id) {
		
		//Buscamos la vacante la guardamos sacando una excepcion si no lo encuentra
		Vacante vacante = vacanteService.findById(id)
		        .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));
		
		//Mapeamos al dto de respuesta de vancate
		VacanteResponseDto respuestaDto = mapper.map(vacante , VacanteResponseDto.class);
		
		//La añadimos los datos de las relaciones
		respuestaDto.setNombreEmpresa(vacante.getEmpresa().getNombreEmpresa());
		respuestaDto.setNombreCategoria(vacante.getCategoria().getNombre());
		
		return ResponseEntity.ok(respuestaDto);
		
	}
	
	//METODO CON RUTA PARA FILTRAR LAS VACANTES POR NOMBRE EMPRESA
	@GetMapping("/filtrar/empresa/{nombre}")
	public ResponseEntity<List<VacanteResponseDto>> filtroVacantesEmpresa(@PathVariable String nombre) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.filtrarVacantesEmpresa(nombre);
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	//METODO CON RUTA PARA FILTRAR LAS VACANTES POR CATEGORIA
	@GetMapping("/filtrar/categoria/{id}")
	public ResponseEntity<List<VacanteResponseDto>> filtroVacantesCategoria(@PathVariable int id) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.filtrarVacantesCategoria(id);
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	//METODO CON RUTA PARA FILTRAR LAS VACANTES POR CONTRATO = DETALLES
	@GetMapping("/filtrar/contrato/{contrato}")
	public ResponseEntity<List<VacanteResponseDto>> filtroVacantesTipoContrato(@PathVariable TipoDeContrato contrato) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.filtrarVacantesTipoContrato(contrato);
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	
	//METODO CON RUTA PARA EMPRESA VER SUS VACANTES CREADAS
	@GetMapping("/misvacantes/creadas")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<List<VacanteResponseDto>> empresaMisVacantesCreadas(@AuthenticationPrincipal Usuario usuario) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.obtenerVacantesDeEmpresaCreadas(usuario);
			
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	//METODO CON RUTA PARA EMPRESA VER SUS VACANTES ASGINADAS
	@GetMapping("/misvacantes/asignadas")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<List<VacanteResponseDto>> empresaMisVacantesAsginadas(@AuthenticationPrincipal Usuario usuario) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.obtenerVacantesDeEmpresaAsignadas(usuario);
			
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	
	//METODO CON RUTA PARA EMPRESA VER SUS VACANTES ASGINADAS
	@GetMapping("/misvacantes/canceladas")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<List<VacanteResponseDto>> empresaMisVacantesCanceladas(@AuthenticationPrincipal Usuario usuario) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.obtenerVacantesDeEmpresaCanceladas(usuario);
			
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	
	//METODO CON RUTA PARA PUBLICAR UN VACANTE
	@PostMapping("/publicar")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<VacanteResponseDto> publicarVacante(@RequestBody VacanteRequestDto vacanteDto) {
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//Le pasamos los datos de usuario y lo que llega por el body de vacanteDto
		VacanteResponseDto respuestaDto = vacanteService.publicarVacante(vacanteDto, usuario);
			
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	
	//METODO CON RUTA PARA MODIFICAR UN VACANTE
	@PutMapping("/editar/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<VacanteResponseDto> editarVacante(@PathVariable int id, @RequestBody VacanteRequestDto vacanteDto) {
		
		//Actualizamos la vacante con los datos que nos llega en el dto y guardamos el dto de respuesta
		VacanteResponseDto respuestaDto = vacanteService.editarVacante(id, vacanteDto);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	
	//METODO CON RUTA PARA CANCELAR UN VACANTE
	@DeleteMapping("/cancelar/{id}")
	@PreAuthorize("hasRole('EMPRESA')")
	public ResponseEntity<String> cancelarVacante(@PathVariable int id) {
		
		vacanteService.cancelarVacante(id);
		
		return ResponseEntity.ok("Vacante cancelada");
	}

	
	//METODO PARA INSCRIBIRSE UN CLIENTE EN UNA VACANTE
	@PostMapping("/inscribirse/{id}")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<SolicitudResponseDto> incribirseVacante(
			@PathVariable int id , 
			@AuthenticationPrincipal Usuario usuario , 
			@RequestBody SolicitudRequestDto solicitudDto) {
		
		
		SolicitudResponseDto respuestaDto = vacanteService.inscribirseVacante(id, usuario, solicitudDto);
		
		return ResponseEntity.ok(respuestaDto);
	}
	
	
	//Metodo para obtener los tipos de contrato para los forms del from
	@GetMapping("/tiposcontrato")
	public ResponseEntity<TipoDeContrato[]> getTiposContrato() {
	    return ResponseEntity.ok(TipoDeContrato.values());
	}

	
	
}
