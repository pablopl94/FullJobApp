package com.fulljob.api.models.dto;

import java.time.LocalDate;

import com.fulljob.api.models.entities.EstadoVacante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacanteResponseDto {

	//Categiria de la vancate
	private String nombreCategoria;
	
	//Nombre de la empresa que publica la vancante
	private String nombreEmpresa;
	
	//Datos de la vacante
	private String nombre;

	private String descripcion;

	private LocalDate fecha;

	private double salario;

	private EstadoVacante estatus;

	private boolean destacado;

	private String imagen;

	private String detalles;
}
