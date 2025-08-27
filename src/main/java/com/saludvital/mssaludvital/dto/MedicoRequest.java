package com.saludvital.mssaludvital.dto;

public class MedicoRequest {

	
        private String nombre;
        private String apellido;
        private String numeroLicencia;
        private String telefono;
        private String email;
        private String password;
        private com.saludvital.mssaludvital.enums.Especialidad especialidad;
        private java.math.BigDecimal tarifaConsulta;

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getNumeroLicencia() { return numeroLicencia; }
        public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public com.saludvital.mssaludvital.enums.Especialidad getEspecialidad() { return especialidad; }
        public void setEspecialidad(com.saludvital.mssaludvital.enums.Especialidad especialidad) { this.especialidad = especialidad; }
        public java.math.BigDecimal getTarifaConsulta() { return tarifaConsulta; }
        public void setTarifaConsulta(java.math.BigDecimal tarifaConsulta) { this.tarifaConsulta = tarifaConsulta; }
    }