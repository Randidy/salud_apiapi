package com.saludvital.mssaludvital.controller;

import com.saludvital.mssaludvital.dto.AdminRequest;
import com.saludvital.mssaludvital.dto.AdminUpdateRequest;
import com.saludvital.mssaludvital.dto.ApiResponse;
import com.saludvital.mssaludvital.dto.MedicoRequest;
import com.saludvital.mssaludvital.dto.MedicoUpdateRequest;
import com.saludvital.mssaludvital.dto.PacienteRequest;
import com.saludvital.mssaludvital.dto.PacienteResponse;
import com.saludvital.mssaludvital.dto.PacienteUpdateRequest;
import com.saludvital.mssaludvital.entity.Medico;
import com.saludvital.mssaludvital.entity.Paciente;
import com.saludvital.mssaludvital.entity.Role;
import com.saludvital.mssaludvital.entity.User;
import com.saludvital.mssaludvital.enums.RoleName;
import com.saludvital.mssaludvital.repository.RoleRepository;
import com.saludvital.mssaludvital.service.MedicoService;
import com.saludvital.mssaludvital.service.PacienteService;
import com.saludvital.mssaludvital.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administración", description = "Endpoints para administración del sistema (solo administradores)")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Gestión de Administradores
    @PostMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar nuevo administrador", description = "Crea un nuevo administrador en el sistema")
    public ResponseEntity<?> crearAdministrador(@Valid @RequestBody AdminRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El email ya está en uso"));
        }

        // Crear usuario con rol ADMIN
        User user = new User(request.getNombre(), request.getEmail(), request.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
        user.setRoles(Collections.singleton(adminRole));

        User savedUser = userService.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Administrador creado exitosamente", savedUser));
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar administradores", description = "Obtiene la lista de todos los administradores")
    public ResponseEntity<?> listarAdministradores() {
        List<User> admins = userService.findAllByRoles_Name(RoleName.ROLE_ADMIN);
        return ResponseEntity.ok(new ApiResponse(true, "Administradores obtenidos exitosamente", admins));
    }

    @PutMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar administrador", description = "Actualiza la información de un administrador por su ID")
    public ResponseEntity<?> actualizarAdministrador(@PathVariable Long id,
                                                     @Valid @RequestBody AdminUpdateRequest request) {
        User admin = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + id));

        // Validar que sea ADMIN
        boolean esAdmin = admin.getRoles().stream()
                .anyMatch(r -> r.getName().equals(RoleName.ROLE_ADMIN));

        if (!esAdmin) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El usuario no es un administrador"));
        }

        // Actualizar solo los campos que llegan
        if (request.getNombre() != null) admin.setName(request.getNombre());
        if (request.getEmail() != null) admin.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userService.save(admin);

        return ResponseEntity.ok(new ApiResponse(true, "Administrador actualizado exitosamente", updatedUser));
    }

    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador por su ID")
    public ResponseEntity<?> eliminarAdministrador(@PathVariable Long id) {
        User admin = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + id));

        // Verificar que realmente sea ADMIN antes de borrarlo
        boolean esAdmin = admin.getRoles().stream()
                .anyMatch(r -> r.getName().equals(RoleName.ROLE_ADMIN));

        if (!esAdmin) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El usuario no es un administrador"));
        }

        userService.deleteById(id);

        return ResponseEntity.ok(new ApiResponse(true, "Administrador eliminado exitosamente"));
    }

    
    // ✅ LISTAR PACIENTES
    @GetMapping("/pacientes")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los pacientes", description = "Obtiene la lista completa de pacientes sin credenciales")
    public ResponseEntity<?> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.findAll();

        List<PacienteResponse> response = pacientes.stream().map(p -> {
            PacienteResponse dto = new PacienteResponse();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setNumeroIdentificacion(p.getNumeroIdentificacion());
            dto.setFechaNacimiento(p.getFechaNacimiento());
            dto.setTelefono(p.getTelefono());
            dto.setDireccion(p.getDireccion());
            dto.setTieneAlergias(p.getAlergias() != null && !p.getAlergias().isEmpty());
            dto.setAlergias(
                    p.getAlergias() != null
                            ? p.getAlergias().stream().map(a -> a.getNombre()).toList()
                            : List.of()
            );
            dto.setCitas(p.getCitas() != null ? p.getCitas().size() : 0);
            dto.setRecetas(p.getRecetas() != null ? p.getRecetas().size() : 0);
            return dto;
        }).toList();

        return ResponseEntity.ok(new ApiResponse(true, "Pacientes obtenidos exitosamente", response));
    }

 // ✅ CREAR PACIENTE
    @PostMapping("/pacientes")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMIN')")
    @Operation(summary = "Registrar nuevo paciente", description = "Crea un nuevo paciente en el sistema")
    public ResponseEntity<?> crearPaciente(@Valid @RequestBody PacienteRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El email ya está en uso"));
        }

        if (pacienteService.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El número de identificación ya está en uso"));
        }

        // Crear usuario
        User user = new User(request.getNombre(), request.getEmail(), request.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_PACIENTE)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userService.save(user);

        // Crear paciente con todos los campos
        Paciente paciente = pacienteService.createPaciente(
                request.getNombre(),
                request.getNumeroIdentificacion(),
                request.getFechaNacimiento(),
                request.getTelefono(),
                request.getDireccion(),
                savedUser
        );

        return ResponseEntity.ok(new ApiResponse(true, "Paciente creado exitosamente", paciente));
    }

 // ✅ ACTUALIZAR PACIENTE
    @PutMapping("/pacientes/{id}")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('ADMIN')")
    @Operation(summary = "Actualizar paciente", description = "Actualiza la información de un paciente")
    public ResponseEntity<?> actualizarPaciente(@PathVariable Long id,
                                               @Valid @RequestBody PacienteUpdateRequest request) {
        Paciente actualizado = pacienteService.updatePaciente(
                id,
                request.getNombre(),
                request.getNumeroIdentificacion(),
                request.getFechaNacimiento(),
                request.getTelefono(),
                request.getDireccion()
        );

        return ResponseEntity.ok(new ApiResponse(true, "Paciente actualizado exitosamente", actualizado));
    }

    // Gestión de Médicos
    @PostMapping("/medicos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar nuevo médico", description = "Crea un nuevo médico en el sistema")
    public ResponseEntity<?> crearMedico(@Valid @RequestBody MedicoRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El email ya está en uso"));
        }

        if (medicoService.existsByNumeroLicencia(request.getNumeroLicencia())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El número de licencia ya está en uso"));
        }

        // Crear usuario
        User user = new User(request.getNombre() + " " + request.getApellido(),
                           request.getEmail(), request.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_MEDICO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userService.save(user);

        // Crear médico
        Medico medico = medicoService.createMedico(
                request.getNombre(),
                request.getApellido(),
                request.getNumeroLicencia(),
                request.getTelefono(),
                request.getEmail(),
                request.getEspecialidad(),
                request.getTarifaConsulta(),
                savedUser
        );

        return ResponseEntity.ok(new ApiResponse(true, "Médico creado exitosamente", medico));
    }
    
    

    @PutMapping("/medicos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar médico", description = "Actualiza la información de un médico")
    public ResponseEntity<?> actualizarMedico(@PathVariable Long id,
                                             @Valid @RequestBody MedicoUpdateRequest request) {
        Medico medico = medicoService.updateMedico(
                id,
                request.getNombre(),
                request.getApellido(),
                request.getNumeroLicencia(),
                request.getTelefono(),
                request.getEmail(),
                request.getEspecialidad(),
                request.getTarifaConsulta(),
                request.getEstado(),
                request.getDisponible()
        );

        return ResponseEntity.ok(new ApiResponse(true, "Médico actualizado exitosamente", medico));
    }

    @DeleteMapping("/pacientes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        try {
            pacienteService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Paciente eliminado exitosamente"));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
        }
    }
    
    @GetMapping("/paciente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPacienteCompletoById(@PathVariable Long id) {
        Paciente paciente = pacienteService.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", paciente.getId());
        response.put("nombre", paciente.getNombre());
        response.put("numeroIdentificacion", paciente.getNumeroIdentificacion());
        response.put("fechaNacimiento", paciente.getFechaNacimiento());
        response.put("telefono", paciente.getTelefono());
        response.put("direccion", paciente.getDireccion());

        if (paciente.getUsuario() != null) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", paciente.getUsuario().getId());
            userData.put("email", paciente.getUsuario().getEmail());
            userData.put("roles", paciente.getUsuario().getRoles().stream()
                    .map(r -> r.getName()).toList());
            response.put("usuario", userData);
        }

        return ResponseEntity.ok(new ApiResponse(true, "Paciente obtenido exitosamente", response));
    }
    
    @PutMapping("/paciente/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarPacienteCompleto(@PathVariable Long id, 
                                                        @RequestBody Map<String, Object> request) {
        Paciente paciente = pacienteService.updatePaciente(
            id,
            (String) request.get("nombre"),
            (String) request.get("numeroIdentificacion"),
            LocalDate.parse((String) request.get("fechaNacimiento")),
            (String) request.get("telefono"),
            (String) request.get("direccion")
        );

        if (request.containsKey("email") && paciente.getUsuario() != null) {
            String email = (String) request.get("email");
            paciente.getUsuario().setEmail(email);
            userService.save(paciente.getUsuario());
        }

        return ResponseEntity.ok(new ApiResponse(true, "Paciente actualizado exitosamente", paciente));
    }
    
    
    
}
