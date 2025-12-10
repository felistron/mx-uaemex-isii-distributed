# Sistema de Nómina - Arquitectura Distribuida

Sistema de gestión de nómina implementado con arquitectura distribuida, compuesto por un backend API REST y un frontend (en desarrollo).

## Descripción

Este proyecto implementa un sistema completo de gestión de nómina para la Universidad Autónoma del Estado de México, utilizando una arquitectura distribuida que separa el backend (API REST) del frontend (interfaz de usuario).

### Características Principales

- ✅ **Arquitectura Distribuida**: Backend y frontend como módulos independientes
- ✅ **API REST**: Backend desarrollado con Spring Boot 4.0.0
- ✅ **Autenticación JWT**: Sistema seguro de autenticación basado en tokens
- ✅ **Gestión de Empleados**: Registro y consulta de empleados
- ✅ **Cálculo de Nóminas**: Cálculo automático de ISR, IMSS y salario neto
- ✅ **Base de Datos**: PostgreSQL para producción, H2 para pruebas
- ✅ **Cobertura de Pruebas**: 99% de cobertura con 226 pruebas unitarias
- ✅ **Contenerización**: Soporte completo para Docker y Docker Compose

---

## Estructura del Proyecto

```
mx-uaemex-isii-distributed/
├── backend/                    # API REST (Spring Boot)
│   ├── src/                    # Código fuente
│   ├── docs/                   # Documentación del backend
│   ├── Dockerfile              # Imagen Docker del backend
│   ├── pom.xml                 # Dependencias Maven
│   └── README.md               # Documentación detallada del backend
│
├── frontend/                   # Aplicación web (En desarrollo)
│   ├── src/                    # Código fuente
│   ├── Dockerfile              # Imagen Docker del frontend
│   └── README.md               # Documentación del frontend
│
├── docker-compose.yml          # Orquestación de servicios
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
BACKEND_JWT_EXPIRATION_TIME=86400000

# Frontend (cuando esté disponible)
FRONTEND_PORT=3000
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
   - **Backend API:** http://localhost:8080
   - **Frontend:** http://localhost:3000 (cuando esté disponible)

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
export PORT=8080

# Ejecutar con Maven
mvn spring-boot:run
```

#### Frontend (cuando esté disponible)

```bash
cd frontend
# Instrucciones pendientes
```

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

### Frontend (Próximamente)

- **[📖 README del Frontend](frontend/README.md)** - Documentación del frontend (en desarrollo)

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

### Frontend (En desarrollo)
- Tecnologías por definir

---

## API REST

### Endpoints Principales

#### Autenticación (Públicos)
- `POST /auth/register` - Registrar empleado
- `POST /auth/login` - Iniciar sesión (obtener token JWT)

#### Empleados (Protegidos)
- `GET /empleado/` - Listar todos los empleados
- `GET /empleado/{rfc}` - Obtener empleado por RFC

#### Nóminas (Protegidos)
- `GET /nomina/?rfc={rfc}` - Listar nóminas de un empleado
- `POST /nomina/` - Crear nueva nómina
- `DELETE /nomina/{id}` - Eliminar nómina

**Nota:** Los endpoints protegidos requieren token JWT en el header:
```
Authorization: Bearer {token}
```

### Ejemplo de Uso

```bash
# 1. Registrar un empleado
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "JUAN CARLOS",
    "apellidos": "PEREZ GARCIA",
    "rfc": "PEGJ900101ABC",
    "correo": "juan.perez@example.com",
    "esAdmin": false
  }'

# 2. Iniciar sesión
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@example.com",
    "password": "Admin123456*"
  }'

# 3. Usar el token para crear una nómina
curl -X POST http://localhost:8080/nomina/ \
  -H "Authorization: Bearer {tu-token}" \
  -H "Content-Type: application/json" \
  -d '{
    "rfc": "PEGJ900101ABC",
    "fechaInicio": "2025-01-01",
    "fechaFin": "2025-01-15",
    "salario": 15000.00
  }'
```

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
