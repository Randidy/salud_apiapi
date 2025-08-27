# 🏥 **MS SALUDVITAL - Sistema de Gestión Médica**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-red.svg)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/API-Swagger%20UI-green.svg)](https://swagger.io/)

## 📋 **Descripción**

**MS SaludVital** es un microservicio REST API completo para la gestión integral de un sistema médico. Desarrollado con **Spring Boot 3.3.0** y **Java 17**, implementa autenticación JWT, autorización basada en roles, y todas las funcionalidades necesarias para administrar pacientes, médicos, citas médicas, recetas y medicamentos.

### ✨ **Características Principales**

- 🔐 **Autenticación JWT** con tokens Bearer de 24h de validez
- 👥 **Sistema de roles** (ADMIN, MÉDICO, PACIENTE) con permisos granulares
- 🏥 **Gestión completa** de pacientes, médicos, citas y recetas
- 💊 **Catálogo de medicamentos** con validaciones de alergias
- 📅 **Sistema de citas** con reglas de negocio (máximo 3/día, cancelación 2h antes)
- 📊 **Documentación Swagger** interactiva
- ⚡ **API REST** diseñada para integración con Angular/React
- 🗄️ **Base de datos MySQL** con datos de prueba precargados

## 🛠️ **Tecnologías Utilizadas**
- **☕ Java 17** - Lenguaje de programación
- **🍃 Spring Boot 3.3.0** - Framework principal
- **🔒 Spring Security 6** - Seguridad y autenticación
- **📊 Spring Data JPA** - Persistencia de datos
- **🗄️ MySQL 8** - Base de datos relacional
- **🔑 JWT (JSON Web Token)** - Autenticación stateless
- **📚 Swagger/OpenAPI 3** - Documentación de API
- **📦 Maven** - Gestión de dependencias

---

## 🚀 **Inicio Rápido**

### **1. 📋 Prerrequisitos**
```bash
✅ Java 17 o superior
✅ MySQL 8.0 o superior  
✅ Maven 3.6 o superior
✅ Git (opcional)
```

### **2. 🗄️ Configuración de Base de Datos**
```sql
# 1. Conectar a MySQL
mysql -u root -p

# 2. Crear base de datos
CREATE DATABASE salud;
exit;
```

### **3. ⚙️ Configuración del Proyecto**
```bash
# 1. Clonar o descargar el proyecto
cd /path/to/project/ms_saludvital

# 2. Verificar application.properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/salud
spring.datasource.username=root
spring.datasource.password=tu_password_mysql
```

### **4. 🔥 Ejecutar la Aplicación**
```bash
# Opción A: Con Maven
mvn spring-boot:run

# Opción B: Con JAR compilado
mvn clean package
java -jar target/ms-saludvital-0.0.1-SNAPSHOT.jar
```

### **5. ✅ Verificar Instalación**
- **🌐 API Base**: http://localhost:8081/
- **📚 Swagger UI**: http://localhost:8081/swagger-ui.html
- **📋 Health Check**: http://localhost:8081/actuator/health

---

## 👥 **Usuarios Predefinidos**

El sistema incluye **3 usuarios de prueba** con diferentes roles:

| **ROL** | **EMAIL** | **PASSWORD** | **DESCRIPCIÓN** |
|---------|-----------|--------------|-----------------|
| 👑 **ADMIN** | `admin@saludvital.com` | `admin123` | **Acceso total** - Gestión de usuarios, medicamentos, supervisión |
| 🩺 **MÉDICO** | `medico@saludvital.com` | `medico123` | **Gestión médica** - Citas, recetas, perfil |
| 👤 **PACIENTE** | `paciente@saludvital.com` | `paciente123` | **Autogestión** - Solicitar citas, ver expediente |

---

## 🔧 **Uso de la API**

