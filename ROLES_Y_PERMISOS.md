# 🛡️ **SISTEMA DE ROLES Y PERMISOS - MS SALUDVITAL**

## 🎯 **GUÍA RÁPIDA DE INICIO**

### **📱 Usuarios Predefinidos para Pruebas**
| **ROL** | **EMAIL** | **PASSWORD** | **DESCRIPCIÓN** |
|---------|-----------|--------------|-----------------|
| 👑 **ADMIN** | `admin@saludvital.com` | `admin123` | **Acceso TOTAL** - Gestión completa del sistema |
| 🩺 **MÉDICO** | `medico@saludvital.com` | `medico123` | **Gestión Médica** - Citas y recetas |
| 👤 **PACIENTE** | `paciente@saludvital.com` | `paciente123` | **Autogestión** - Solicitar citas, ver expediente |

### **🚀 Pasos para Probar**
1. **Login**: `POST /auth/login` con credenciales de arriba
2. **Copiar Token**: Del campo `token` en la respuesta
3. **Headers**: Agregar `Authorization: Bearer {tu_token}` en todas las peticiones
4. **Swagger UI**: Visitar `http://localhost:8081/swagger-ui.html`

---

## 📊 **TABLA COMPLETA DE PERMISOS POR ENDPOINT**

| **ENDPOINT** | **MÉTODO** | **👑 ADMIN** | **🩺 MÉDICO** | **👤 PACIENTE** | **DESCRIPCIÓN** |
|--------------|------------|------------|--------------|----------------|-----------------|
| **🔐 AUTENTICACIÓN** |||||
| `/auth/login` | POST | ✅ | ✅ | ✅ | Iniciar sesión |
| `/auth/register` | POST | ✅ | ✅ | ✅ | Registrar nuevo paciente |
| **👑 ADMINISTRACIÓN** |||||
| `/admin/pacientes` | GET | ✅ | ❌ | ❌ | Ver todos los pacientes |
| `/admin/pacientes` | POST | ✅ | ❌ | ❌ | Crear nuevo paciente |
| `/admin/pacientes/{id}` | PUT | ✅ | ❌ | ❌ | Actualizar paciente |
| `/admin/medicos` | POST | ✅ | ❌ | ❌ | Crear nuevo médico |
| `/admin/medicos/{id}` | PUT | ✅ | ❌ | ❌ | Actualizar médico |
| **👤 PACIENTES** |||||
| `/paciente/perfil` | GET | ❌ | ❌ | ✅ | Ver perfil propio |
| `/paciente/expediente` | GET | ❌ | ❌ | ✅ | Ver expediente médico propio |
| **🩺 MÉDICOS** |||||
| `/medico/perfil` | GET | ❌ | ✅ | ❌ | Ver perfil médico propio |
| `/medico/disponibles` | GET | ✅ | ✅ | ✅ | Listar médicos disponibles |
| `/medico` | GET | ✅ | ❌ | ❌ | Listar todos los médicos |
| `/medico/{id}` | GET | ✅ | ✅ | ✅ | Ver médico específico |
| **📅 CITAS MÉDICAS** |||||
| `/citas/nueva` | POST | ❌ | ❌ | ✅ | Solicitar nueva cita |
| `/citas/mis-citas` | GET | ❌ | ❌ | ✅ | Ver mis citas |
| `/citas/medico` | GET | ❌ | ✅ | ❌ | Ver citas asignadas |
| `/citas` | GET | ✅ | ❌ | ❌ | Ver TODAS las citas |
| `/citas/{id}` | PUT | ✅ | ❌ | ✅* | Modificar cita (*solo propia) |
| `/citas/{id}/cancelar` | PUT | ✅ | ❌ | ✅* | Cancelar cita (*solo propia) |
| `/citas/{id}/completar` | PUT | ✅ | ✅ | ❌ | Completar cita |
| **💊 RECETAS MÉDICAS** |||||
| `/recetas/nueva` | POST | ❌ | ✅ | ❌ | Emitir nueva receta |
| `/recetas/mis-recetas` | GET | ❌ | ❌ | ✅ | Ver mis recetas |
| `/recetas/medico` | GET | ❌ | ✅ | ❌ | Ver recetas emitidas |
| `/recetas` | GET | ✅ | ❌ | ❌ | Ver TODAS las recetas |
| `/recetas/{id}` | GET | ✅ | ✅ | ✅* | Ver receta específica (*solo propia) |
| **💉 MEDICAMENTOS** |||||
| `/medicamentos` | GET | ✅ | ✅ | ✅ | Listar medicamentos |
| `/medicamentos/{id}` | GET | ✅ | ✅ | ✅ | Ver medicamento específico |
| `/medicamentos/buscar` | GET | ✅ | ✅ | ✅ | Buscar medicamentos |
| `/medicamentos` | POST | ✅ | ❌ | ❌ | Crear medicamento |
| `/medicamentos/{id}` | PUT | ✅ | ❌ | ❌ | Actualizar medicamento |
| `/medicamentos/{id}` | DELETE | ✅ | ❌ | ❌ | Eliminar medicamento |

