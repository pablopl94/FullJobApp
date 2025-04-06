package com.fulljob.api.restcontroller;

import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.services.IEmpresaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class EmpresaRestController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IEmpresaService empresaService;


    // GET    /empresas ............................. [ROLE_ADMON]
    @PreAuthorize("hasRole('ADMON')")
    @GetMapping("/empresas")
    public ResponseEntity<List<EmpresaResponseDto>> mostrarEmpresas() {
        List<EmpresaResponseDto> respuestaDTO = empresaService.findAll()
                .stream()
                .map(empresa -> mapper.map(empresa, EmpresaResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuestaDTO);
    }

    //GET    /empresas/{id} ........................ [ROLE_ADMON]
    @PreAuthorize("hasRole('ADMON')")
    @GetMapping("/empresas/{id}")
    public ResponseEntity<EmpresaResponseDto> empresaPorId(@PathVariable Integer id){

        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
        EmpresaResponseDto empresaDto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(empresaDto);

    }

    //GET    /empresas/buscar/{nombre} ............. [ROLE_ADMON]
    @GetMapping("/empresas/{nombre}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<List<EmpresaResponseDto>> empresaPorNombre(@PathVariable String nombre) {
        List<Empresa> empresas = empresaService.buscarPorNombre(nombre);

        List<EmpresaResponseDto> empdto = empresas.stream()
                .map(empresa -> mapper.map(empresa, EmpresaResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empdto);
    }


    //GET    /empresas/miperfil .................... [ROLE_EMPRESA]
    @GetMapping("/empresas/miperfil")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<EmpresaResponseDto> perfilEmpresa( Authentication auth) {

        String email = auth.getName();
        Empresa empresa = empresaService.buscarPorEmail(email);
        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(dto);

    }

}