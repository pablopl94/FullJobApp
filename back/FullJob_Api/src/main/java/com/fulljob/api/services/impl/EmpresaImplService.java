package com.fulljob.api.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.models.entities.Vacante;
import com.fulljob.api.repository.IEmpresaRepository;
import com.fulljob.api.repository.ISolicitudRepository;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.services.IEmpresaService;

@Service
public class EmpresaImplService extends GenericCrudServiceImpl<Empresa, Integer> implements IEmpresaService {

	@Autowired
	private IEmpresaRepository empresaRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IVacanteRepository vacanteRepository;
	
	@Autowired
	private ISolicitudRepository solicitudRepository;
	
	@Autowired
	private ModelMapper mapper;

	// Aquí indicamos el repositorio que usamos en el CRUD genérico
	@Override
	protected JpaRepository<Empresa, Integer> getRepository() {
		return empresaRepository;
	}

	@Override
	public List<Empresa> buscarPorNombre(String nombre) {
		return empresaRepository.findByNombreEmpresaContainingIgnoreCase(nombre);
	}

	@Override
	public Empresa buscarPorEmail(String email) {
		return empresaRepository.findByUsuarioEmail(email).orElseThrow(() -> new RuntimeException("La empresa no existe"));
	}
	
	@Override
	public EmpresaResponseDto eliminarEmpresa(Usuario usuario) {

	    // Comprobamos que el usuario no es nulo
	    if (usuario == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe");
	    }

	    // Ponemos el usuario en no activo y lo actualizamos
	    usuario.setEnabled(0);
	    usuarioRepository.save(usuario);

	    // Buscamos la empresa
	    Empresa empresa = empresaRepository.findByUsuarioEmail(usuario.getEmail())
	            .orElseThrow(() -> new RuntimeException("La empresa no existe"));

	    // Sacamos las vacantes de la empresa
	    List<Vacante> listaVacantes = vacanteRepository.findByEmpresa_IdEmpresa(empresa.getIdEmpresa());

	    try {
	        // Recorremos cada vacante y eliminamos primero sus solicitudes asociadas
	        for (Vacante vacante : listaVacantes) {
	            solicitudRepository.deleteAll(solicitudRepository.findByVacante_idVacante(vacante.getIdVacante()));
	        }

	        // Luego eliminamos las vacantes
	        vacanteRepository.deleteAll(listaVacantes);

	        // Por último, eliminamos la empresa
	        empresaRepository.deleteById(empresa.getIdEmpresa());

	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	                "La empresa tiene vacantes o solicitudes asociadas, no se puede borrar: " + e.getMessage());
	    }

	    // Devolvemos la respuesta en dto de la empresa eliminada
	    return mapper.map(empresa, EmpresaResponseDto.class);
	}


}
