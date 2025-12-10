# Backend - Sistema de Nómina (API REST)

## Descripción del Proyecto

API REST para sistema de gestión de nómina. Este módulo backend forma parte de una arquitectura distribuida y proporciona servicios para el registro de empleados, autenticación mediante JWT, cálculo de nóminas y administración del sistema.

**Versión:** 0.0.1-SNAPSHOT  
**Grupo:** mx.uaemex.fi  
**Artefacto:** backend  
**Arquitectura:** Distribuida (API REST)

---

## Tecnologías Utilizadas

### Lenguaje de Programación
- **Java 17** - Lenguaje principal de desarrollo

### Frameworks Principales
- **Spring Boot 4.0.0** - Framework base de la aplicación
    - Spring Boot Starter WebMVC - Desarrollo de APIs REST
    - Spring Boot Starter Data JPA - Persistencia de datos
    - Spring Boot Starter Security - Seguridad y autenticación
    - Spring Boot Starter Validation - Validación de datos

### Seguridad
- **Spring Security** - Gestión de autenticación y autorización
- **JJWT (JSON Web Tokens) 0.13.0** - Generación y validación de tokens JWT
    - jjwt-api
    - jjwt-impl
    - jjwt-jackson

### Base de Datos
- **PostgreSQL** - Base de datos relacional para producción
- **H2 Database** - Base de datos en memoria para pruebas

### Herramientas de Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Herramientas de desarrollo y recarga automática
- **Maven** - Gestión de dependencias y construcción del proyecto

### Testing
- **Spring Boot Starter Test** - Suite de pruebas unitarias
- **Spring Security Test** - Pruebas de seguridad
- **JUnit 5** - Framework de pruebas (incluido en Spring Boot Starter Test)
- **Mockito** - Framework de mocking (incluido en Spring Boot Starter Test)
- **JaCoCo 0.8.11** - Análisis de cobertura de código

### Herramientas de Construcción
- **Maven Compiler Plugin** - Compilación del proyecto
- **Maven Surefire Plugin 3.2.5** - Ejecución de pruebas
- **Spring Boot Maven Plugin** - Empaquetado de la aplicación

---

## Arquitectura

### Arquitectura General
El proyecto implementa una **arquitectura distribuida basada en API REST** con una clara **separación de responsabilidades** entre la presentación, lógica de negocio y persistencia de datos. Esta estructura facilita el mantenimiento, testing y la integración con otros servicios (como el frontend).

### Capas de la Aplicación

```
┌─────────────────────────────────────────────────────────┐
│         CAPA DE PRESENTACIÓN (presentation/)            │
│  • Controladores REST API                               │
│  • DTOs (Data Transfer Objects)                         │
│  • Filtros de seguridad (JWT)                           │
├─────────────────────────────────────────────────────────┤
│         CAPA DE LÓGICA DE NEGOCIO (logic/)              │
│  • Servicios de negocio                                 │
│  • Validadores personalizados                           │
│  • Excepciones del dominio                              │
│  • Reglas de negocio y cálculos                         │
├─────────────────────────────────────────────────────────┤
│         CAPA DE PERSISTENCIA (persistence/)             │
│  • Entidades JPA (Models)                               │
│  • Repositorios Spring Data JPA                         │
│  • Mapeo objeto-relacional                              │
├─────────────────────────────────────────────────────────┤
│         CONFIGURACIÓN (config/)                         │
│  • Spring Security Configuration                        │
│  • Bean Definitions                                     │
├─────────────────────────────────────────────────────────┤
│         BASE DE DATOS                                   │
│  • PostgreSQL (Producción)                              │
│  • H2 Database (Pruebas)                                │
└─────────────────────────────────────────────────────────┘
```

### Principios de Diseño Aplicados

- **Separación de Responsabilidades (SoC)**: Cada capa tiene responsabilidades bien definidas
- **Bajo Acoplamiento**: Las capas interactúan a través de interfaces bien definidas
- **Alta Cohesión**: Los componentes relacionados están agrupados en la misma capa
- **Inversión de Dependencias**: Las capas superiores dependen de abstracciones, no de implementaciones

### Estructura del Código

La aplicación está organizada en tres capas principales que reflejan una clara separación de responsabilidades:

