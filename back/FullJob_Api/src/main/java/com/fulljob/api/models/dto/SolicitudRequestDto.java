package com.fulljob.api.models.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
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
public class SolicitudRequestDto {

    @NotNull(message = "El archivo no puede ser null") 
    @NotEmpty(message = "El archivo no puede estar vacío")
    @Size(max = 255, message = "El nombre del archivo no puede ser mayor a 255 caracteres") 
    private String archivo;  

    @Size(max = 2000, message = "Los comentarios no pueden exceder los 2000 caracteres")
    private String comentarios; 

    @NotNull(message = "El curriculum no puede ser null") 
    private MultipartFile curriculum;  
}