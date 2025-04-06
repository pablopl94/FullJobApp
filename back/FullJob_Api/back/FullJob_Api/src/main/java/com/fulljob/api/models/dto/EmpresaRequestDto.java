package com.fulljob.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRequestDto {

    @NotBlank(message = "El CIF no puede estar vacío")
    private String cif;

    @NotBlank(message = "El nombre de la empresa no puede estar vacío")
    private String nombreEmpresa;

    @NotBlank(message = "La dirección fiscal no puede estar vacía")
    private String direccionFiscal;

    @NotBlank(message = "El país no puede estar vacío")
    private String pais;

}
