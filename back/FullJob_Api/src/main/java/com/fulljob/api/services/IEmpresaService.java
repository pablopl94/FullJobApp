package com.fulljob.api.services;

import com.fulljob.api.models.entities.Empresa;

import java.util.List;


public interface IEmpresaService extends IGenericCrud<Empresa, Integer>{
    List<Empresa> buscarPorNombre(String nombre);
    Empresa buscarPorEmail(String email);
}