```
src/main/java/mx/uaemex/fi/
├── presentation/          # CAPA DE PRESENTACIÓN
│   ├── controller/        # Controladores REST y MVC
│   ├── dto/              # Objetos de transferencia de datos
│   └── filter/           # Filtros de seguridad (JWT)
│
├── logic/                # CAPA DE LÓGICA DE NEGOCIO
│   ├── service/          # Servicios de negocio
│   ├── validation/       # Validadores personalizados
│   └── exception/        # Excepciones personalizadas
│
├── persistence/          # CAPA DE PERSISTENCIA
│   ├── model/            # Entidades del dominio (JPA)
│   └── repository/       # Repositorios JPA
│
├── config/               # Configuración de Spring Security y beans
└── Main.java             # Punto de entrada de la aplicación
```

### Componentes Principales

#### 1. **Capa de Presentación** (`presentation/`)
Responsable de exponer la API REST y manejar las peticiones HTTP:
- **Controladores** (`controller/`): Endpoints REST para autenticación, empleados y nóminas
    - `AuthController`: Endpoints de registro y login
    - `EmpleadoController`: Endpoints para gestión de empleados
    - `NominaController`: Endpoints para gestión de nóminas
- **DTOs** (`dto/`): Objetos para transferencia de datos entre capas
- **Filtros** (`filter/`): Filtros de seguridad JWT para validar tokens

#### 2. **Capa de Lógica de Negocio** (`logic/`)
Contiene toda la lógica de negocio y reglas de la aplicación:
- **Servicios** (`service/`):
    - **AuthService**: Lógica de autenticación y registro
    - **EmpleadoService**: Gestión de empleados
    - **NominaService**: Cálculo y gestión de nóminas
    - **JwtService**: Generación y validación de tokens JWT
    - **CustomUserDetailsService**: Servicio de autenticación de usuarios
    - **CalculoNominaService2025**: Cálculos de nómina para el año 2025
- **Validadores** (`validation/`): Validación de RFC, email, períodos, contraseñas, etc.
- **Excepciones** (`exception/`): Excepciones personalizadas del dominio

#### 3. **Capa de Persistencia** (`persistence/`)
Gestiona el acceso y persistencia de datos:
- **Modelos** (`model/`): Entidades JPA del dominio (Empleado, Nomina, Acceso)
- **Repositorios** (`repository/`): Interfaces JPA para acceso a datos

#### 4. **Configuración** (`config/`)
- **Security Config**: Configuración de Spring Security
- **Bean Definitions**: Definición de beans del contenedor Spring

### Patrones de Diseño Utilizados
- **Arquitectura en Capas (Layered Architecture)**: Separación clara entre presentación, lógica y persistencia
- **RESTful API**: Arquitectura de servicios REST para comunicación con clientes
- **DTO (Data Transfer Object)**: Transferencia de datos entre capas
- **Repository Pattern**: Abstracción del acceso a datos en la capa de persistencia
- **Service Layer Pattern**: Encapsulación de la lógica de negocio
- **Dependency Injection**: Inversión de control con Spring IoC
- **Filter Pattern**: Filtros para procesamiento de peticiones HTTP (autenticación JWT)
- **Builder Pattern**: Construcción de objetos complejos (Lombok @Builder)
- **Singleton Pattern**: Gestión de beans Spring (por defecto)

### Diagramas UML

El proyecto incluye **6 diagramas UML completos** en formato PlantUML que documentan toda la arquitectura:

**Diagramas de Estructura (6):**
1. **Diagrama Entidad-Relación** - Modelo de datos del sistema
2. **Diagrama de Clases - Capa de Persistencia** - Entidades JPA y Repositorios
3. **Diagrama de Clases - Capa de Servicios** - Service Layer y lógica de negocio
4. **Diagrama de Clases - Capa de Lógica de Negocio** - Validadores y Excepciones
5. **Diagrama de Clases - Capa de Presentación** - Controladores, DTOs y Filtros
6. **Diagrama de Despliegue** - Arquitectura de infraestructura y despliegue

**Accede a los diagramas en:** [docs/uml/](docs/uml/)