---

## 🎯 **PERMISOS DETALLADOS POR ROL**

### 👑 **ROL ADMIN** - **SUPER ADMINISTRADOR**
**🔥 Acceso TOTAL al sistema - Puede hacer TODO**

#### ✅ **Capacidades Completas:**
- 👥 **Gestión de Usuarios**: Crear médicos y pacientes desde panel admin
- 📊 **Supervisión Total**: Ver TODAS las citas, expedientes y recetas del sistema
- 💊 **Gestión de Medicamentos**: Crear, editar, eliminar medicamentos
- 📅 **Control de Citas**: Ver, modificar, cancelar CUALQUIER cita del sistema
- 🔧 **Administración**: Gestión completa de roles y usuarios

#### 📍 **Endpoints Exclusivos:**
```bash
# Panel de Administración
✅ GET    /admin/pacientes        - Ver todos los pacientes
✅ POST   /admin/pacientes        - Crear nuevos pacientes
✅ PUT    /admin/pacientes/{id}   - Actualizar cualquier paciente
✅ POST   /admin/medicos          - Crear nuevos médicos
✅ PUT    /admin/medicos/{id}     - Actualizar cualquier médico

# Supervisión del Sistema
✅ GET    /citas                  - Ver TODAS las citas del sistema
✅ GET    /recetas                - Ver TODAS las recetas del sistema
✅ GET    /medico                 - Ver lista completa de médicos

# Gestión de Medicamentos
✅ POST   /medicamentos           - Crear nuevos medicamentos
✅ PUT    /medicamentos/{id}      - Actualizar medicamentos
✅ DELETE /medicamentos/{id}      - Eliminar medicamentos

# Control Total de Citas
✅ PUT    /citas/{id}/cancelar    - Cancelar CUALQUIER cita
✅ PUT    /citas/{id}/completar   - Completar CUALQUIER cita
```

---

### 🩺 **ROL MÉDICO** - **GESTOR MÉDICO**
**⚕️ Gestión médica profesional - Atención y recetas**

#### ✅ **Capacidades Médicas:**
- 👨‍⚕️ **Perfil Médico**: Ver y gestionar su perfil profesional
- 📅 **Sus Citas**: Ver y completar SOLO sus citas asignadas
- 💊 **Emitir Recetas**: Crear recetas médicas para cualquier paciente
- 🔍 **Consultar Medicamentos**: Acceso completo al catálogo para recetar
- 📋 **Sus Recetas**: Ver historial de recetas que ha emitido

#### 📍 **Endpoints Permitidos:**
```bash
# Gestión de Perfil
✅ GET    /medico/perfil          - Ver su perfil médico personal
✅ GET    /medico/disponibles     - Ver lista de médicos disponibles
✅ GET    /medico/{id}            - Ver información de cualquier médico

# Gestión de Citas
✅ GET    /citas/medico           - Ver SOLO sus citas asignadas
✅ PUT    /citas/{id}/completar   - Completar SOLO sus citas

# Gestión de Recetas
✅ POST   /recetas/nueva          - Emitir nuevas recetas a cualquier paciente
✅ GET    /recetas/medico         - Ver SOLO las recetas que ha emitido
✅ GET    /recetas/{id}           - Ver recetas específicas (propias o de pacientes)

# Consulta de Medicamentos
✅ GET    /medicamentos           - Ver catálogo completo de medicamentos
✅ GET    /medicamentos/{id}      - Ver información de medicamento específico
✅ GET    /medicamentos/buscar    - Buscar medicamentos por nombre
```

