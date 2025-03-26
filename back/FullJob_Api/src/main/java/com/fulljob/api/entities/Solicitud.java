package com.fulljob.api.entities;

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
	
	@Builder.Default
	@Enumerated(EnumType.ORDINAL)
	private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

	
	private String curriculum;
	
    @ManyToOne
    @JoinColumn(name = "id_vacante")
    private Vacante vacante;
    
    
    @ManyToOne
    @JoinColumn(name = "email")
    private Usuario usuario;
	
}
