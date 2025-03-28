package com.fulljob.api.restcontroller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fulljob.api.models.dto.EmpresaRegistroRequestDto;
import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.dto.UsuarioPasswordResponseDto;
import com.fulljob.api.services.IEmpresaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")

public class AdminController {

    @Autowired
    private IEmpresaService empresaService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método lo hemos creado para registrar una nueva empresa en el sistema.
     *
     * Lo que hace es:
     * 1. Recibe los datos necesarios para crear la empresa, incluyendo los del usuario (email, nombre...).
     * 2. Llama al servicio que se encarga de crear el usuario con rol "EMPRESA" y la entidad Empresa asociada.
     * 3. Genera una contraseña aleatoria que se asigna al usuario.
     * 4. Devuelve los datos de la empresa registrada y la contraseña generada (sin encriptar).
     *
     * ⚠️ Importante:
     * - Este ruta solo la puede usar un ADMON por lo que este es el unico que puede registrar a las empresas..
     * - La empresa **no se registra por sí misma** ni inicia sesión en este punto.
     * - Más adelante, cuando la empresa quiera entrar al sistema, deberá usar el endpoint de login
     *   con el email y la contraseña que le haya dado el admin.
     */
    @PostMapping("/alta/empresa")
    public ResponseEntity<Map<String, Object>> altaEmpresa(@RequestBody @Valid EmpresaRegistroRequestDto dto) {

        // Llamamos al servicio que registra al usuario y a la empresa
        UsuarioPasswordResponseDto cliente = empresaService.altaEmpresa(dto, null);

        // Mapeamos la entidad Empresa a un DTO para enviarla como respuesta
        EmpresaResponseDto newEmpresaResponse = modelMapper.map(empresaService.findByUsuario(cliente.getUsuario()), EmpresaResponseDto.class);

        // Metemos los datos del usuario manualmente por ahora, ya que ModelMapper no puede mapearlos directamente
        // (porque están dentro de la relación con Empresa). Si da tiempo, lo configuramos más adelante con un PropertyMap.
        newEmpresaResponse.setEmail(cliente.getUsuario().getEmail());
        newEmpresaResponse.setNombre(cliente.getUsuario().getNombre());
        newEmpresaResponse.setApellidos(cliente.getUsuario().getApellidos());
        
        
        // Devolvemos la empresa registrada y la contraseña generada
        return ResponseEntity.status(201).body(
                Map.of(
                        "empresa", newEmpresaResponse,
                        "passwordGenerada", cliente.getPasswordGenerada()
                )
        );
    }
}
