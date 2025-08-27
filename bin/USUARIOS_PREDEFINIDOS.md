# 👥 **GUÍA DE USUARIOS PREDEFINIDOS - MS SALUDVITAL**

## 🎯 **Usuarios de Prueba Disponibles**

El sistema incluye **3 usuarios predefinidos** con diferentes roles para testing completo de la funcionalidad:

| **👤 ROL** | **📧 EMAIL** | **🔑 PASSWORD** | **🎭 DESCRIPCIÓN** |
|-----------|-------------|----------------|-------------------|
| 👑 **ADMINISTRADOR** | `admin@saludvital.com` | `admin123` | **Acceso TOTAL** - Control completo del sistema |
| 🩺 **MÉDICO** | `medico@saludvital.com` | `medico123` | **Gestión Médica** - Citas y recetas profesionales |
| 👤 **PACIENTE** | `paciente@saludvital.com` | `paciente123` | **Autogestión** - Portal personal de salud |

---

## 👑 **ADMINISTRADOR - Control Total**

### **📋 Información del Usuario**
```json
{
  "email": "admin@saludvital.com",
  "password": "admin123"
}
```

- **Nombre completo**: Administrador del Sistema
- **Email**: `admin@saludvital.com`
- **Password**: `admin123`
- **Rol**: `ROLE_ADMIN`
- **ID**: `1`

### **🔥 Capacidades Completas**
✅ **Gestión de usuarios** - Crear médicos y pacientes  
✅ **Supervisión total** - Ver TODAS las citas, recetas y datos del sistema  
✅ **Gestión de medicamentos** - Crear, modificar, eliminar medicamentos  
✅ **Control administrativo** - Configuración y administración del sistema  

---

## 🩺 **MÉDICO - Gestión Profesional**

### **📋 Información del Usuario**
```json
{
  "email": "medico@saludvital.com", 
  "password": "medico123"
}
```

- **Nombre completo**: Dr. Juan Medico
- **Email**: `medico@saludvital.com`
- **Password**: `medico123`
- **Rol**: `ROLE_MEDICO`
- **ID**: `2`
- **Especialidad**: Medicina General
- **Número de Licencia**: `MED001`
- **Teléfono**: `+1234567890`

### **⚕️ Capacidades Médicas**
✅ **Sus citas médicas** - Ver y completar SOLO sus citas asignadas  
✅ **Emitir recetas** - Crear recetas médicas con validación de alergias  
✅ **Historial de recetas** - Ver todas las recetas que ha emitido  
✅ **Consulta de medicamentos** - Acceso completo al catálogo para recetar  

---

## 👤 **PACIENTE - Portal Personal**

### **📋 Información del Usuario**
```json
{
  "email": "paciente@saludvital.com",
  "password": "paciente123"
}
```

- **Nombre completo**: Ana Paciente
- **Email**: `paciente@saludvital.com`
- **Password**: `paciente123`
- **Rol**: `ROLE_PACIENTE`
- **ID**: `3`
- **Número de Identificación**: `12345678`
- **Fecha de Nacimiento**: `1990-01-15`
- **Edad**: 34 años (calculada automáticamente)

### **🏥 Capacidades de Autogestión**
✅ **Sus citas médicas** - Solicitar, ver, modificar y cancelar SOLO sus citas  
✅ **Su expediente médico** - Ver historial, diagnósticos, alergias y tratamientos  
✅ **Sus recetas médicas** - Ver todas las recetas emitidas para él  
✅ **Consulta de médicos** - Ver médicos disponibles y especialidades

---

## 🚀 **Casos de Uso Prácticos**

### **🧪 Testing Rápido con cURL**

#### **1. Login Admin**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@saludvital.com",
    "password": "admin123"
  }'
```

#### **2. Login Médico**  
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "medico@saludvital.com", 
    "password": "medico123"
  }'
```

#### **3. Login Paciente**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "paciente@saludvital.com",
    "password": "paciente123"
  }'
```

### **💡 Respuesta de Login Típica**
```json
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

---

## 🔄 **Flujos de Testing Completos**

### **👑 Como Administrador:**
```bash
# 1. Login y obtener token
POST /auth/login

# 2. Ver todos los pacientes del sistema
GET /admin/pacientes
Authorization: Bearer {token}

# 3. Crear nuevo médico especialista
POST /admin/medicos
{
  "nombre": "Dr. Ana",
  "apellido": "García", 
  "numeroLicencia": "MED67890",
  "telefono": "+987654321",
  "email": "ana.garcia@hospital.com",
  "password": "medico123",
  "especialidad": "DERMATOLOGIA",
  "tarifaConsulta": 200.00
}

# 4. Ver todas las citas del sistema
GET /citas

# 5. Crear medicamento
POST /medicamentos
{
  "nombre": "Amoxicilina 500mg",
  "descripcion": "Antibiótico de amplio espectro"
}
```

