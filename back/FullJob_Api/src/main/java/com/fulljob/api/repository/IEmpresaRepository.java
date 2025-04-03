package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {

	Empresa findByUsuario(Usuario usuario);

	
}
