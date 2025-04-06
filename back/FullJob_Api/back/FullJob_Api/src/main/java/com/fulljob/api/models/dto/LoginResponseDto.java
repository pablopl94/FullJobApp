package com.fulljob.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
	
	//Datos del cliente
    private String email;
    
    private String nombre;
    
    private String rol;

    //Token
    private String token;
}
