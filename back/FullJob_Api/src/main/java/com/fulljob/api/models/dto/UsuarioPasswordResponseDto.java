package com.fulljob.api.models.dto;

import com.fulljob.api.models.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPasswordResponseDto {
	
    private Usuario usuario;
    private String passwordGenerada;
}
