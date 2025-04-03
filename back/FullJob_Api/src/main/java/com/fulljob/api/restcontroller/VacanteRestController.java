package com.fulljob.api.restcontroller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.VacanteResponseDto;
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
	
	
//🔹 GET    /vacantes ............................. [permitAll]
//🔹 GET    /vacantes/{id} ........................ [permitAll]
//🔹 GET    /vacantes/filtrar/nombr................ [permitAll]
//🔹 GET    /vacantes/filtrar/categoria ........... [permitAll]
//🔹 GET    /vacantes/filtrar/tipocontrato .  ..... [permitAll]
//🔹 GET    /vacantes/misvacantes ................  [ROLE_EMPRESA]   ← solo vacantes creadas por la empresa logueada
//🔹 POST   /vacantes/publicar .................... [ROLE_EMPRESA]
//🔹 PUT    /vacantes/editar/{id} ................. [ROLE_EMPRESA]
//🔹 DELETE /vacantes/cancelar/{id} ............... [ROLE_EMPRESA]   ← no elimina la vacante, cambia su estado a CANCELADA
	
	
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
	
	//METODO CON RUTA PARA FILTRAR LAS VACANTES POR NOMBRE EMPRESA
	@GetMapping("/filtrar/categoria/{id}")
	public ResponseEntity<List<VacanteResponseDto>> filtroVacantesCategoria(@PathVariable int id) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.filtrarVacantesCategoria(id);
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	//METODO CON RUTA PARA FILTRAR LAS VACANTES POR NOMBRE EMPRESA
	@GetMapping("/filtrar/categoria/{contrato}")
	public ResponseEntity<List<VacanteResponseDto>> filtroVacantesTipoContrato(@PathVariable String contrato) {
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.filtrarVacantesTipoContrato(contrato);
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	
	//METODO CON RUTA PARA EMPRESA VER SUS VACANTES
	@GetMapping("/misvacantes")
	public ResponseEntity<List<VacanteResponseDto>> empresaMisVacantes() {
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<VacanteResponseDto> listaRespuestaDto = vacanteService.obtenerVacantesDeEmpresa(usuario);
		
		
		return ResponseEntity.ok(listaRespuestaDto );
	}
	
	
	//METODO CON RUTA PARA PUBLICAR UN VACANTE
	@GetMapping("/publicar")
	public ResponseEntity<List<VacanteResponseDto>> publicarVacante() {
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		//ME FALTA IMPLEMENTAR <---- PABLO
		
		
		return ResponseEntity.ok(null);
	}
	
	
	
	//METODO CON RUTA PARA PUBLICAR UN VACANTE
	@PutMapping("/editar{id}")
	public ResponseEntity<List<VacanteResponseDto>> editarVacante(@PathVariable int id) {
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		//ME FALTA IMPLEMENTAR <---- PABLO
		
		
		return ResponseEntity.ok(null);
	}
	
	
	
	
	//METODO CON RUTA PARA PUBLICAR UN VACANTE
	@GetMapping("/cancelar/{id}")
	public ResponseEntity<List<VacanteResponseDto>> cancelarVacante(@PathVariable int id) {
		
		//Obtenemos el usuario autenticado con security
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		//ME FALTA IMPLEMENTAR <---- PABLO
		
		
		return ResponseEntity.ok(null);
	}
	
	
	
	
	
}
