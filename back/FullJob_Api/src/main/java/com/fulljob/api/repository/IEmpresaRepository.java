package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {

	Empresa findByUsuario_email(String email);
	List<Empresa> findByNombreEmpresaContainingIgnoreCase(String nombreEmpresa);
	Optional<Empresa> findByUsuarioEmail(String email);
}
