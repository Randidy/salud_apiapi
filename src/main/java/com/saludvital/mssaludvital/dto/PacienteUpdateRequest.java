package com.saludvital.mssaludvital.dto;

import java.time.LocalDate;
import java.util.List;

public class PacienteUpdateRequest {

    // Datos del paciente
    private String nombre;
    private String numeroIdentificacion;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;

    // Datos del usuario
    private String email;
    private String password;

    // Alergias (solo IDs)
    private List<Long> alergiaIds;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Long> getAlergiaIds() { return alergiaIds; }
    public void setAlergiaIds(List<Long> alergiaIds) { this.alergiaIds = alergiaIds; }
}