#### ❌ **Restricciones:**
- ❌ **Crear o cancelar citas** (solo completar las suyas)
- ❌ **Ver citas de otros médicos** (solo las propias)
- ❌ **Crear/modificar medicamentos** (solo consultar)
- ❌ **Funciones administrativas** (crear usuarios, etc.)
- ❌ **Ver todas las recetas del sistema** (solo las propias)

---

### 👤 **ROL PACIENTE** - **AUTOGESTIÓN MÉDICA**
**🏥 Portal del paciente - Gestión personal de salud**

#### ✅ **Capacidades del Paciente:**
- 👤 **Su Perfil**: Ver y gestionar su información personal
- 📋 **Su Expediente**: Acceso completo a su historial médico, alergias y enfermedades
- 📅 **Sus Citas**: Solicitar, ver, modificar y cancelar SOLO sus citas
- 💊 **Sus Recetas**: Ver todas las recetas emitidas para él
- 🔍 **Consultar Médicos**: Ver médicos disponibles para seleccionar especialista
- 💉 **Consultar Medicamentos**: Ver información de medicamentos (solo lectura)

#### 📍 **Endpoints Permitidos:**
```bash
# Gestión Personal
✅ GET    /paciente/perfil        - Ver su perfil personal
✅ GET    /paciente/expediente    - Ver su expediente médico completo

# Consulta de Médicos
✅ GET    /medico/disponibles     - Ver médicos activos y disponibles
✅ GET    /medico/{id}            - Ver información de médico específico

# Gestión de Citas
✅ POST   /citas/nueva            - Solicitar nueva cita médica
✅ GET    /citas/mis-citas        - Ver SOLO sus citas programadas
✅ PUT    /citas/{id}             - Modificar SOLO sus citas (con 2h anticipación)
✅ PUT    /citas/{id}/cancelar    - Cancelar SOLO sus citas (con 2h anticipación)

# Consulta de Recetas
✅ GET    /recetas/mis-recetas    - Ver SOLO las recetas emitidas para él
✅ GET    /recetas/{id}           - Ver receta específica propia

# Consulta de Medicamentos
✅ GET    /medicamentos           - Ver catálogo de medicamentos
✅ GET    /medicamentos/{id}      - Ver información de medicamento específico
✅ GET    /medicamentos/buscar    - Buscar medicamentos por nombre
```

#### ❌ **Restricciones:**
- ❌ **Emitir recetas médicas** (solo médicos)
- ❌ **Completar citas** (solo médicos)
- ❌ **Ver datos de otros pacientes** (solo los propios)
- ❌ **Ver citas de otros pacientes** (solo las propias)
- ❌ **Crear/modificar medicamentos** (solo admin)
- ❌ **Funciones médicas o administrativas**

#### ⚠️ **Reglas Especiales:**
- **Máximo 3 citas por día** por paciente
- **Cancelación/modificación** requiere mínimo 2 horas de anticipación
- **Solo puede ver** sus propios datos (citas, recetas, expediente)

---

## ⚙️ **REGLAS DE NEGOCIO Y VALIDACIONES**

### 📅 **GESTIÓN DE CITAS**
| **Regla** | **Descripción** | **Aplicable a** |
|-----------|----------------|-----------------|
| ✅ **Máximo 3 citas/día** | Un paciente no puede tener más de 3 citas en un día | 👤 PACIENTE |
| ✅ **No horarios duplicados** | Un médico no puede tener 2 citas a la misma hora | 🩺 MÉDICO |
| ✅ **Cancelación 2h antes** | Cancelar/modificar con mínimo 2 horas de anticipación | 👤 PACIENTE |
| ✅ **Solo médicos completan** | Solo médicos pueden marcar citas como completadas | 🩺 MÉDICO |
| ✅ **Horario médico** | Citas solo en horarios de atención del médico | 🩺 MÉDICO |

