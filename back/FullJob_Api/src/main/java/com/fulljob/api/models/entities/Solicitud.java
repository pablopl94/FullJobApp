package com.fulljob.api.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue()
	@Column(name = "id_solicitud")
	private int idSolicitud;
	
	private LocalDate fecha;
	
	private String archivo;
	
	private String comentario;
	
	// Se guarda como 0 o 1 segun el orden del enum, por defecto sera 0 
	@Builder.Default 
	@Enumerated(EnumType.ORDINAL)
	private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

	private String curriculum;
	
	// ANOTACIONES RELACIONES DE SOLICITUD
	
    @ManyToOne
    @JoinColumn(name = "id_vacante")
    private Vacante vacante;
    
    
    @ManyToOne
    @JoinColumn(name = "email")
    private Usuario usuario;
	
}