### **🩺 Como Médico:**
```bash
# 1. Login y obtener token
POST /auth/login

# 2. Ver mi perfil médico
GET /medico/perfil

# 3. Ver mis citas asignadas
GET /citas/medico

# 4. Completar una cita
PUT /citas/1/completar

# 5. Emitir receta médica
POST /recetas/nueva
{
  "pacienteId": 3,
  "items": [
    {
      "medicamentoId": 1,
      "dosis": "1 tableta",
      "frecuencia": "Cada 8 horas por 7 días"
    }
  ]
}

# 6. Ver mis recetas emitidas
GET /recetas/medico
```

### **👤 Como Paciente:**
```bash
# 1. Login y obtener token  
POST /auth/login

# 2. Ver mi perfil
GET /paciente/perfil

# 3. Ver mi expediente médico
GET /paciente/expediente

# 4. Ver médicos disponibles
GET /medico/disponibles

# 5. Solicitar nueva cita
POST /citas/nueva
{
  "medicoId": 2,
  "fecha": "2024-02-15", 
  "hora": "09:30",
  "motivo": "Consulta general por dolor de espalda"
}

# 6. Ver mis citas programadas
GET /citas/mis-citas

# 7. Ver mis recetas médicas
GET /recetas/mis-recetas

# 8. Cancelar una cita (con 2h anticipación)
PUT /citas/1/cancelar
```

---

## ⚠️ **Restricciones y Validaciones**

### **❌ Lo que NO puede hacer cada rol:**

#### **🩺 MÉDICO:**
- ❌ Crear o cancelar citas (solo completar las suyas)
- ❌ Ver citas de otros médicos  
- ❌ Crear/modificar/eliminar medicamentos
- ❌ Funciones administrativas

#### **👤 PACIENTE:**
- ❌ Emitir recetas médicas
- ❌ Completar citas
- ❌ Ver datos de otros pacientes
- ❌ Funciones médicas o administrativas

### **⚡ Reglas de Negocio:**
- **Máximo 3 citas por día** por paciente
- **Cancelación/modificación** requiere mínimo 2h anticipación  
- **JWT tokens** expiran a las 24 horas
- **Validación de alergias** en recetas médicas

---

## 🧪 **Escenarios de Testing Específicos**

### **🔐 Testing de Seguridad**
```bash
# 1. Sin token → 401 Unauthorized
GET /citas/mis-citas
# (Sin header Authorization)

# 2. Token incorrecto → 403 Forbidden  
GET /admin/pacientes
Authorization: Bearer {token_de_paciente}

# 3. Endpoint no autorizado → 403 Forbidden
POST /medicamentos  
Authorization: Bearer {token_de_medico}
```

### **⚠️ Testing de Validaciones**
```bash
# 1. Máximo 3 citas por día
# Solicitar 4ta cita del mismo día → Error de validación

# 2. Cancelación tardía
# Cancelar cita con menos de 2h → Error de validación  

# 3. Medicamento alérgico
# Recetar medicamento que causa alergia → Error de validación
```

---

## 🛠️ **Herramientas de Testing**

### **📚 Swagger UI**
- **URL**: http://localhost:8081/swagger-ui.html
- **Uso**: Interfaz gráfica completa con autenticación
- **Ventaja**: No necesitas herramientas adicionales

### **🔗 Postman**
- **Archivo**: `MS_SaludVital_Postman_Collection.json`
- **Uso**: Colección organizada por roles
- **Ventaja**: Token se guarda automáticamente

### **💻 cURL**
- **Documentación**: Ejemplos arriba
- **Uso**: Testing desde terminal
- **Ventaja**: Ideal para CI/CD y scripts

---

## 🎯 **Flujo de Testing Recomendado**

### **Fase 1: Validación Básica**
1. **Login** con cada usuario → Verificar tokens
2. **Endpoints propios** → Verificar acceso a datos personales  
3. **Endpoints restringidos** → Verificar errores 403

### **Fase 2: Funcionalidad Core**  
1. **Paciente**: Solicitar cita → Ver médicos → Crear cita
2. **Médico**: Ver citas → Completar cita → Emitir receta
3. **Admin**: Crear médico → Supervisar sistema → Gestionar medicamentos

### **Fase 3: Validaciones de Negocio**
1. **Límites**: 3 citas por día, 2h cancelación
2. **Alergias**: Validación en recetas
3. **Seguridad**: Cross-role access, token expiration

---

## 📞 **Documentación Adicional**

- **🛡️ Permisos detallados**: `ROLES_Y_PERMISOS.md`
- **📖 Guía completa**: `README.md`  
- **🔗 Colección Postman**: `MS_SaludVital_Postman_Collection.json`
- **📚 API Docs**: http://localhost:8081/swagger-ui.html

---

## 🏆 **¡Sistema Listo para Testing!**

Con estos **3 usuarios predefinidos** puedes probar:

✅ **Todos los roles** y sus permisos específicos  
✅ **Flujos completos** de gestión médica  
✅ **Validaciones de seguridad** y negocio  
✅ **Integración completa** de la API REST

**🚀 ¡Comienza con el usuario que prefieras y explora todas las funcionalidades!** 🏥✨