package com.fulljob.api.restcontroller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fulljob.api.auth.JwtUtils;
import com.fulljob.api.models.dto.AltaClienteRequestDto;
import com.fulljob.api.models.dto.AltaClienteResponseDto;
import com.fulljob.api.models.dto.SolicitudRequestDto;
import com.fulljob.api.models.dto.SolicitudResponseDto;
import com.fulljob.api.models.entities.*;
import com.fulljob.api.repository.IUsuarioRepository;
import com.fulljob.api.repository.IVacanteRepository;
import com.fulljob.api.repository.ISolicitudRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
public class ClienteRestController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IVacanteRepository vacanteRepository;

    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/registro")
    public ResponseEntity<AltaClienteResponseDto> registrarCliente(@RequestBody AltaClienteRequestDto dto) {
        if (usuarioRepository.existsById(dto.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol("CLIENTE")
                .enabled(1)
                .fechaRegistro(LocalDate.now())
                .build();

        usuarioRepository.save(usuario);

        String token = jwtUtils.generateToken(usuario);

        AltaClienteResponseDto response = AltaClienteResponseDto.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .rol(usuario.getRol())
                .fechaRegistro(usuario.getFechaRegistro())
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitudes")
    public ResponseEntity<List<SolicitudResponseDto>> verMisSolicitudes() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Solicitud> solicitudes = solicitudRepository.findByUsuario_email(usuario.getEmail());

        List<SolicitudResponseDto> respuesta = solicitudes.stream().map(s -> {
            SolicitudResponseDto dto = mapper.map(s, SolicitudResponseDto.class);
            dto.setNombreVacante(s.getVacante().getNombre());
            dto.setNombreEmpresa(s.getVacante().getEmpresa().getNombreEmpresa());
            dto.setEstado(s.getEstado());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/solicitudes/postular/{idVacante}")
    public ResponseEntity<String> postular(@PathVariable int idVacante, @RequestBody SolicitudRequestDto solicitudDto) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Vacante> vacanteOpt = vacanteRepository.findById(idVacante);
        if (vacanteOpt.isEmpty() || vacanteOpt.get().getEstatus() != EstadoVacante.CREADA) {
            return ResponseEntity.badRequest().body("La vacante no está disponible para postulación.");
        }

        Vacante vacante = vacanteOpt.get();

        List<Solicitud> solicitudesPrevias = solicitudRepository.findByUsuario_email(usuario.getEmail());

        boolean yaExiste = solicitudesPrevias.stream()
            .anyMatch(s -> s.getVacante().getIdVacante() == vacante.getIdVacante());

        if (yaExiste) {
            return ResponseEntity.badRequest().body("Ya has enviado una solicitud a esta vacante.");
        }

        Solicitud solicitud = Solicitud.builder()
                .fecha(LocalDate.now())
                .archivo(solicitudDto.getArchivo())
                .comentarios(solicitudDto.getComentarios())
                .curriculum(solicitudDto.getCurriculum())
                .usuario(usuario)
                .vacante(vacante)
                .estado(EstadoSolicitud.PRESENTADA)
                .build();

        solicitudRepository.save(solicitud);

        return ResponseEntity.ok("Solicitud enviada correctamente.");
    }

    @DeleteMapping("/solicitudes/cancelar/{id}")
    public ResponseEntity<String> cancelarSolicitud(@PathVariable int id) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Solicitud> solicitudOpt = solicitudRepository.findById(id);
        if (solicitudOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Solicitud solicitud = solicitudOpt.get();

        if (!solicitud.getUsuario().getEmail().equals(usuario.getEmail())) {
            return ResponseEntity.status(403).body("No puedes cancelar una solicitud que no es tuya.");
        }

        if (solicitud.getEstado() != EstadoSolicitud.PRESENTADA) {
            return ResponseEntity.badRequest().body("La solicitud ya fue adjudicada y no puede cancelarse.");
        }

        solicitudRepository.delete(solicitud);
        return ResponseEntity.ok("Solicitud cancelada correctamente.");
    }
}
