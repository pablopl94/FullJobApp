package com.fulljob.api.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Integer idEmpresa;

	@Column(unique = true, nullable = false, length = 10)
	private String cif;

	@Column(name = "nombre_empresa", nullable = false, length = 100)
	private String nombreEmpresa;

	@Column(name = "direccion_fiscal", length = 100)
	private String direccionFiscal;

	@Column(length = 45)
	private String pais;

	// ANOTACIONES RELACIONES DE EMPRESA

	@OneToOne
	@JoinColumn(name = "email", referencedColumnName = "email")
	private Usuario usuario;

}