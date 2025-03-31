package com.fulljob.api.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "vacantes")
public class Vacante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vacante")
	private int idVacante;

	@Column(nullable = false, length = 200)
	private String nombre;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(nullable = false)
	private double salario;

	// Este atributo le asignamos una clase Enum y por defecto
	// cuando se creen las vacantes tendran el estado CREADA
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoVacante estado = EstadoVacante.CREADA;

	@Column(nullable = false)
	private boolean destacado;

	@Column(nullable = false, length = 250)
	private String imagen;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String detalles;

	// ANOTACIONES RELACIONES DE VACANTE

	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoriaS;

	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa empresa;
}