Los diagramas pueden visualizarse usando:
- PlantUML Online Server: https://www.plantuml.com/plantuml/uml/
- Extensión PlantUML para VS Code o IntelliJ IDEA

---

## Pruebas

El proyecto cuenta con una **cobertura de pruebas excepcional** con 226 pruebas unitarias distribuidas en 28 archivos de prueba.

### Estadísticas de Pruebas

| Métrica                        | Valor        |
|--------------------------------|--------------|
| **Total de Pruebas**           | 226          |
| **Pruebas Exitosas**           | 226 (100%)   |
| **Tiempo de Ejecución**        | ~41 segundos |
| **Cobertura de Instrucciones** | **99%** ✅    |
| **Cobertura de Ramas**         | **100%** ✅   |
| **Cobertura de Líneas**        | **99%** ✅    |
| **Cobertura de Métodos**       | **99%** ✅    |
| **Cobertura de Clases**        | **100%** ✅   |

### Distribución por Capa

- **Controladores**: 3 archivos, 62 pruebas (100% cobertura)
- **Servicios**: 7 archivos, 94 pruebas (100% cobertura)
- **Validadores**: 5 archivos, 27 pruebas (100% cobertura)
- **Filtros**: 1 archivo, 11 pruebas (82% cobertura)
- **DTOs**: 5 archivos, 10 pruebas (100% cobertura)
- **Modelos**: 3 archivos, 15 pruebas
- **Excepciones**: 2 archivos, 6 pruebas (100% cobertura)

### Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de cobertura JaCoCo
mvn clean test jacoco:report

# Ver reporte de cobertura
# El reporte se genera en: target/site/jacoco/index.html
```

**Para más detalles sobre las pruebas, consulta [RESUMEN-DE-PRUEBAS.md](docs/RESUMEN-DE-PRUEBAS.md)**

---

## Guía de Inicio Rápido

### Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **PostgreSQL** (para producción) o **H2** (para pruebas)
- **Docker** (opcional, para despliegue en contenedores)

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd mx-uaemex-isii-distributed/backend
```

### 2. Configurar Variables de Entorno

Crear un archivo `.env` o configurar las variables de entorno necesarias:

```bash
export JWT_SECRET="tu-secreto-jwt-super-seguro-de-al-menos-256-bits"
export JWT_EXPIRATION_MS=86400000
export DB_URL="jdbc:postgresql://localhost:5432/nomina"
export DB_USERNAME="postgres"
export DB_PASSWORD="tu-password"
export PORT=8080
```

Para pruebas, puedes usar H2:

```bash
export DB_URL="jdbc:h2:mem:testdb"
export DB_USERNAME="sa"
export DB_PASSWORD=""
```

### 3. Compilar el Proyecto

```bash
# Compilar sin ejecutar pruebas
mvn clean compile

# Compilar y ejecutar pruebas
mvn clean install

# Compilar y empaquetar (genera JAR)
mvn clean package
```

### 4. Ejecutar la Aplicación

#### Opción A: Usando Maven

```bash
# Ejecutar en modo desarrollo
mvn spring-boot:run
```

#### Opción B: Ejecutar el JAR generado

```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar el JAR
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### 5. Verificar la Aplicación

La API REST estará disponible en:

```
http://localhost:8080
```

### Endpoints Principales

- **POST** `/auth/register` - Registrar un nuevo empleado
- **POST** `/auth/login` - Iniciar sesión y obtener token JWT
- **GET** `/empleado/` - Obtener todos los empleados (requiere autenticación)
- **GET** `/empleado/{rfc}` - Obtener un empleado por RFC (requiere autenticación)
- **GET** `/nomina/?rfc={rfc}` - Obtener nóminas de un empleado (requiere autenticación)
- **POST** `/nomina/` - Crear una nueva nómina (requiere autenticación)
- **DELETE** `/nomina/{id}` - Eliminar una nómina (requiere autenticación)

---

## Despliegue con Docker

### Construcción de la Imagen

```bash
# Desde el directorio backend
docker build -t backend-nomina:latest .
```

### Ejecutar el Contenedor

#### Opción 1: Con variables de entorno en línea de comandos

```bash
docker run -d -p 8080:8080 \
  -e PORT=8080 \
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits \
  -e JWT_EXPIRATION_MS=86400000 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/nomina \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=tu-password \
  --name backend-nomina-app \
  backend-nomina:latest
