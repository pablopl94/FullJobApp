package com.fulljob.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaResponseDto {

    private Integer idEmpresa;

    private String cif;

    private String nombreEmpresa;

    private String direccionFiscal;

    private String pais;

    //EMPRESA
    private String email;
    private String nombre;
    private String apellidos;
}
