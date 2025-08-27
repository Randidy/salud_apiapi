package com.saludvital.mssaludvital.service;

import com.saludvital.mssaludvital.entity.Medico;
import com.saludvital.mssaludvital.entity.User;
import com.saludvital.mssaludvital.enums.Especialidad;
import com.saludvital.mssaludvital.enums.EstadoDoctor;
import com.saludvital.mssaludvital.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> findById(Long id) {
        return medicoRepository.findById(id);
    }

    public Optional<Medico> findByNumeroLicencia(String numeroLicencia) {
        return medicoRepository.findByNumeroLicencia(numeroLicencia);
    }

    public Optional<Medico> findByUsuarioEmail(String email) {
        return medicoRepository.findByUsuarioEmail(email);
    }

    public Optional<Medico> findByUsuarioId(Long userId) {
        return medicoRepository.findByUsuarioId(userId);
    }

    public List<Medico> findByEspecialidad(Especialidad especialidad) {
        return medicoRepository.findByEspecialidad(especialidad);
    }

    public List<Medico> findByEstado(EstadoDoctor estado) {
        return medicoRepository.findByEstado(estado);
    }

    public List<Medico> findByEspecialidadAndEstado(Especialidad especialidad, EstadoDoctor estado) {
        return medicoRepository.findByEspecialidadAndEstado(especialidad, estado);
    }

    public List<Medico> findMedicosDisponibles() {
        return medicoRepository.findMedicosDisponibles();
    }

    public boolean existsByNumeroLicencia(String numeroLicencia) {
        return medicoRepository.existsByNumeroLicencia(numeroLicencia);
    }

    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void deleteById(Long id) {
        medicoRepository.deleteById(id);
    }

    public Medico createMedico(String nombre, String apellido, String numeroLicencia,
                              String telefono, String email, Especialidad especialidad,
                              BigDecimal tarifaConsulta, User usuario) {
        if (existsByNumeroLicencia(numeroLicencia)) {
            throw new RuntimeException("Ya existe un médico con el número de licencia: " + numeroLicencia);
        }
        
        Medico medico = new Medico(nombre, apellido, numeroLicencia, especialidad, tarifaConsulta);
        medico.setTelefono(telefono);
        medico.setEmail(email);
        medico.setUsuario(usuario);
        medico.setEstado(EstadoDoctor.ACTIVO);
        medico.setDisponible(true);
        
        return save(medico);
    }

    public Medico updateMedico(Long id, String nombre, String apellido, String numeroLicencia,
                              String telefono, String email, Especialidad especialidad,
                              BigDecimal tarifaConsulta, EstadoDoctor estado, Boolean disponible) {
        Medico medico = findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + id));
        
        if (!medico.getNumeroLicencia().equals(numeroLicencia) && 
            existsByNumeroLicencia(numeroLicencia)) {
            throw new RuntimeException("Ya existe un médico con el número de licencia: " + numeroLicencia);
        }
        
        medico.setNombre(nombre);
        medico.setApellido(apellido);
        medico.setNumeroLicencia(numeroLicencia);
        medico.setTelefono(telefono);
        medico.setEmail(email);
        medico.setEspecialidad(especialidad);
        medico.setTarifaConsulta(tarifaConsulta);
        medico.setEstado(estado);
        medico.setDisponible(disponible);
        
        return save(medico);
    }
}