### **🔐 Paso 1: Autenticación**
```bash
# Login para obtener token JWT
POST http://localhost:8081/auth/login
Content-Type: application/json

{
  "email": "admin@saludvital.com",
  "password": "admin123"
}

# Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "name": "Administrador",
    "email": "admin@saludvital.com",
    "roles": ["ROLE_ADMIN"]
  }
}
```

### **🛡️ Paso 2: Usar Token en Requests**
```bash
# Agregar header Authorization en TODAS las peticiones
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### **📚 Paso 3: Explorar con Swagger**
Visita **http://localhost:8081/swagger-ui.html** para:
- 📖 Ver todos los endpoints disponibles
- 🧪 Probar la API directamente desde el navegador
- 📋 Ver esquemas de datos y ejemplos
- 🔐 Autenticarse y probar con diferentes roles

---

## 📊 **Endpoints Principales**

### **🔐 Autenticación**
```bash
POST /auth/login          # Iniciar sesión
POST /auth/register       # Registrar nuevo paciente
```

### **👑 Administración (Solo ADMIN)**
```bash
GET  /admin/pacientes     # Listar todos los pacientes
POST /admin/pacientes     # Crear nuevo paciente
POST /admin/medicos       # Crear nuevo médico
PUT  /admin/medicos/{id}  # Actualizar médico
```

### **📅 Gestión de Citas**
```bash
POST /citas/nueva         # Solicitar cita (PACIENTE)
GET  /citas/mis-citas     # Ver mis citas (PACIENTE)  
GET  /citas/medico        # Ver citas asignadas (MÉDICO)
PUT  /citas/{id}/completar # Completar cita (MÉDICO)
GET  /citas               # Ver todas las citas (ADMIN)
```

### **💊 Gestión de Recetas**
```bash
POST /recetas/nueva       # Emitir receta (MÉDICO)
GET  /recetas/mis-recetas # Ver mis recetas (PACIENTE)
GET  /recetas/medico      # Ver recetas emitidas (MÉDICO)
GET  /recetas             # Ver todas las recetas (ADMIN)
```

### **💉 Medicamentos**
```bash
GET    /medicamentos      # Listar medicamentos (Todos)
POST   /medicamentos      # Crear medicamento (ADMIN)
PUT    /medicamentos/{id} # Actualizar medicamento (ADMIN)
DELETE /medicamentos/{id} # Eliminar medicamento (ADMIN)
```

**📖 Para la lista completa de endpoints y permisos, consulta: [ROLES_Y_PERMISOS.md](ROLES_Y_PERMISOS.md)**

---

## 🏗️ **Arquitectura del Proyecto**

```
src/main/java/com/saludvital/mssaludvital/
├── 📁 config/              # Configuración (Security, Swagger)
├── 📁 controller/          # Controladores REST
├── 📁 dto/                 # DTOs para requests/responses
├── 📁 entity/              # Entidades JPA (User, Paciente, Medico, etc.)
├── 📁 enums/               # Enumeraciones (Roles, Especialidades)
├── 📁 exception/           # Manejo de excepciones
├── 📁 repository/          # Repositorios JPA
├── 📁 security/            # JWT, filtros de seguridad
├── 📁 service/             # Lógica de negocio
└── 📄 MsSaludvitalApplication.java
```

### **🗄️ Modelo de Base de Datos**

**Entidades principales:**
- **User** - Usuarios del sistema con roles
- **Paciente** - Información de pacientes  
- **Medico** - Información de médicos
- **Cita** - Citas médicas
- **Receta** - Recetas médicas con items
- **Medicamento** - Catálogo de medicamentos
- **Role** - Roles del sistema

---

## ⚙️ **Reglas de Negocio Implementadas**

### **📅 Gestión de Citas**
- ✅ **Máximo 3 citas por día** por paciente
- ✅ **Sin horarios duplicados** para el mismo médico  
- ✅ **Cancelación 2h antes** - Modificación/cancelación requiere 2+ horas de anticipación
- ✅ **Solo médicos completan** - Solo médicos pueden marcar citas como completadas
- ✅ **Horarios médicos** - Citas solo en horarios de atención

### **💊 Gestión de Recetas**
- ✅ **Validación de alergias** - No recetar medicamentos que causen alergia al paciente
- ✅ **Medicamentos existentes** - Los medicamentos deben existir en el catálogo
- ✅ **Caducidad automática** - Recetas expiran a los 30 días
- ✅ **Solo médicos emiten** - Solo médicos pueden crear recetas

### **🔐 Seguridad y Validaciones**
- ✅ **IDs únicos** - Números de identificación/licencia únicos
- ✅ **Edad automática** - Cálculo automático desde fecha de nacimiento
- ✅ **Emails únicos** - Validación de emails únicos por usuario
- ✅ **JWT 24h** - Tokens expiran a las 24 horas
- ✅ **Roles específicos** - Cada endpoint valida permisos específicos

---

## 🧪 **Testing y Desarrollo**

### **🔍 Herramientas de Testing**
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **Postman**: Importar [colección Postman](MS_SaludVital_Postman_Collection.json)
- **curl**: Ejemplos en [ROLES_Y_PERMISOS.md](ROLES_Y_PERMISOS.md)

### **📋 Casos de Prueba Sugeridos**

#### **👑 Como Admin:**
1. Login → Crear médico → Crear paciente → Ver todas las citas
2. Crear medicamentos → Supervisar recetas del sistema

#### **🩺 Como Médico:**  
1. Login → Ver perfil → Ver mis citas → Completar cita
2. Emitir receta → Ver mis recetas emitidas

#### **👤 Como Paciente:**
1. Login → Ver médicos disponibles → Solicitar cita  
2. Ver mis citas → Cancelar cita → Ver mis recetas

---

## 🛠️ **Resolución de Problemas**

### **❌ Error de Conexión MySQL**
```bash
# Verificar que MySQL esté corriendo
sudo systemctl status mysql
sudo systemctl start mysql

