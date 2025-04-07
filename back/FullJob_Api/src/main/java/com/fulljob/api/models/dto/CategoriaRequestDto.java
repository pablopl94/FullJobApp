package com.fulljob.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaRequestDto {

	@NotBlank(message = "El nombre de la categoría no puede estar vacío")
	@NotNull(message = "El nombre no puede ser nulo")
	@Size(max = 100, message = "El nombre no debe tener más de 100 caracteres")
	private String nombre;

	@Size(max = 2000, message = "La descripción no puede tener más de 2000 caracteres")
	private String descripcion;
}
