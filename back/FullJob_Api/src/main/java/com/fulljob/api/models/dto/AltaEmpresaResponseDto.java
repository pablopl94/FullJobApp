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
	private EmpresaResponseDto empresa;

    //Datos usuario
    private String email;
    private String nombre;
    private String apellidos;
}
