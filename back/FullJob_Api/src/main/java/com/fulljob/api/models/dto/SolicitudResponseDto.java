package com.fulljob.api.models.dto;

import java.time.LocalDate;

import com.fulljob.api.models.entities.EstadoSolicitud;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudResponseDto {

	private int idSolicitud;
	private LocalDate fecha;
	private String archivo;
	private String curriculum;
	private String comentarios;
	private EstadoSolicitud estado;
	
	//Datos de la empresa
	private String nombreEmpresa;

    //Datos de la vacante
	private int idVacante;
	private String nombreVacante;
	private double salario;

	//Daatos del usuario solicitante
	private String nombreUsuario;
	private String apellidosUsuario;
	private String email;
	
	//Datos de la categoria
	private int idCategoria;
	private String nombreCategoria;
	
	
	
}
