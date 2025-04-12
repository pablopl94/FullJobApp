package com.fulljob.api.models.entities;

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
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private Integer idSolicitud;
	
	@Column(nullable = false)
	private LocalDate fecha;
	
	@Column(nullable = false, length = 250)
	private String archivo;
	
	@Column(length = 2000)
	private String comentarios;
	
	// Se guarda como 0 o 1 segun el orden del enum, por defecto sera 0 
	@Builder.Default 
	@Enumerated(EnumType.ORDINAL)
	private EstadoSolicitud estado = EstadoSolicitud.PRESENTADA;
	
	@Column(length = 45)
	private String curriculum;
	
	// ANOTACIONES RELACIONES DE SOLICITUD
	
    @ManyToOne
    @JoinColumn(name = "id_vacante", nullable = false)
    private Vacante vacante;
    
    
    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private Usuario usuario;
	
}
