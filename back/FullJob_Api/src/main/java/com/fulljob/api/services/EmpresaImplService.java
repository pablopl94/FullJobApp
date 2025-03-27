package com.fulljob.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import com.fulljob.api.exceptions.ResourceNotFoundException;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.repository.IEmpresaRepository;

@Service
public class EmpresaImplService implements IEmpresaService {

	@Autowired
	private IEmpresaRepository empresaRepository;

	@Override
	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

	@Override
	public Empresa findById(Integer id) {
		return empresaRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(("Empresa no encontrada con ID: " + id)));
	}

	@Override
	public Empresa insertOne(Empresa entity) {
		 if (empresaRepository.existsById(entity.getIdEmpresa())) {
		        throw new ResourceNotFoundException("Empresa no encontrada con ID: " + entity.getIdEmpresa());
		    }
		    return empresaRepository.save(entity);
	}

	@Override
	public Empresa updatetOne(Empresa entity) {
	    if (!empresaRepository.existsById(entity.getIdEmpresa())) {
	        throw new ResourceNotFoundException("Empresa no encontrada con ID: " + entity.getIdEmpresa());
	    }
	    return empresaRepository.save(entity);
	}
	
	@Override
	public void deleteOne(Integer id) {
	    if (!empresaRepository.existsById(id)) {
	        throw new ResourceNotFoundException("Empresa no encontrado con ID: " + id);
	    }
	    try {
	        empresaRepository.deleteById(id);
	    } catch (DataIntegrityViolationException e) {
	        throw new IllegalStateException("No se puede eliminar el empleado porque está asociado a otros registros.");
	    } catch (Exception e) {
	        throw new RuntimeException("Error inesperado al eliminar el empleado.");
	    }
	}
	

}
