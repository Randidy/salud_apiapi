package com.saludvital.mssaludvital.service;

import com.saludvital.mssaludvital.entity.Paciente;
import com.saludvital.mssaludvital.entity.User;
import com.saludvital.mssaludvital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    
	 @Autowired
	    private PacienteRepository pacienteRepository;

	    // Listar todos
	    public List<Paciente> findAll() {
	        return pacienteRepository.findAll();
	    }

	    // Buscar por id
	    public Optional<Paciente> findById(Long id) {
	        return pacienteRepository.findById(id);
	    }

	    // Buscar por número de identificación
	    public Optional<Paciente> findByNumeroIdentificacion(String numeroIdentificacion) {
	        return pacienteRepository.findByNumeroIdentificacion(numeroIdentificacion);
	    }

	    // Buscar por email de usuario
	    public Optional<Paciente> findByUsuarioEmail(String email) {
	        return pacienteRepository.findByUsuarioEmail(email);
	    }

	    // Buscar por id de usuario
	    public Optional<Paciente> findByUsuarioId(Long userId) {
	        return pacienteRepository.findByUsuarioId(userId);
	    }

	    // Verifica si existe número de identificación
	    public boolean existsByNumeroIdentificacion(String numeroIdentificacion) {
	        return pacienteRepository.existsByNumeroIdentificacion(numeroIdentificacion);
	    }

	    // Guardar paciente
	    public Paciente save(Paciente paciente) {
	        return pacienteRepository.save(paciente);
	    }

	    // Eliminar por id
	    public void deleteById(Long id) {
	        pacienteRepository.deleteById(id);
	    }

	    // Crear paciente
	    public Paciente createPaciente(String nombre,
	                                   String numeroIdentificacion,
	                                   LocalDate fechaNacimiento,
	                                   String telefono,
	                                   String direccion,
	                                   User usuario) {
	        if (existsByNumeroIdentificacion(numeroIdentificacion)) {
	            throw new RuntimeException("Ya existe un paciente con el número de identificación: " + numeroIdentificacion);
	        }

	        Paciente paciente = new Paciente(nombre, numeroIdentificacion, fechaNacimiento);
	        paciente.setTelefono(telefono);
	        paciente.setDireccion(direccion);
	        paciente.setUsuario(usuario);

	        return save(paciente);
	    }

	    // Actualizar paciente
	    public Paciente updatePaciente(Long id,
	                                   String nombre,
	                                   String numeroIdentificacion,
	                                   LocalDate fechaNacimiento,
	                                   String telefono,
	                                   String direccion) {
	        Paciente paciente = findById(id)
	                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));

	        if (!paciente.getNumeroIdentificacion().equals(numeroIdentificacion) &&
	                existsByNumeroIdentificacion(numeroIdentificacion)) {
	            throw new RuntimeException("Ya existe un paciente con el número de identificación: " + numeroIdentificacion);
	        }

	        paciente.setNombre(nombre);
	        paciente.setNumeroIdentificacion(numeroIdentificacion);
	        paciente.setFechaNacimiento(fechaNacimiento);
	        paciente.setTelefono(telefono);
	        paciente.setDireccion(direccion);

	        return save(paciente);
	    }

	    // 🔹 Nuevo método: obtener por id y lanzar excepción si no existe
	    public Paciente getPacienteOrThrow(Long id) {
	        return findById(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
	    }

	    // 🔹 Nuevo método: eliminar y retornar estado
	    public void eliminarPaciente(Long id) {
	        Paciente paciente = getPacienteOrThrow(id);
	        pacienteRepository.delete(paciente);
	    }
	    
	    
	}

