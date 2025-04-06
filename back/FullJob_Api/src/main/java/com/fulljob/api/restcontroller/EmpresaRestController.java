package com.fulljob.api.restcontroller;

import com.fulljob.api.models.dto.AltaEmpresaRequestDto;
import com.fulljob.api.models.dto.AltaEmpresaResponseDto;
import com.fulljob.api.models.dto.EmpresaRequestDto;
import com.fulljob.api.models.dto.EmpresaResponseDto;
import com.fulljob.api.models.entities.Empresa;
import com.fulljob.api.models.entities.Usuario;
import com.fulljob.api.services.IEmpresaService;
import com.fulljob.api.services.IUsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private IUsuarioService usuarioService;


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
    public ResponseEntity<EmpresaResponseDto> perfilEmpresa(Authentication auth) {
        String email = auth.getName();
        Empresa empresa = empresaService.buscarPorEmail(email);
        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(dto);

    }

    // POST   /empresas/register .................... [ROLE_ADMON]

    @PostMapping("/empresas/register")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<AltaEmpresaResponseDto> registrarEmpresa(@RequestBody AltaEmpresaRequestDto altaDto) {

        // USUARIO
        Usuario usuario = new Usuario();
        usuario.setEmail(altaDto.getEmail());
        usuario.setNombre(altaDto.getNombre());
        usuario.setApellidos(altaDto.getApellidos());
        usuario.setPassword(altaDto.getPassword());
        usuario.setEnabled(1);
        usuario.setRol("EMPRESA");

        // EMPRESA
        Empresa empresa = new Empresa();
        empresa.setCif(altaDto.getCif());
        empresa.setNombreEmpresa(altaDto.getNombreEmpresa());
        empresa.setDireccionFiscal(altaDto.getDireccionFiscal());
        empresa.setPais(altaDto.getPais());
        empresa.setUsuario(usuario); // Relación con el usuario

        if (usuarioService.findById(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        usuarioService.updateOne(usuario);     // o usuarioService.insertOne()
        Empresa empresaGuardada = empresaService.insertOne(empresa);

        AltaEmpresaResponseDto response = mapper.map(empresaGuardada, AltaEmpresaResponseDto.class);
        return ResponseEntity.ok(response);
    }

    // PUT    /empresas/{id} ........................ [ROLE_ADMON]
    @PutMapping("/empresas/{id}")
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


    // PUT    /desactivar/{id} ...................... [ROLE_ADMON]
    @PutMapping("/empresas/desactivar/{id}")
    public ResponseEntity<EmpresaResponseDto> desactivarEmpresa(@PathVariable Integer id){
        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
        empresa.getUsuario().setEnabled(0);
        empresaService.updateOne(empresa);

        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(dto);
    }



    // PUT    /activar/{id} ......................... [ROLE_ADMON]
    @PutMapping("/empresas/activar/{id}")
    public ResponseEntity<EmpresaResponseDto> activarEmpresa(@PathVariable Integer id){
        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
        empresa.getUsuario().setEnabled(1);
        empresaService.updateOne(empresa);

        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(dto);

    }

    // PUT    /empresas/update ...................... [ROLE_EMPRESA]
    @PutMapping("/empresas/update/")
    public ResponseEntity<EmpresaResponseDto> updateEmpresa(Authentication auth, @RequestBody EmpresaRequestDto dto){
        String email = auth.getName();
        Empresa empresa = empresaService.buscarPorEmail(email);

        empresa.setCif(dto.getCif());
        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setDireccionFiscal(dto.getDireccionFiscal());
        empresa.setPais(dto.getPais());

        empresaService.updateOne(empresa);
        EmpresaResponseDto responseDto = mapper.map(empresa, EmpresaResponseDto.class);

        return ResponseEntity.ok(responseDto);
    }


    // DELETE /empresas/{id} ........................ [ROLE_ADMON]
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<EmpresaResponseDto> eliminarEmpresa(@PathVariable Integer id){
    Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("La empresa no existe"));
    Usuario usuario = empresa.getUsuario();
    if (usuario != null) {
        usuarioService.deleteOne(usuario.getEmail());
    }
    empresaService.deleteOne(id);


    EmpresaResponseDto responseDto = mapper.map(empresa, EmpresaResponseDto.class);
    return ResponseEntity.ok(responseDto);

    }
    // PUT    /empresa/eliminar/{id} ............... [ROLE_ADMON]
    @PutMapping("/empresa/eliminar/{id}")
    @PreAuthorize("hasRole('ADMON')")
    public ResponseEntity<EmpresaResponseDto> eliminarEmpresaSinBorrar(@PathVariable Integer id) {
        Empresa empresa = empresaService.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Desactivar usuario
        empresa.getUsuario().setEnabled(0);

        // 2. Limpiar campos
        empresa.setCif("ANON");
        empresa.setNombreEmpresa("");
        empresa.setDireccionFiscal("");
        empresa.setPais("");

        // Guardar cambios
        empresaService.updateOne(empresa);

        // Devolver respuesta
        EmpresaResponseDto dto = mapper.map(empresa, EmpresaResponseDto.class);
        return ResponseEntity.ok(dto);
    }
}