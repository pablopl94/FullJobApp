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
public class UsuarioResponseDto {
    private String email;
    private String nombre;
    private String apellidos;
    private String rol;
    private Integer enabled;
    private LocalDate fechaRegistro;

}
