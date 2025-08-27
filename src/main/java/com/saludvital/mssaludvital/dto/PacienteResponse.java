package com.saludvital.mssaludvital.dto;

import java.time.LocalDate;
import java.util.List;

public class PacienteResponse {
    private Long id;
    private String nombre;
    private String numeroIdentificacion;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private boolean tieneAlergias;
    private List<String> alergias;  // puedes devolver solo nombres de alergias
    private int citas;
    private int recetas;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isTieneAlergias() { return tieneAlergias; }
    public void setTieneAlergias(boolean tieneAlergias) { this.tieneAlergias = tieneAlergias; }

    public List<String> getAlergias() { return alergias; }
    public void setAlergias(List<String> alergias) { this.alergias = alergias; }

    public int getCitas() { return citas; }
    public void setCitas(int citas) { this.citas = citas; }

    public int getRecetas() { return recetas; }
    public void setRecetas(int recetas) { this.recetas = recetas; }
}
