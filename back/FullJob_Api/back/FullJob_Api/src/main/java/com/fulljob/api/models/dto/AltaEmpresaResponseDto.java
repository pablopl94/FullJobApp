package com.fulljob.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AltaEmpresaResponseDto {

	//Datos de la empresa
    private String cif;

    private String nombreEmpresa;

    private String direccionFiscal;

    private String pais;

    //Datos del cliente
    private String email;
    
    private String nombre;
    
    private String rol;

    //Token
    private String token;
}