### 💊 **GESTIÓN DE RECETAS**
| **Regla** | **Descripción** | **Aplicable a** |
|-----------|----------------|-----------------|
| ✅ **Solo médicos emiten** | Solo médicos pueden crear recetas médicas | 🩺 MÉDICO |
| ✅ **Validación alergias** | No recetar medicamentos a los que el paciente es alérgico | 🩺 MÉDICO |
| ✅ **Medicamentos existentes** | Los medicamentos deben existir en el catálogo | 🩺 MÉDICO |
| ✅ **Caducidad automática** | Las recetas caducan automáticamente a los 30 días | Sistema |
| ✅ **Campos obligatorios** | Dosis, frecuencia y medicamento son obligatorios | 🩺 MÉDICO |

### 📋 **EXPEDIENTES MÉDICOS**
| **Regla** | **Descripción** | **Aplicable a** |
|-----------|----------------|-----------------|
| ✅ **Modificación restringida** | Solo médicos y admin pueden modificar diagnósticos | 🩺👑 MÉDICO/ADMIN |
| ✅ **Acceso propio** | Pacientes solo pueden ver su propio expediente | 👤 PACIENTE |
| ✅ **Identificación única** | Número de identificación debe ser único | Sistema |
| ✅ **Edad automática** | La edad se calcula automáticamente desde fecha nacimiento | Sistema |
| ✅ **Alergias obligatorias** | Si reporta alergias, la lista no puede estar vacía | 👤 PACIENTE |

### 🔐 **SEGURIDAD Y AUTENTICACIÓN**
| **Regla** | **Descripción** | **Aplicable a** |
|-----------|----------------|-----------------|
| ✅ **JWT Obligatorio** | Todas las rutas protegidas requieren token Bearer | Todos |
| ✅ **Token 24h validez** | Los tokens JWT expiran a las 24 horas | Sistema |
| ✅ **Roles específicos** | Cada endpoint valida roles específicos | Sistema |
| ✅ **Acceso a datos propios** | Los usuarios solo ven/modifican sus propios datos | 👤🩺 PAC/MED |
| ✅ **Passwords encriptadas** | Las contraseñas se almacenan con BCrypt | Sistema |

---

## 🚀 **EJEMPLOS PRÁCTICOS DE USO**

### 👑 **FLUJO COMPLETO COMO ADMIN**
**🔑 Credenciales:** `admin@saludvital.com / admin123`

```bash
# 1. 🔐 INICIAR SESIÓN
POST http://localhost:8081/auth/login
Content-Type: application/json
{
  "email": "admin@saludvital.com",
  "password": "admin123"
}

# 2. 👥 CREAR NUEVO MÉDICO
POST http://localhost:8081/admin/medicos
Authorization: Bearer {token}
Content-Type: application/json
{
  "nombre": "Carlos",
  "apellido": "Mendoza",
  "numeroLicencia": "MED12345",
  "telefono": "+1234567890",
  "email": "carlos.mendoza@hospital.com",
  "password": "medico123",
  "especialidad": "CARDIOLOGIA",
  "tarifaConsulta": 150.00
}

# 3. 👤 CREAR NUEVO PACIENTE
POST http://localhost:8081/admin/pacientes
Authorization: Bearer {token}
Content-Type: application/json
{
  "nombre": "María González",
  "email": "maria.gonzalez@email.com",
  "password": "paciente123",
  "numeroIdentificacion": "12345678",
  "fechaNacimiento": "1990-05-15"
}

# 4. 📊 VER TODAS LAS CITAS DEL SISTEMA
GET http://localhost:8081/citas
Authorization: Bearer {token}

# 5. 💉 CREAR NUEVO MEDICAMENTO
POST http://localhost:8081/medicamentos
Authorization: Bearer {token}
Content-Type: application/json
{
  "nombre": "Ibuprofeno 600mg",
  "descripcion": "Antiinflamatorio no esteroideo para dolor y fiebre"
}
```

### 🩺 **FLUJO COMPLETO COMO MÉDICO**
**🔑 Credenciales:** `medico@saludvital.com / medico123`

