package com.saludvital.mssaludvital.service;

import com.saludvital.mssaludvital.entity.Cita;
import com.saludvital.mssaludvital.entity.Medico;
import com.saludvital.mssaludvital.entity.Paciente;
import com.saludvital.mssaludvital.enums.EstadoCita;
import com.saludvital.mssaludvital.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public List<Cita> findByPacienteId(Long pacienteId) {
        return citaRepository.findByPacienteIdOrderByFechaDesc(pacienteId);
    }

    public List<Cita> findByMedicoId(Long medicoId) {
        return citaRepository.findByMedicoIdOrderByFechaDesc(medicoId);
    }

    public List<Cita> findByPacienteIdAndEstado(Long pacienteId, EstadoCita estado) {
        return citaRepository.findByPacienteIdAndEstado(pacienteId, estado);
    }

    public List<Cita> findByMedicoIdAndEstado(Long medicoId, EstadoCita estado) {
        return citaRepository.findByMedicoIdAndEstado(medicoId, estado);
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }

    public Cita crearCita(Paciente paciente, Medico medico, LocalDate fecha, 
                         LocalTime hora, String motivo) {
        validarCita(paciente, medico, fecha, hora);
        
        Cita cita = new Cita(fecha, hora, paciente, medico, motivo, medico.getTarifaConsulta());
        cita.setEstado(EstadoCita.PROGRAMADA);
        
        return save(cita);
    }

    private void validarCita(Paciente paciente, Medico medico, LocalDate fecha, LocalTime hora) {
        // Validar que no se puedan agendar más de 3 citas por día
        Long citasDelDia = citaRepository.countCitasPacienteEnFecha(paciente.getId(), fecha);
        if (citasDelDia >= 3) {
            throw new RuntimeException("No se pueden programar más de 3 citas en el mismo día");
        }
        
        // Validar que no haya conflicto de horario
        List<Cita> citasConflicto = citaRepository.findByMedicoAndFechaAndHora(medico.getId(), fecha, hora);
        if (!citasConflicto.isEmpty()) {
            throw new RuntimeException("El horario seleccionado ya está ocupado");
        }
        
        // Validar que la cita sea en el futuro
        LocalDateTime fechaHoraCita = LocalDateTime.of(fecha, hora);
        if (fechaHoraCita.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se pueden programar citas en el pasado");
        }
    }

    public Cita actualizarCita(Long id, LocalDate fecha, LocalTime hora, String motivo) {
        Cita cita = findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        
        // Validar que se pueda modificar (al menos 2 horas de anticipación)
        LocalDateTime fechaHoraActual = LocalDateTime.of(cita.getFecha(), cita.getHora());
        if (fechaHoraActual.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede modificar la cita con menos de 2 horas de anticipación");
        }
        
        validarCita(cita.getPaciente(), cita.getMedico(), fecha, hora);
        
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo);
        
        return save(cita);
    }

    public Cita cancelarCita(Long id) {
        Cita cita = findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        
        // Validar que se pueda cancelar (al menos 2 horas de anticipación)
        LocalDateTime fechaHoraCita = LocalDateTime.of(cita.getFecha(), cita.getHora());
        if (fechaHoraCita.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede cancelar la cita con menos de 2 horas de anticipación");
        }
        
        cita.setEstado(EstadoCita.CANCELADA);
        return save(cita);
    }

    public Cita completarCita(Long id) {
        Cita cita = findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        
        cita.setEstado(EstadoCita.COMPLETADA);
        return save(cita);
    }
}