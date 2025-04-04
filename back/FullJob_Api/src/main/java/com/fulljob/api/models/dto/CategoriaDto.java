package com.fulljob.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder	
public class CategoriaDto {
	
	@NotBlank(message = "El nombre de la categoría no debe estar vacío o nulo.")
	@Size(max = 100,  message = "El nombre de la categía no debe contener más de 100 carácteres.")
	private String nombre;
	
	@Size(max = 2000,  message = "La descripción de la categía no debe contener más de 2000 carácteres.")
	private String descripcion;
}
