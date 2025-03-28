package com.fulljob.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;


public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {

	Optional<Empresa> findByUsuario(Usuario usuario);

}
