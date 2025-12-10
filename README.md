# Sistema de Nómina - Arquitectura Distribuida

Sistema de gestión de nómina implementado con arquitectura distribuida, compuesto por un backend API REST y un frontend web.

## Descripción

Este proyecto implementa un sistema completo de gestión de nómina para la Universidad Autónoma del Estado de México, utilizando una arquitectura distribuida que separa el backend (API REST) del frontend (interfaz de usuario web).

### Características Principales

- ✅ **Arquitectura Distribuida**: Backend y frontend como módulos independientes
- ✅ **API REST**: Backend desarrollado con Spring Boot 4.0.0
- ✅ **Frontend Responsivo**: Aplicación web con HTML5, CSS3, JavaScript y Bootstrap 5
- ✅ **Autenticación JWT**: Sistema seguro de autenticación basado en tokens y cookies
- ✅ **Gestión de Empleados**: Registro, consulta y administración de empleados
- ✅ **Cálculo de Nóminas**: Cálculo automático de ISR, IMSS y salario neto
- ✅ **Validación de Errores**: Manejo específico de errores de validación por campo
- ✅ **Base de Datos**: PostgreSQL para producción, H2 para pruebas
- ✅ **Cobertura de Pruebas**: 99% de cobertura con 226 pruebas unitarias en backend
- ✅ **Contenerización**: Soporte completo para Docker y Docker Compose

---

## Estructura del Proyecto

```
mx-uaemex-isii-distributed/
├── backend/                    # API REST (Spring Boot)
│   ├── src/                    # Código fuente Java
│   ├── docs/                   # Documentación del backend
│   │   ├── MANUAL-DE-USUARIO.md
│   │   ├── README-DOCKER.md
│   │   ├── RESUMEN-DE-PRUEBAS.md
│   │   └── uml/                # Diagramas UML
│   ├── Dockerfile              # Imagen Docker del backend
│   ├── pom.xml                 # Dependencias Maven
│   └── README.md               # Documentación detallada del backend
│
├── frontend/                   # Aplicación web (HTML + CSS + JS)
│   ├── src/                    # Código fuente frontend
│   │   ├── *.html              # 6 páginas HTML
│   │   ├── css/                # Bootstrap y estilos personalizados
│   │   └── js/                 # Lógica JavaScript (7 módulos)
│   ├── Dockerfile              # Imagen Docker del frontend (Nginx)
│   ├── nginx.conf              # Configuración Nginx
│   ├── README.md               # Documentación principal
│   ├── GUIA-RAPIDA.md          # Guía de inicio rápido
│   ├── FRONTEND-README.md      # Documentación técnica
│   ├── VALIDACION-ERRORES.md   # Sistema de validación
│   └── [otros docs].md         # Documentación adicional
│
├── docker-compose.yml          # Orquestación de servicios
├── .env                        # Variables de entorno
└── README.md                   # Este archivo
```

---

## Inicio Rápido

### Opción 1: Docker Compose (Recomendado)

La forma más sencilla de ejecutar todo el sistema:

1. **Crear archivo `.env` en la raíz del proyecto:**

```env
# Backend
BACKEND_PORT=8080
BACKEND_DB_URL=jdbc:postgresql://postgres:5432/nomina
BACKEND_DB_USERNAME=postgres
BACKEND_DB_PASSWORD=tu-password-seguro
BACKEND_JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits
BACKEND_JWT_EXPIRATION_MS=86400000

# Frontend
FRONTEND_PORT=3001
```

2. **Iniciar los servicios:**

```bash
docker-compose up -d
```

3. **Verificar que los servicios estén ejecutándose:**

```bash
docker-compose ps
```

4. **Acceder a los servicios:**
   - **Backend API:** http://localhost:3000
   - **Frontend:** http://localhost:3001

### Opción 2: Desarrollo Local

#### Backend

```bash
cd backend

# Configurar variables de entorno
export JWT_SECRET="tu-secreto-jwt-super-seguro"
export JWT_EXPIRATION_MS=86400000
export DB_URL="jdbc:h2:mem:testdb"
export DB_USERNAME="sa"
export DB_PASSWORD=""
export PORT=3000

# Ejecutar con Maven
mvn spring-boot:run
```

#### Frontend

```bash
cd frontend/src

# Opción 1: Con Python
python -m http.server 3001

# Opción 2: Con Node.js
npx http-server -p 3001

# Opción 3: Con PHP
php -S localhost:3001
```

**Acceder al frontend:** http://localhost:3001

**Nota:** Asegúrate de configurar la URL del backend en `frontend/src/js/config.js` si usas un puerto diferente.

---

## Documentación

### Backend (API REST)

- **[📖 README del Backend](backend/README.md)** - Documentación completa del backend
  - Guía de instalación y configuración
  - Arquitectura y patrones de diseño
  - Endpoints de la API REST
  - Ejemplos de uso
  
- **[🐳 Guía Docker](backend/docs/README-DOCKER.md)** - Despliegue con Docker
  - Construcción de imágenes
  - Variables de entorno
  - Docker Compose
  - Solución de problemas

- **[📘 Manual de Usuario - API](backend/docs/MANUAL-DE-USUARIO.md)** - Guía de uso de la API
  - Endpoints disponibles
  - Ejemplos con cURL
  - Códigos de respuesta
  - Preguntas frecuentes

- **[🧪 Resumen de Pruebas](backend/docs/RESUMEN-DE-PRUEBAS.md)** - Calidad del código
  - Estadísticas de pruebas
  - Cobertura de código
  - Matriz de pruebas

### Frontend (Aplicación Web)

