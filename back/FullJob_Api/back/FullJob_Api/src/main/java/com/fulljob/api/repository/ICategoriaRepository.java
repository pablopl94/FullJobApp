package com.fulljob.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {

	List<Categoria> findByNombreContainingIgnoreCase(String nombre);
}
