package com.saludvital.mssaludvital;

import com.saludvital.mssaludvital.entity.*;
import com.saludvital.mssaludvital.enums.RoleName;
import com.saludvital.mssaludvital.enums.Especialidad;
import com.saludvital.mssaludvital.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsSaludvitalApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MsSaludvitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear roles por defecto si no existen
        Role adminRole, medicoRole, pacienteRole;
        if (roleRepository.count() == 0) {
            adminRole = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            medicoRole = roleRepository.save(new Role(RoleName.ROLE_MEDICO));
            pacienteRole = roleRepository.save(new Role(RoleName.ROLE_PACIENTE));
        } else {
            adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElse(null);
            medicoRole = roleRepository.findByName(RoleName.ROLE_MEDICO).orElse(null);
            pacienteRole = roleRepository.findByName(RoleName.ROLE_PACIENTE).orElse(null);
        }

        // Crear usuarios de ejemplo si no existen
        if (userRepository.count() == 0) {
            // ADMIN
            User adminUser = new User("Administrador", "admin@saludvital.com", 
                passwordEncoder.encode("admin123"));
            adminUser.getRoles().add(adminRole);
            userRepository.save(adminUser);

            // MÉDICO
            User medicoUser = new User("Dr. Carlos Rodríguez", "medico@saludvital.com", 
                passwordEncoder.encode("medico123"));
            medicoUser.getRoles().add(medicoRole);
            User savedMedicoUser = userRepository.save(medicoUser);

            Medico medico = new Medico("Dr. Carlos", "Rodríguez", "MED123456", 
                Especialidad.CARDIOLOGIA, new java.math.BigDecimal("150.00"));
            medico.setTelefono("+1234567890");
            medico.setEmail("medico@saludvital.com");
            medico.setUsuario(savedMedicoUser);
            medicoRepository.save(medico);

            // PACIENTE  
            User pacienteUser = new User("Juan Pérez", "paciente@saludvital.com", 
                passwordEncoder.encode("paciente123"));
            pacienteUser.getRoles().add(pacienteRole);
            User savedPacienteUser = userRepository.save(pacienteUser);

            Paciente paciente = new Paciente("Juan Pérez", "12345678", 
                java.time.LocalDate.of(1990, 5, 15));
            paciente.setUsuario(savedPacienteUser);
            pacienteRepository.save(paciente);
        }

        // Crear medicamentos básicos si no existen
        if (medicamentoRepository.count() == 0) {
            medicamentoRepository.save(new Medicamento("Paracetamol", "Analgésico y antipirético"));
            medicamentoRepository.save(new Medicamento("Ibuprofeno", "Antiinflamatorio no esteroideo"));
            medicamentoRepository.save(new Medicamento("Amoxicilina", "Antibiótico de amplio espectro"));
            medicamentoRepository.save(new Medicamento("Omeprazol", "Inhibidor de la bomba de protones"));
            medicamentoRepository.save(new Medicamento("Losartán", "Antihipertensivo"));
            medicamentoRepository.save(new Medicamento("Metformina", "Antidiabético"));
            medicamentoRepository.save(new Medicamento("Atorvastatina", "Reductor de colesterol"));
            medicamentoRepository.save(new Medicamento("Salbutamol", "Broncodilatador"));
            medicamentoRepository.save(new Medicamento("Loratadina", "Antihistamínico"));
            medicamentoRepository.save(new Medicamento("Diclofenaco", "Antiinflamatorio y analgésico"));
        }
    }
}