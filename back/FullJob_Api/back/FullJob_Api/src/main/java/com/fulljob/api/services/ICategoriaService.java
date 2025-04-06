package com.fulljob.api.services;

import java.util.List;

import com.fulljob.api.models.entities.Categoria;



public interface ICategoriaService extends IGenericCrud<Categoria, Integer> {

	List<Categoria> findByName(String name);
}
