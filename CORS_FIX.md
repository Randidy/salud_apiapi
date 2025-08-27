# CORS Fix Applied

## ✅ Changes Made

### 1. **SecurityConfig.java**
- **Added CORS configuration** allowing `http://localhost:4200` and `http://127.0.0.1:4200`
- **Enabled all HTTP methods**: GET, POST, PUT, DELETE, OPTIONS, PATCH
- **Allowed all headers** and credentials
- **Set max age** to 1 hour for preflight caching

### 2. **CorsConfig.java** (New File)
- **Additional Web MVC CORS configuration** for redundancy
- **Same permissions** as SecurityConfig

### 3. **Angular Service**
- **Updated API URL** to include `/api` context path: `http://localhost:8081/api/auth`

## 🚀 How to Test

### 1. Restart Backend
```bash
cd /mnt/e/Andrea_proyecto/ms_saludvital
mvn spring-boot:run
```

### 2. Start Angular Frontend
```bash
cd /mnt/e/Andrea_proyecto/saludvital_angul
npm start
```

### 3. Test Login
1. Go to http://localhost:4200
2. Click on any test user button to fill credentials
3. Click "Entrar"
4. Should no longer see CORS errors in browser console
5. Should redirect to appropriate dashboard based on role

## 🔧 CORS Configuration Details

**Allowed Origins:**
- `http://localhost:4200` (Angular dev server)
- `http://127.0.0.1:4200` (alternative localhost)

**Allowed Methods:**
- GET, POST, PUT, DELETE, OPTIONS, PATCH

**Allowed Headers:**
- All headers (`*`)

**Credentials:**
- Enabled (allows cookies and authorization headers)

**Exposed Headers:**
- `Authorization`, `Content-Type`

## 🐛 If CORS Still Fails

1. **Check browser console** for exact error message
2. **Verify backend is running** on `http://localhost:8081`
3. **Verify frontend is running** on `http://localhost:4200`
4. **Clear browser cache** and try again
5. **Check network tab** in DevTools to see actual request/response headers

## 📋 Test Users

| Role | Email | Password |
|------|--------|----------|
| Admin | admin@saludvital.com | admin123 |
| Médico | medico@saludvital.com | medico123 |
| Paciente | paciente@saludvital.com | paciente123 |