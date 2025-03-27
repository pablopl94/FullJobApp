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
public class Vacante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vacante")
	private int idVacante;
	
	private String nombre;
	private String descripcion;
	private LocalDate fecha;
	private double salario;
	
	//Este atributo le asignamos una clase Enum y por defecto
	//cuando se creen las vacantes tendran el estado CREADA
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EstadoVacante estado = EstadoVacante.CREADA;
    
	//Este atributo booleano le asignamos por decfecto el valor falso
    @Builder.Default
    private boolean destacado = false;
    
    private String imagen;
    
    private String detalles;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoriaS;
    
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
	
}
