package com.fulljob.api.models.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmpresaResponseDto {
	
	private Integer idEmpresa;
	
    private String cif;

    private String nombreEmpresa;

    private String direccionFiscal;

    private String pais;

    private LocalDate fechaRegistro;
    
    private Integer enabled;
   
}