```

#### Opción 2: Usando Docker Compose (recomendado)

El proyecto incluye un archivo `docker-compose.yml` en la raíz que orquesta tanto el backend como el frontend:

```bash
# Desde el directorio raíz del proyecto
docker-compose up -d
```

### Variables de Entorno Requeridas

Para Docker Compose, crear un archivo `.env` en la raíz con:

```env
# Backend
BACKEND_PORT=8080
BACKEND_DB_URL=jdbc:postgresql://postgres:5432/nomina
BACKEND_DB_USERNAME=postgres
BACKEND_DB_PASSWORD=tu-password
BACKEND_JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits
BACKEND_JWT_EXPIRATION_MS=86400000

# Frontend
FRONTEND_PORT=3001
```

### Comandos Útiles de Docker

```bash
# Ver contenedores en ejecución
docker ps

# Ver logs de la aplicación
docker logs backend-nomina-app

# Detener el contenedor
docker stop backend-nomina-app

# Iniciar el contenedor
docker start backend-nomina-app

# Eliminar el contenedor
docker rm backend-nomina-app

# Eliminar la imagen
docker rmi backend-nomina:latest
```

**Para una guía completa de Docker, consulta [README-DOCKER.md](docs/README-DOCKER.md)**

---

## Configuración

### Variables de Entorno Requeridas

La aplicación requiere las siguientes variables de entorno para funcionar:

| Variable            | Descripción                       | Ejemplo                                   | Requerido |
|---------------------|-----------------------------------|-------------------------------------------|-----------|
| `PORT`              | Puerto donde escucha el backend   | `8080`                                    | No (8080) |
| `JWT_SECRET`        | Secreto para firma de JWT         | `tu-secreto-seguro-de-256-bits`           | **Sí**    |
| `JWT_EXPIRATION_MS` | Tiempo de expiración del JWT (ms) | `86400000` (24 horas)                     | **Sí**    |
| `DB_URL`            | URL de conexión a base de datos   | `jdbc:postgresql://localhost:5432/nomina` | **Sí**    |
| `DB_USERNAME`       | Usuario de base de datos          | `postgres`                                | **Sí**    |
| `DB_PASSWORD`       | Contraseña de base de datos       | `tu-password`                             | **Sí**    |

### Archivo de Configuración

El archivo `application.yaml` contiene la configuración base:

```yaml
app:
  jwt:
    secret: ${JWT_SECRET}
    expiration: ${JWT_EXPIRATION_MS}
spring:
  application:
    name: backend
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
server:
  port: ${PORT:8080}
```

### Base de Datos

#### PostgreSQL (Producción)

```sql
-- Crear base de datos
CREATE DATABASE nomina;

-- Conectar a la base de datos
\c nomina

-- El esquema será creado automáticamente por Hibernate
-- Asegúrate de tener las migraciones o scripts necesarios
```

#### H2 (Pruebas)

Para pruebas locales, puedes usar H2 en memoria:

```bash
export DB_URL="jdbc:h2:mem:testdb"
export DB_USERNAME="sa"
export DB_PASSWORD=""
```

---

## API REST

### Autenticación

El backend utiliza JWT (JSON Web Tokens) para autenticación. Todos los endpoints excepto `/auth/register` y `/auth/login` requieren un token JWT válido.

#### Endpoints de Autenticación

**POST /auth/register** - Registrar un nuevo empleado

```json
Request Body:
{
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "rfc": "PEGJ900101ABC",
  "correo": "juan.perez@example.com",
  "esAdmin": false
}

Response: 200 OK
{
  "rfc": "PEGJ900101ABC",
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "correo": "juan.perez@example.com",
  "esAdmin": false
}
```

**POST /auth/login** - Iniciar sesión

```json
Request Body:
{
  "correo": "admin@example.com",
  "password": "Admin123456*"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresInMs": 86400000
}

Headers:
Set-Cookie: access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...; HttpOnly; Path=/; Max-Age=86400
```

### Endpoints de Empleados

