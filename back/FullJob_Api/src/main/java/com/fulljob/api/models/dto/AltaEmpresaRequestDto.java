package com.fulljob.api.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AltaEmpresaRequestDto {

    // Datos del usuario asociado
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;
	
	private String password;

    // DATO DE LA EMPRESA
    @NotBlank(message = "El CIF no puede estar vacío")
    private String cif;

    @NotBlank(message = "El nombre de la empresa no puede estar vacío")
    private String nombreEmpresa;

    @NotBlank(message = "La dirección fiscal no puede estar vacía")
    private String direccionFiscal;

    @NotBlank(message = "El país no puede estar vacío")
    private String pais;
}
