package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Categoria;



public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

}
