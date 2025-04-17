package com.fulljob.api.restcontroller;

import com.fulljob.api.models.dto.EmpresaRequestDto;
import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.IEmpresaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empresas")
@EnableMethodSecurity(prePostEnabled = true)
public class EmpresaRestController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IEmpresaService empresaService;



    //ENDPOINT PARA MOSTRAR TODAS LAS EMPRESAS
    // GET    /empresas ............................. [ROLE_ADMON]
    @GetMapping()
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<List<EmpresaResponseDto>> mostrarEmpresas() {
        List<EmpresaResponseDto> respuestaDTO = empresaService.findAll()
            .stream()
            .map(empresa -> {
            	
                EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
                
                dto.setFechaRegistro(empresa.getUsuario().getFechaRegistro());
                dto.setEmail(empresa.getUsuario().getEmail());
                return dto;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(respuestaDTO);
    }

    //ENDPOINT PARA VER DETALLES DE LA EMPRESA
    //GET    /empresas/{id} ........................ [ROLE_ADMON]
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<EmpresaResponseDto> empresaPorId(@PathVariable Integer id) {
        Empresa empresa = empresaService.findById(id)
                .orElseThrow(() -> new RuntimeException("La empresa no existe"));

        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        dto.setFechaRegistro(empresa.getUsuario().getFechaRegistro());        

        return ResponseEntity.ok(dto);
    }



    //ENDPOINT PARA  BUSCAR EMPRESA POR SU NOMBRE
    //GET    /empresas/buscar/{nombre} ............. [ROLE_ADMON]
    @GetMapping("/buscar/{nombre}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<List<EmpresaResponseDto>> empresaPorNombre(@PathVariable String nombre) {
        List<Empresa> empresas = empresaService.buscarPorNombre(nombre);

        List<EmpresaResponseDto> empdto = empresas.stream()
                .map(empresa -> mapper.map(empresa, EmpresaResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(empdto);
    }
  

    //ENDOPINT PARA MODIFICAR EMPRESA ADMIN
    // PUT    /empresas/{id} ........................ [ROLE_ADMON]
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<EmpresaResponseDto> actualizarEmpresa(@PathVariable Integer id, @RequestBody EmpresaRequestDto dto) {
    	
        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
        empresa.setCif(dto.getCif());
        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setDireccionFiscal(dto.getDireccionFiscal());
        empresa.setPais(dto.getPais());
        
        empresaService.updateOne(empresa);
        
        return ResponseEntity.ok(mapper.map(empresa, EmpresaResponseDto.class));
    }

    
    //ENDOPINT PARA ELIMINAR LA EMPRESA  (elimina los datos de la empresa y desactiva al usuario vinculado)
    // DELETE /empresas/{id} ........................ [ROLE_ADMON]
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<EmpresaResponseDto> eliminarEmpresa(@PathVariable Integer id){
    	
    Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
    
    Usuario usuario = empresa.getUsuario();

    EmpresaResponseDto respuestaDto =  empresaService.eliminarEmpresa(usuario);

    return ResponseEntity.ok(respuestaDto);

    }

    
    //ENDPOINT PARA VER LOS DATOS DE LA EMPRESA AUTENTICADA
    //GET    /empresas/perfil.................... [ROLE_EMPRESA]
    @GetMapping("/perfil")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<EmpresaResponseDto> perfilEmpresa(@AuthenticationPrincipal Usuario usuario) {
    	
        Empresa empresa = empresaService.buscarPorEmail(usuario.getEmail());
        
        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        
        return ResponseEntity.ok(dto);
    }


    //MODIFICAR LOS DATOS DE LA EMPRESA POR LA EMPRESA
    //PUT    /empresas/update ...................... [ROLE_EMPRESA ]
    @PutMapping("/update")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<EmpresaResponseDto> updateEmpresa(@AuthenticationPrincipal Usuario usuario, @RequestBody EmpresaRequestDto dto){     

        Empresa empresa = empresaService.buscarPorEmail(usuario.getEmail());

        empresa.setCif(dto.getCif());
        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setDireccionFiscal(dto.getDireccionFiscal());
        empresa.setPais(dto.getPais());

        empresaService.updateOne(empresa);
        EmpresaResponseDto responseDto = mapper.map(empresa, EmpresaResponseDto.class);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<EmpresaResponseDto> crearEmpresa(@RequestBody EmpresaRequestDto dto) {
        Empresa nuevaEmpresa = new Empresa();

        nuevaEmpresa.setCif(dto.getCif());
        nuevaEmpresa.setNombreEmpresa(dto.getNombreEmpresa());
        nuevaEmpresa.setDireccionFiscal(dto.getDireccionFiscal());
        nuevaEmpresa.setPais(dto.getPais());

        empresaService.save(nuevaEmpresa);

        EmpresaResponseDto respuestaDto = mapper.map(nuevaEmpresa, EmpresaResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDto);
    }

}