package com.saludvital.mssaludvital.controller;

import com.saludvital.mssaludvital.dto.ApiResponse;
import com.saludvital.mssaludvital.dto.CitaRequest;
import com.saludvital.mssaludvital.dto.CitaResponse;
import com.saludvital.mssaludvital.entity.Cita;
import com.saludvital.mssaludvital.entity.Medico;
import com.saludvital.mssaludvital.entity.Paciente;
import com.saludvital.mssaludvital.service.CitaService;
import com.saludvital.mssaludvital.service.MedicoService;
import com.saludvital.mssaludvital.service.PacienteService;
import com.saludvital.mssaludvital.service.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/citas")
@Tag(name = "Citas", description = "Endpoints para gestión de citas médicas")
@SecurityRequirement(name = "bearerAuth")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @PostMapping("/nueva")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Solicitar nueva cita", description = "Crear una nueva cita médica")
    public ResponseEntity<?> crearCita(@Valid @RequestBody CitaRequest citaRequest, 
                                      Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Paciente paciente = pacienteService.findByUsuarioId(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        Medico medico = medicoService.findById(citaRequest.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        
        Cita cita = citaService.crearCita(paciente, medico, citaRequest.getFecha(), 
                                         citaRequest.getHora(), citaRequest.getMotivo());
        
        CitaResponse response = mapToResponse(cita);
        
        return ResponseEntity.ok(new ApiResponse(true, "Cita creada exitosamente", response));
    }

    @GetMapping("/mis-citas")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Ver mis citas", description = "Obtiene las citas del paciente autenticado")
    public ResponseEntity<?> getMisCitas(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Paciente paciente = pacienteService.findByUsuarioId(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        List<Cita> citas = citaService.findByPacienteId(paciente.getId());
        List<CitaResponse> responses = citas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new ApiResponse(true, "Citas obtenidas exitosamente", responses));
    }

    @GetMapping("/medico")
    @PreAuthorize("hasRole('MEDICO')")
    @Operation(summary = "Ver citas del médico", description = "Obtiene las citas del médico autenticado")
    public ResponseEntity<?> getCitasMedico(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Medico medico = medicoService.findByUsuarioId(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        
        List<Cita> citas = citaService.findByMedicoId(medico.getId());
        List<CitaResponse> responses = citas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new ApiResponse(true, "Citas obtenidas exitosamente", responses));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todas las citas", description = "Obtiene todas las citas del sistema (solo admin)")
    public ResponseEntity<?> getAllCitas() {
        List<Cita> citas = citaService.findAll();
        List<CitaResponse> responses = citas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new ApiResponse(true, "Citas obtenidas exitosamente", responses));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMIN')")
    @Operation(summary = "Modificar cita", description = "Actualiza una cita existente")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id, 
                                           @Valid @RequestBody CitaRequest citaRequest) {
        Cita cita = citaService.actualizarCita(id, citaRequest.getFecha(), 
                                              citaRequest.getHora(), citaRequest.getMotivo());
        
        CitaResponse response = mapToResponse(cita);
        
        return ResponseEntity.ok(new ApiResponse(true, "Cita actualizada exitosamente", response));
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMIN')")
    @Operation(summary = "Cancelar cita", description = "Cancela una cita existente")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        Cita cita = citaService.cancelarCita(id);
        CitaResponse response = mapToResponse(cita);
        
        return ResponseEntity.ok(new ApiResponse(true, "Cita cancelada exitosamente", response));
    }

    @PutMapping("/{id}/completar")
    @PreAuthorize("hasRole('MEDICO') or hasRole('ADMIN')")
    @Operation(summary = "Completar cita", description = "Marca una cita como completada")
    public ResponseEntity<?> completarCita(@PathVariable Long id) {
        Cita cita = citaService.completarCita(id);
        CitaResponse response = mapToResponse(cita);
        
        return ResponseEntity.ok(new ApiResponse(true, "Cita completada exitosamente", response));
    }

    private CitaResponse mapToResponse(Cita cita) {
        return new CitaResponse(
                cita.getId(),
                cita.getFecha(),
                cita.getHora(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombreCompleto(),
                cita.getMedico().getEspecialidad().getDisplayName(),
                cita.getEstado(),
                cita.getMotivo(),
                cita.getTarifaAplicada()
        );
    }
}