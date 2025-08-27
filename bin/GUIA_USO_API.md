# 🚀 Guía Completa de Uso de la API Salud Vital

## 📋 Información General
- **Base URL**: `http://localhost:8081/api`
- **Autenticación**: JWT Bearer Token
- **Formato**: JSON
- **Puerto**: 8081

---

## 🔐 AUTENTICACIÓN

### 1. Registro de Paciente
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan.perez@email.com", 
  "password": "123456",
  "numeroIdentificacion": "12345678",
  "fechaNacimiento": "1990-05-15"
}
```

### 2. Login (Obtener Token)
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "juan.perez@email.com",
  "password": "123456"
}
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "userInfo": {
      "id": 1,
      "name": "Juan Pérez",
      "email": "juan.perez@email.com",
      "roles": ["ROLE_PACIENTE"]
    }
  }
}
```

---

## 🔑 USO DEL TOKEN JWT

### En Headers de cada request:
```http
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### En Postman:
1. Ve a la pestaña **Authorization**
2. Selecciona **Bearer Token**
3. Pega el token obtenido del login

---

## 👤 ENDPOINTS PARA PACIENTES

### Ver Mi Perfil
```http
GET /api/paciente/perfil
Authorization: Bearer {token}
```

### Ver Mi Expediente Médico
```http
GET /api/paciente/expediente
Authorization: Bearer {token}
```

---

## 🩺 ENDPOINTS PARA MÉDICOS

### Listar Médicos Disponibles
```http
GET /api/medico/disponibles
Authorization: Bearer {token}
```

### Ver Perfil del Médico (solo médicos)
```http
GET /api/medico/perfil
Authorization: Bearer {token}
```

---

## 📅 GESTIÓN DE CITAS

### Solicitar Nueva Cita (Pacientes)
```http
POST /api/citas/nueva
Authorization: Bearer {token}
Content-Type: application/json

{
  "medicoId": 1,
  "fecha": "2024-12-20",
  "hora": "10:00:00",
  "motivo": "Consulta general"
}
```

### Ver Mis Citas (Pacientes)
```http
GET /api/citas/mis-citas
Authorization: Bearer {token}
```

### Ver Citas del Médico (Médicos)
```http
GET /api/citas/medico
Authorization: Bearer {token}
```

### Modificar Cita
```http
PUT /api/citas/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "medicoId": 1,
  "fecha": "2024-12-21",
  "hora": "11:00:00",
  "motivo": "Consulta de seguimiento"
}
```

### Cancelar Cita
```http
PUT /api/citas/{id}/cancelar
Authorization: Bearer {token}
```

### Completar Cita (Médicos)
```http
PUT /api/citas/{id}/completar
Authorization: Bearer {token}
```

---

## 💊 GESTIÓN DE RECETAS

### Emitir Nueva Receta (Médicos)
```http
POST /api/recetas/nueva
Authorization: Bearer {token}
Content-Type: application/json

