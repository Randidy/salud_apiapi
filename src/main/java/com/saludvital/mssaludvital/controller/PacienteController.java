package com.saludvital.mssaludvital.controller;

import com.saludvital.mssaludvital.dto.ApiResponse;
import com.saludvital.mssaludvital.entity.Paciente;
import com.saludvital.mssaludvital.service.PacienteService;
import com.saludvital.mssaludvital.service.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@Tag(name = "Pacientes", description = "Endpoints para gestión de pacientes")
@SecurityRequirement(name = "bearerAuth")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Ver perfil del paciente", description = "Obtiene la información del perfil del paciente autenticado")
    public ResponseEntity<?> getPerfil(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Paciente paciente = pacienteService.findByUsuarioId(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        return ResponseEntity.ok(new ApiResponse(true, "Perfil obtenido exitosamente", paciente));
    }

    @GetMapping("/expediente")
    @PreAuthorize("hasRole('PACIENTE')")
    @Operation(summary = "Ver expediente médico", description = "Obtiene el expediente médico completo del paciente")
    public ResponseEntity<?> getExpediente(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        Paciente paciente = pacienteService.findByUsuarioId(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        return ResponseEntity.ok(new ApiResponse(true, "Expediente obtenido exitosamente", paciente));
    }
    
    

}