- **[📖 README del Frontend](frontend/README.md)** - Resumen y enlaces principales
- **[🚀 Guía Rápida](frontend/GUIA-RAPIDA.md)** - Inicio rápido paso a paso
- **[📘 Documentación Técnica](frontend/FRONTEND-README.md)** - Documentación completa
  - Tecnologías utilizadas
  - Estructura del proyecto
  - Funcionalidades detalladas
  - Configuración y ejecución
- **[✅ Validación de Errores](frontend/VALIDACION-ERRORES.md)** - Sistema de validación
  - Manejo de errores 400
  - Validación por campo
  - Ejemplos de uso
- **[📋 Checklist](frontend/CHECKLIST.md)** - Lista de verificación de funcionalidades
- **[🧪 Datos de Prueba](frontend/DATOS-PRUEBA.md)** - Ejemplos para testing
- **[⚙️ Configuración](frontend/CONFIGURACION.md)** - Troubleshooting y configuración avanzada
- **[📦 Inventario](frontend/INVENTARIO.md)** - Lista completa de archivos creados
- **[📝 Resumen de Implementación](frontend/RESUMEN-IMPLEMENTACION.md)** - Resumen ejecutivo

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 4.0.0** - Framework principal
- **Spring Security** - Autenticación y autorización
- **JWT (JJWT 0.13.0)** - Tokens de autenticación
- **PostgreSQL** - Base de datos (producción)
- **H2 Database** - Base de datos (pruebas)
- **JUnit 5 + Mockito** - Testing
- **JaCoCo** - Cobertura de código
- **Maven** - Gestión de dependencias
- **Docker** - Contenerización

### Frontend
- **HTML5** - Estructura de páginas
- **CSS3** - Estilos personalizados
- **JavaScript (ES6+)** - Lógica de negocio
- **Bootstrap 5** - Framework CSS (responsivo)
- **Fetch API** - Comunicación con backend
- **Nginx** - Servidor web (Docker)
- **Docker** - Contenerización

---

## API REST

### Endpoints Principales

#### Autenticación (Públicos)
- `POST /auth/register` - Registrar empleado
- `POST /auth/login` - Iniciar sesión (obtener token JWT)

#### Empleados (Protegidos)
- `GET /empleado/` - Listar todos los empleados

#### Nóminas (Protegidos)
- `GET /nomina/?rfc={rfc}` - Listar nóminas de un empleado
- `POST /nomina/` - Crear nueva nómina
- `DELETE /nomina/{id}` - Eliminar nómina

**Nota:** Los endpoints protegidos requieren token JWT. El frontend lo envía automáticamente mediante la cookie `access_token`.

### Ejemplo de Uso

```bash
# 1. Registrar un empleado
curl -X POST http://localhost:3000/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "JUAN CARLOS",
    "apellidos": "PEREZ GARCIA",
    "rfc": "PEGJ900101ABC",
    "correo": "juan.perez@example.com",
    "esAdmin": false
  }'

# 2. Iniciar sesión
curl -X POST http://localhost:3000/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@example.com",
    "password": "Admin123456*"
  }'

# 3. Usar el token para crear una nómina
curl -X POST http://localhost:3000/nomina/ \
  -H "Cookie: access_token={tu-token}" \
  -H "Content-Type: application/json" \
  -d '{
    "rfc": "PEGJ900101ABC",
    "fechaInicio": "2025-01-01",
    "fechaFin": "2025-01-15",
    "salario": 15000.00
  }'
```

---

## Flujo de Uso del Sistema Completo

### Para Usuarios Finales (Frontend)

1. **Acceder al sistema**: http://localhost:3001 (o el puerto configurado)
2. **Registrar un empleado administrador**:
   - Click en "Registrar Empleado"
   - Llenar formulario completo
   - Marcar "Es administrador"
   - Proporcionar contraseña
3. **Iniciar sesión**:
   - Ingresar correo y contraseña
   - El sistema guarda el token JWT en una cookie automáticamente
4. **Dashboard de administrador**:
   - Ver lista de empleados
   - Gestionar nóminas de cada empleado
5. **Calcular nómina**:
   - Seleccionar empleado desde dashboard
   - Ingresar salario y fechas
   - Ver resultado con desglose automático
6. **Consultar nóminas**:
   - Ver historial de nóminas por empleado
   - Eliminar nóminas si es necesario

### Para Desarrolladores (API Directa)

Ver sección [Ejemplo de Uso](#ejemplo-de-uso) para peticiones directas a la API.

---

## Pruebas

El backend cuenta con una **cobertura excepcional** del 99%:

- **226 pruebas unitarias** - 100% exitosas
- **99% cobertura de instrucciones**
- **100% cobertura de ramas**
- **99% cobertura de líneas**
- **100% cobertura de clases**

Ejecutar pruebas:

```bash
cd backend
mvn test

# Con reporte de cobertura
mvn clean test jacoco:report

# Ver reporte en: backend/target/site/jacoco/index.html
```

---

## Autores

Proyecto desarrollado para la clase de Ingeniería de Software II  
**Facultad de Ingeniería - Universidad Autónoma del Estado de México**

| Nombre            | Correo Institucional            | Correo Personal          |
|-------------------|---------------------------------|--------------------------|
| Fernando Espinosa | jfespinosas001@alumno.uaemex.mx | jferespinosa18@gmail.com |

---

## Licencia

Este proyecto es de uso académico para la Universidad Autónoma del Estado de México.

---

## Soporte

Para reportar problemas o solicitar nuevas características, contacta al equipo de desarrollo.

---

**Última actualización:** Diciembre 2025  
**Versión:** 0.0.1-SNAPSHOT