# Crear base de datos
mysql -u root -p
CREATE DATABASE salud;
```

### **❌ Puerto 8081 Ocupado**  
```properties
# Cambiar en application.properties
server.port=8082
```

### **❌ 401 Unauthorized**
- Verificar header: `Authorization: Bearer {token}`
- Token expirado: hacer login nuevamente
- Formato correcto del header

### **❌ 403 Forbidden**
- Usuario sin permisos para el endpoint
- Verificar rol del usuario
- Consultar [ROLES_Y_PERMISOS.md](ROLES_Y_PERMISOS.md)

---

## 🏆 **Estado del Proyecto**

✨ **¡Proyecto completamente funcional y listo para usar!**

✅ **Implementado y probado:**
- Autenticación JWT con roles diferenciados
- Autorización granular por endpoint  
- Gestión completa de todas las entidades
- Validaciones de negocio según especificaciones
- API REST documentada con Swagger
- Base de datos con datos de prueba
- Integración lista para Angular/React

---

## 📚 **Documentación Adicional**

- **🛡️ [Roles y Permisos](ROLES_Y_PERMISOS.md)** - Guía completa de autorización
- **🔗 [Colección Postman](MS_SaludVital_Postman_Collection.json)** - Endpoints listos para probar
- **📖 [Swagger UI](http://localhost:8081/swagger-ui.html)** - Documentación interactiva

---

## 🚀 **¡Comienza Ahora!**

1. **📥 Clona** el repositorio
2. **🗄️ Configura** MySQL  
3. **▶️ Ejecuta** `mvn spring-boot:run`
4. **🌐 Visita** http://localhost:8081/swagger-ui.html
5. **🔐 Haz login** con `admin@saludvital.com / admin123`
6. **🧪 Prueba** todos los endpoints

**¡Tu sistema de gestión médica está listo para usar!** 🏥✨