**GET /empleado/** - Obtener todos los empleados (requiere autenticación)

```
Headers:
Cookie: access_token={token}

Response: 200 OK
[
  {
    "rfc": "PEGJ900101ABC",
    "nombre": "JUAN CARLOS",
    "apellidos": "PEREZ GARCIA",
    "correo": "juan.perez@example.com",
    "esAdmin": false
  },
  ...
]
```

**GET /empleado/{rfc}** - Obtener un empleado por RFC (requiere autenticación)

```
Headers:
Cookie: access_token={token}

Response: 200 OK
{
  "rfc": "PEGJ900101ABC",
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "correo": "juan.perez@example.com",
  "esAdmin": false
}
```

### Endpoints de Nóminas

**GET /nomina/?rfc={rfc}** - Obtener nóminas de un empleado (requiere autenticación)

```
Headers:
Cookie: access_token={token}

Response: 200 OK
[
  {
    "id": 1,
    "rfc": "PEGJ900101ABC",
    "fechaInicio": "2025-01-01",
    "fechaFin": "2025-01-15",
    "salarioBruto": 15000.00,
    "isr": 1500.00,
    "imss": 750.00,
    "salarioNeto": 12750.00
  },
  ...
]
```

**POST /nomina/** - Crear una nueva nómina (requiere autenticación)

```json
Headers:
Cookie: access_token={token}

Request Body:
{
  "rfc": "PEGJ900101ABC",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-01-15",
  "salario": 15000.00
}

Response: 200 OK
{
  "id": 1,
  "rfc": "PEGJ900101ABC",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-01-15",
  "salarioBruto": 15000.00,
  "isr": 1500.00,
  "imss": 750.00,
  "salarioNeto": 12750.00
}
```

**DELETE /nomina/{id}** - Eliminar una nómina (requiere autenticación)

```
Headers:
Cookie: access_token={token}

Response: 204 No Content
```

### Códigos de Estado HTTP

| Código | Descripción                                    |
|--------|------------------------------------------------|
| 200    | Operación exitosa                              |
| 201    | Recurso creado exitosamente                    |
| 204    | Operación exitosa sin contenido de respuesta   |
| 400    | Error en la solicitud (datos inválidos)        |
| 401    | No autenticado (token inválido o ausente)      |
| 403    | No autorizado (sin permisos)                   |
| 404    | Recurso no encontrado                          |
| 500    | Error interno del servidor                     |

---

## Autores

Este proyecto fue desarrollado para la clase de Ingeniería de Software II impartida en la Facultad de Ingeniería de la Universidad Autónoma del Estado de México por:

| Nombre corto (nombre + apellido) | Correo institucional            | Correo personal          |
|----------------------------------|---------------------------------|--------------------------|
| Fernando Espinosa                | jfespinosas001@alumno.uaemex.mx | jferespinosa18@gmail.com |

---

## Documentación Adicional

### Documentación para Usuarios
- **[MANUAL-DE-USUARIO.md](docs/MANUAL-DE-USUARIO.md)** - Manual completo para personal de Recursos Humanos
    - Guía paso a paso de todas las funcionalidades
    - Ejemplos prácticos y casos de uso
    - Solución de problemas comunes
    - Preguntas frecuentes
    - Glosario de términos

### Documentación Técnica
- **[Diagramas UML](docs/uml/README.md)** - 9 diagramas completos de arquitectura en PlantUML
    - **Diagramas de Estructura (6):**
        - Diagrama Entidad-Relación
        - Diagrama de Clases - Capa de Persistencia
        - Diagrama de Clases - Capa de Servicios
        - Diagrama de Clases - Capa de Lógica de Negocio
        - Diagrama de Clases - Capa de Presentación
        - Diagrama de Despliegue

### Documentación de Calidad
- **[RESUMEN-DE-PRUEBAS.md](docs/RESUMEN-DE-PRUEBAS.md)** - Documentación completa de pruebas y cobertura

### Documentación de Despliegue
- **[README-DOCKER.md](docs/README-DOCKER.md)** - Guía completa de despliegue con Docker

---

## Soporte

Para reportar problemas o solicitar nuevas características, contacta al equipo de desarrollo.

---

**Última actualización:** Diciembre 2025