{
  "pacienteId": 1,
  "items": [
    {
      "medicamentoId": 1,
      "dosis": "500mg",
      "frecuencia": "Cada 8 horas"
    },
    {
      "medicamentoId": 2,
      "dosis": "200mg", 
      "frecuencia": "Cada 12 horas"
    }
  ]
}
```

### Ver Mis Recetas (Pacientes)
```http
GET /api/recetas/mis-recetas
Authorization: Bearer {token}
```

### Ver Recetas Emitidas (Médicos)
```http
GET /api/recetas/medico
Authorization: Bearer {token}
```

---

## 🧪 GESTIÓN DE MEDICAMENTOS

### Listar Todos los Medicamentos
```http
GET /api/medicamentos
Authorization: Bearer {token}
```

### Buscar Medicamentos
```http
GET /api/medicamentos/buscar?nombre=paracetamol
Authorization: Bearer {token}
```

### Crear Medicamento (Solo Admin)
```http
POST /api/medicamentos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Aspirina",
  "descripcion": "Analgésico y antiinflamatorio"
}
```

---

## ⚙️ ADMINISTRACIÓN (Solo Admin)

### Crear Paciente
```http
POST /api/admin/pacientes
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "María García",
  "email": "maria.garcia@email.com",
  "password": "123456",
  "numeroIdentificacion": "87654321",
  "fechaNacimiento": "1985-10-20"
}
```

### Crear Médico
```http
POST /api/admin/medicos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Dr. Carlos",
  "apellido": "Rodríguez",
  "numeroLicencia": "MED123456",
  "telefono": "+1234567890",
  "email": "dr.carlos@hospital.com",
  "password": "123456",
  "especialidad": "CARDIOLOGIA",
  "tarifaConsulta": 150.00
}
```

---

## 🎯 ESPECIALIDADES DISPONIBLES

```
CARDIOLOGIA
DERMATOLOGIA
GASTROENTEROLOGIA
GINECOLOGIA
NEUROLOGIA
OFTALMOLOGIA
PEDIATRIA
PSIQUIATRIA
TRAUMATOLOGIA
UROLOGIA
MEDICINA_GENERAL
```

---

## 📊 CÓDIGOS DE RESPUESTA

- **200**: Éxito
- **201**: Creado exitosamente
- **400**: Error en los datos enviados
- **401**: No autenticado (token inválido/expirado)
- **403**: Sin permisos (rol insuficiente)
- **404**: Recurso no encontrado
- **500**: Error interno del servidor

---

## 🔄 FLUJO TÍPICO DE USO

### Para Pacientes:
1. **Registro**: `POST /api/auth/register`
2. **Login**: `POST /api/auth/login` → Guardar token
3. **Ver médicos**: `GET /api/medico/disponibles`
4. **Solicitar cita**: `POST /api/citas/nueva`
5. **Ver mis citas**: `GET /api/citas/mis-citas`
6. **Ver mis recetas**: `GET /api/recetas/mis-recetas`

### Para Médicos:
1. **Login**: `POST /api/auth/login` → Guardar token
2. **Ver mis citas**: `GET /api/citas/medico`
3. **Completar citas**: `PUT /api/citas/{id}/completar`
4. **Emitir recetas**: `POST /api/recetas/nueva`
5. **Ver recetas emitidas**: `GET /api/recetas/medico`

### Para Administradores:
1. **Login**: `POST /api/auth/login` → Guardar token
2. **Crear médicos**: `POST /api/admin/medicos`
3. **Crear pacientes**: `POST /api/admin/pacientes`
4. **Gestionar medicamentos**: `POST /api/medicamentos`
5. **Ver toda la información**: `GET /api/citas`, `GET /api/recetas`

---

## 🚨 VALIDACIONES IMPORTANTES

### Citas:
- Máximo 3 citas por paciente por día
- No se pueden agendar en horarios ocupados
- Cancelación/modificación mínimo 2 horas antes

### Recetas:
- No se pueden recetar medicamentos a los que el paciente es alérgico
- Medicamentos deben existir en el catálogo

### Usuarios:
- Emails únicos por usuario
- Números de identificación únicos para pacientes
- Números de licencia únicos para médicos

---

## 🔗 Documentación de la API

### **Swagger UI Integrado**
**URL**: `http://localhost:8081/api/swagger-ui.html`
¡Documentación interactiva con ejemplos y pruebas directas!

### **Archivos de Colección**
📄 **Postman Collection**: `Postman_Collection_SaludVital.json`
📄 **OpenAPI/Swagger**: `swagger_openapi_saludvital.yaml`

### **Usuarios Predefinidos Incluidos**
Todas las colecciones incluyen los usuarios predefinidos:

#### 👑 **ADMIN**
- **Email**: `admin@saludvital.com`
- **Password**: `admin123`

#### 🩺 **MÉDICO** 
- **Email**: `medico@saludvital.com`
- **Password**: `medico123`

#### 👤 **PACIENTE**
- **Email**: `paciente@saludvital.com`
- **Password**: `paciente123`