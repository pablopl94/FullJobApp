package com.fulljob.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulljob.api.models.entities.Empresa;


public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {

}