```bash
# 1. 🔐 INICIAR SESIÓN
POST http://localhost:8081/auth/login
Content-Type: application/json
{
  "email": "medico@saludvital.com",
  "password": "medico123"
}

# 2. 👨‍⚕️ VER MI PERFIL MÉDICO
GET http://localhost:8081/medico/perfil
Authorization: Bearer {token}

# 3. 📅 VER MIS CITAS ASIGNADAS
GET http://localhost:8081/citas/medico
Authorization: Bearer {token}

# 4. ✅ COMPLETAR UNA CITA
PUT http://localhost:8081/citas/1/completar
Authorization: Bearer {token}

# 5. 💊 EMITIR RECETA A PACIENTE
POST http://localhost:8081/recetas/nueva
Authorization: Bearer {token}
Content-Type: application/json
{
  "pacienteId": 1,
  "items": [
    {
      "medicamentoId": 1,
      "dosis": "1 tableta",
      "frecuencia": "Cada 8 horas por 7 días"
    }
  ]
}

# 6. 📋 VER MIS RECETAS EMITIDAS
GET http://localhost:8081/recetas/medico
Authorization: Bearer {token}
```

### 👤 **FLUJO COMPLETO COMO PACIENTE**
**🔑 Credenciales:** `paciente@saludvital.com / paciente123`

```bash
# 1. 🔐 INICIAR SESIÓN
POST http://localhost:8081/auth/login
Content-Type: application/json
{
  "email": "paciente@saludvital.com",
  "password": "paciente123"
}

# 2. 👤 VER MI PERFIL
GET http://localhost:8081/paciente/perfil
Authorization: Bearer {token}

# 3. 📋 VER MI EXPEDIENTE MÉDICO
GET http://localhost:8081/paciente/expediente
Authorization: Bearer {token}

# 4. 🔍 VER MÉDICOS DISPONIBLES
GET http://localhost:8081/medico/disponibles
Authorization: Bearer {token}

# 5. 📅 SOLICITAR NUEVA CITA
POST http://localhost:8081/citas/nueva
Authorization: Bearer {token}
Content-Type: application/json
{
  "medicoId": 1,
  "fecha": "2024-01-15",
  "hora": "09:00",
  "motivo": "Consulta general por dolor de cabeza"
}

# 6. 📅 VER MIS CITAS
GET http://localhost:8081/citas/mis-citas
Authorization: Bearer {token}

# 7. 💊 VER MIS RECETAS
GET http://localhost:8081/recetas/mis-recetas
Authorization: Bearer {token}

# 8. ❌ CANCELAR UNA CITA
PUT http://localhost:8081/citas/1/cancelar
Authorization: Bearer {token}
```

---

## 🛠️ **GUÍA DE RESOLUCIÓN DE PROBLEMAS**

### 🚨 **Errores de Autenticación**

#### **❌ 401 Unauthorized**
**Posibles causas y soluciones:**

| **Error** | **Causa** | **Solución** |
|-----------|-----------|-------------|
| `"message": "Full authentication is required"` | No se envió token JWT | ✅ Agregar header `Authorization: Bearer {token}` |
| Token inválido | Token mal formado o corrupto | ✅ Hacer login nuevamente para obtener token válido |
| Token expirado | Token tiene más de 24 horas | ✅ Hacer `POST /auth/login` para obtener nuevo token |
| Header mal formado | Falta "Bearer " antes del token | ✅ Usar formato: `Authorization: Bearer {tu_token}` |

#### **❌ 403 Forbidden**
**Posibles causas y soluciones:**

| **Error** | **Causa** | **Solución** |
|-----------|-----------|-------------|
| `"Access Denied"` | Usuario no tiene rol necesario | ✅ Verificar que el usuario tenga el rol correcto |
| Endpoint restringido | Ruta no permitida para el rol | ✅ Consultar tabla de permisos arriba |
| Token de rol incorrecto | Usando token de paciente para endpoint de médico | ✅ Hacer login con usuario del rol correcto |

### 📱 **Ejemplos de Headers Correctos**
```bash
# ✅ FORMATO CORRECTO
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5NTY3ODkwLCJleHAiOjE2Mzk2NTQyOTB9.signature

# ❌ FORMATOS INCORRECTOS
Authorization: eyJhbGciOiJIUzI1NiJ9...           # Falta "Bearer "
Bearer eyJhbGciOiJIUzI1NiJ9...                   # Falta "Authorization:"
Authorization: Token eyJhbGciOiJIUzI1NiJ9...      # "Token" en lugar de "Bearer"
```

