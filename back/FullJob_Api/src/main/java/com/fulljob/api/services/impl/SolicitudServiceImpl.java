package com.fulljob.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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
        return solicitudRepo.findByVacante(vacante);
    }


}
