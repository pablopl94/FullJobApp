package com.fulljob.api.models.dto;

import com.fulljob.api.models.entities.EstadoVacante;
import com.fulljob.api.models.entities.TipoDeContrato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacanteRequestDto {
	
	//NOTAS:
	//-Los datos de la empresa los recogemos del usuario autenticado
	//-La fecha de vacante se obtendra cuando realice el alta
	//-El estado de la vacante la hemos puesto por defecto privada asi que no se pone como requisito,
	// tampoco vamos a usar el estado de la vacante para editar vacante ya que solo cambia de estado
	// cuando se cancela o se agina.
	
	//Categoria de la vacante 
	@NotNull(message = "El ID de la categoría es obligatorio")
	private int idCategoria;
	
	@NotBlank(message = "El nombre de la vacante es obligatorio")
	@Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
	private String nombre;

	@NotBlank(message = "La descripción es obligatoria")
	@Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
	private String descripcion;

	@Positive(message = "El salario no puede ser negativo")
	private double salario;

	// Como tenemos por defecto destacado en false, no se requiere validación extra
	private int destacado;

	@NotNull(message = "Debes especificar el tipo de contrato")
	private TipoDeContrato detalles;
	
	//No ponemos validacion porque puede venir por defecto a false
	private EstadoVacante estatus;

	// De momento sin validación
	private String imagen;
	
	//Hay que meter la fecha de publicacion de la vacante
}
