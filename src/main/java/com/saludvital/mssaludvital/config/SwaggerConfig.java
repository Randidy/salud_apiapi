package com.saludvital.mssaludvital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("🏥 MS SaludVital - Sistema de Gestión Médica")
                        .description("""
                                # 🎯 **API REST para Sistema de Gestión Médica Integral**
                                
                                Sistema completo para la gestión de **pacientes, médicos, citas médicas, recetas y medicamentos** 
                                desarrollado con **Spring Boot 3.3.0** y **JWT Authentication**.
                                
                                ## 🚀 **Inicio Rápido**
                                
                                ### **1. 🔐 Autenticación**
                                Todos los endpoints (excepto `/auth/**`) requieren autenticación JWT:
                                
                                1. **Login**: `POST /auth/login` con credenciales
                                2. **Copiar token** de la respuesta  
                                3. **Autorizar**: Hacer clic en el botón 🔒 **Authorize** arriba
                                4. **Pegar token**: En el formato `Bearer {tu_token}`
                                
                                ### **2. 👥 Usuarios de Prueba**
                                
                                | **ROL** | **EMAIL** | **PASSWORD** | **PERMISOS** |
                                |---------|-----------|--------------|--------------|
                                | 👑 **ADMIN** | `admin@saludvital.com` | `admin123` | **Acceso total** - Gestión usuarios, supervisión |
                                | 🩺 **MÉDICO** | `medico@saludvital.com` | `medico123` | **Gestión médica** - Citas y recetas |
                                | 👤 **PACIENTE** | `paciente@saludvital.com` | `paciente123` | **Autogestión** - Solicitar citas, ver expediente |
                                
                                ### **3. 📋 Casos de Uso Sugeridos**
                                
                                #### **👑 Como Admin:**
                                1. Login → `POST /auth/login`
                                2. Crear médico → `POST /admin/medicos`
                                3. Ver todas las citas → `GET /citas`
                                4. Crear medicamento → `POST /medicamentos`
                                
                                #### **🩺 Como Médico:**
                                1. Login → `POST /auth/login`
                                2. Ver mis citas → `GET /citas/medico`
                                3. Completar cita → `PUT /citas/{id}/completar`
                                4. Emitir receta → `POST /recetas/nueva`
                                
                                #### **👤 Como Paciente:**
                                1. Login → `POST /auth/login`
                                2. Ver médicos → `GET /medico/disponibles`
                                3. Solicitar cita → `POST /citas/nueva`
                                4. Ver mis recetas → `GET /recetas/mis-recetas`
                                
                                ## ⚙️ **Reglas de Negocio**
                                
                                - ✅ **Citas**: Máximo 3/día por paciente, cancelación 2h antes
                                - ✅ **Recetas**: Validación de alergias, caducidad 30 días
                                - ✅ **JWT**: Tokens válidos por 24 horas
                                - ✅ **Roles**: Autorización granular por endpoint
                                
                                ## 📚 **Documentación Adicional**
                                
                                - **🛡️ Roles y Permisos**: Ver archivo `ROLES_Y_PERMISOS.md`
                                - **🔗 Postman**: Importar `MS_SaludVital_Postman_Collection.json`
                                - **📖 README**: Guía completa de instalación y uso
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipo MS SaludVital")
                                .email("soporte@saludvital.com")
                                .url("https://github.com/tu-usuario/ms-saludvital"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("📋 Documentación completa en GitHub")
                        .url("https://github.com/tu-usuario/ms-saludvital/blob/main/README.md"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("🖥️ Servidor Local de Desarrollo"),
                        new Server()
                                .url("https://api.saludvital.com")
                                .description("🌐 Servidor de Producción (Ejemplo)")
                ))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("""
                                        **🔑 Autenticación JWT Bearer Token**
                                        
                                        Para usar la API:
                                        1. Hacer login en `/auth/login`
                                        2. Copiar el token de la respuesta
                                        3. Usar formato: `Bearer {token}`
                                        
                                        **Ejemplo:**
                                        ```
                                        Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5NTY3ODkwLCJleHAiOjE2Mzk2NTQyOTB9.signature
                                        ```
                                        
                                        **⏰ Validez:** 24 horas
                                        """)));
    }
}