package com.fulljob.api.models.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AltaClienteResponseDto {

	//Datos del cliente
    private String email;
    
    private String nombre;
    
    private String apellidos;
    
    private String rol;

    private LocalDate fechaRegistro;
    
   //Token generado
    private String token;
}