### 🔧 **Problemas de Configuración**

#### **🗄️ Error de Base de Datos**
```bash
# Si aparece error de conexión MySQL:
# 1. Verificar que MySQL esté corriendo
sudo systemctl start mysql

# 2. Crear la base de datos
mysql -u root -p
CREATE DATABASE salud;
exit

# 3. Verificar application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/salud
```

#### **🚀 Error de Puerto**
```bash
# Si el puerto 8081 está ocupado:
# Cambiar en application.properties:
server.port=8082
```

### 📊 **Códigos de Estado HTTP**

| **Código** | **Significado** | **Acción Recomendada** |
|------------|----------------|------------------------|
| `200 OK` | ✅ Petición exitosa | Continuar normalmente |
| `400 Bad Request` | ❌ Datos de entrada incorrectos | Revisar formato JSON y campos obligatorios |
| `401 Unauthorized` | ❌ Token faltante/inválido | Hacer login y usar token Bearer |
| `403 Forbidden` | ❌ Sin permisos para el endpoint | Verificar rol de usuario |
| `404 Not Found` | ❌ Recurso no encontrado | Verificar ID existe en base de datos |
| `500 Internal Error` | ❌ Error del servidor | Revisar logs del servidor |

---

## 📋 **CHECKLIST DE VERIFICACIÓN**

### ✅ **Antes de usar la API**
- [ ] ✅ **MySQL corriendo** en puerto 3306
- [ ] ✅ **Base de datos "salud" creada**
- [ ] ✅ **Aplicación corriendo** en http://localhost:8081
- [ ] ✅ **Swagger disponible** en http://localhost:8081/swagger-ui.html

### ✅ **Para cada petición**
- [ ] ✅ **Content-Type** correcto (`application/json`)
- [ ] ✅ **Authorization header** con Bearer token (excepto login/register)
- [ ] ✅ **URL correcta** con puerto 8081
- [ ] ✅ **Método HTTP** correcto (GET/POST/PUT/DELETE)
- [ ] ✅ **JSON válido** en el body (para POST/PUT)

### ✅ **Verificación de roles**
- [ ] 👑 **Admin**: Puede acceder a `/admin/**`, `/citas`, `/recetas`
- [ ] 🩺 **Médico**: Puede acceder a `/medico/perfil`, `/citas/medico`, `/recetas/nueva`
- [ ] 👤 **Paciente**: Puede acceder a `/paciente/**`, `/citas/nueva`, `/citas/mis-citas`

---

## 🎯 **RESUMEN RÁPIDO DE ACCESO**

| **ENDPOINT PRINCIPAL** | **👑 ADMIN** | **🩺 MÉDICO** | **👤 PACIENTE** | **PÚBLICO** |
|------------------------|-------------|--------------|----------------|-------------|
| `/auth/login` | ✅ | ✅ | ✅ | ✅ |
| `/auth/register` | ✅ | ✅ | ✅ | ✅ |
| `/admin/**` | ✅ | ❌ | ❌ | ❌ |
| `/medico/**` | ✅* | ✅* | ✅* | ❌ |
| `/paciente/**` | ❌ | ❌ | ✅ | ❌ |
| `/citas/**` | ✅* | ✅* | ✅* | ❌ |
| `/recetas/**` | ✅* | ✅* | ✅* | ❌ |
| `/medicamentos` (GET) | ✅ | ✅ | ✅ | ❌ |
| `/medicamentos` (POST/PUT/DELETE) | ✅ | ❌ | ❌ | ❌ |

**Nota:** Los asteriscos (*) indican que el acceso depende del endpoint específico.

---

## 🏆 **¡SISTEMA LISTO PARA USAR!**

✨ **Sistema de gestión médica completamente funcional con:**
- ✅ **Autenticación JWT** con roles diferenciados
- ✅ **Autorización granular** por endpoint
- ✅ **Validaciones de negocio** según especificaciones
- ✅ **API REST completa** documentada con Swagger
- ✅ **Base de datos MySQL** con datos de prueba
- ✅ **Usuarios predefinidos** listos para testing

**🚀 ¡Comienza a probar con Swagger UI en http://localhost:8081/swagger-ui.html!**