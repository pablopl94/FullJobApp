package com.fulljob.api.models.dto;

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
public class EmpresaRegistroRequestDto {

    // Datos del usuario asociado
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    private String email;

    // No se recibe password porque se genera automáticamente

    // Datos de la empresa
    @NotBlank(message = "El CIF es obligatorio.")
    private String cif;

    @NotBlank(message = "El nombre de la empresa es obligatorio.")
    private String nombreEmpresa;

    @NotBlank(message = "La dirección fiscal es obligatoria.")
    private String direccionFiscal;

    @NotBlank(message = "El país es obligatorio.")
    private String pais;
}
