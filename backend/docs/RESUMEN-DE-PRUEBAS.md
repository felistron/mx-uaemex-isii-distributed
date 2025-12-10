# Resumen Técnico y Matriz de Pruebas Unitarias
## Backend - Sistema de Nómina UAEMex (API REST)

## Resumen Ejecutivo

### Estadísticas Generales

| Métrica                         | Valor                              |
|---------------------------------|------------------------------------|
| **Total de Archivos de Prueba** | 28                                 |
| **Total de Pruebas**            | 226                                |
| **Pruebas Pasando**             | 226 (100%)                         |
| **Pruebas Fallando**            | 0                                  |
| **Tiempo de Ejecución**         | 41.045 segundos                    |
| **Fecha de Última Ejecución**   | 9 de diciembre de 2025, 15:26 hrs  |

### Cobertura de Código JaCoCo

| Métrica                        | Objetivo | Alcanzado             | Estado          |
|--------------------------------|----------|-----------------------|-----------------|
| **Cobertura de Instrucciones** | ≥85%     | **99%** (1,345/1,350) | ✅ SUPERADO +16% |
| **Cobertura de Ramas**         | ≥75%     | **100%** (168/168)    | ✅ SUPERADO +33% |
| **Cobertura de Líneas**        | ≥85%     | **99%** (296/298)     | ✅ SUPERADO +16% |
| **Cobertura de Métodos**       | ≥90%     | **99%** (69/70)       | ✅ SUPERADO +10% |
| **Cobertura de Clases**        | ≥90%     | **100%** (29/29)      | ✅ SUPERADO +10% |

### Distribución por Capa

```
┌──────────────────────┬──────────┬─────────┬─────────────┬─────────────────┐
│ Capa                 │ Archivos │ Pruebas │ Cobertura   │ Estado          │
├──────────────────────┼──────────┼─────────┼─────────────┼─────────────────┤
│ PRESENTACIÓN         │   11     │   90    │ 100% ✅     │ Perfecto        │
│   Controladores      │    3     │   62    │ 100% ✅     │ Perfecto        │
│   DTOs               │    6     │   12    │ 100% ✅     │ Perfecto        │
│   Filtros            │    1     │   11    │ 100% ✅     │ Perfecto        │
│   Aplicación         │    1     │    1    │  37% ⚠️     │ Básico          │
├──────────────────────┼──────────┼─────────┼─────────────┼─────────────────┤
│ LÓGICA DE NEGOCIO    │   14     │  127    │ 100% ✅     │ Perfecto        │
│   Servicios          │    7     │   94    │ 100% ✅     │ Perfecto        │
│   Validadores        │    5     │   27    │ 100% ✅     │ Perfecto        │
│   Excepciones        │    2     │    6    │ 100% ✅     │ Perfecto        │
├──────────────────────┼──────────┼─────────┼─────────────┼─────────────────┤
│ PERSISTENCIA         │    3     │    9    │ 100% ✅     │ Perfecto        │
│   Modelos            │    3     │    9    │ 100% ✅     │ Perfecto        │
└──────────────────────┴──────────┴─────────┴─────────────┴─────────────────┘

TOTAL:                   28        226       99% ✅        EXCELENTE
```

### Organización de Pruebas por Arquitectura

El proyecto de pruebas refleja la misma arquitectura en capas del código de producción:

```
src/test/java/mx/uaemex/fi/
├── presentation/          # Pruebas de la capa de presentación
│   ├── controller/        # AdminControllerTest, AuthControllerTest
│   ├── dto/              # Tests de DTOs (RegisterRequest, LoginRequest, etc.)
│   └── filter/           # JwtAuthenticationFilterTest
│
├── logic/                # Pruebas de la capa de lógica de negocio
│   ├── service/          # Tests de servicios (Auth, Nomina, Empleado, JWT, etc.)
│   ├── validation/       # Tests de validadores personalizados
│   └── exception/        # Tests de excepciones del dominio
│
├── persistence/          # Pruebas de la capa de persistencia
│   └── model/            # Tests de entidades (Empleado, Nomina, Acceso)
│
└── util/                 # Utilidades para pruebas
```

**Beneficios de esta organización:**
- ✅ Fácil localización de pruebas por funcionalidad
- ✅ Separación clara de responsabilidades en las pruebas
- ✅ Facilita el mantenimiento y evolución del código de pruebas
- ✅ Refleja la arquitectura real del sistema

---

## Matriz Completa de Pruebas

### Matriz General por Archivo

| #         | Archivo de Prueba                | Categoría        | Pruebas | Estado   | Cobertura | Prioridad |
|-----------|----------------------------------|------------------|---------|----------|-----------|-----------|
| 1         | BackendApplicationTest           | Aplicación       | 1       | ✅ PASS   | 37%       | Baja      |
| 2         | AccesoTest                       | Modelo           | 3       | ✅ PASS   | 100%      | Media     |
| 3         | AuthControllerTest               | Controlador      | 42      | ✅ PASS   | 100%      | Crítica   |
| 4         | AuthServiceImpTest               | Servicio         | 9       | ✅ PASS   | 100%      | Crítica   |
| 5         | CalculoNominaService2025Test     | Servicio         | 56      | ✅ PASS   | 100%      | Crítica   |
| 6         | ConditionalPasswordValidatorTest | Validador        | 12      | ✅ PASS   | 100%      | Alta      |
| 7         | CustomUserDetailsServiceTest     | Servicio         | 4       | ✅ PASS   | 100%      | Alta      |
| 8         | EmpleadoControllerTest           | Controlador      | 5       | ✅ PASS   | 100%      | Crítica   |
| 9         | EmpleadoResponseTest             | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| 10        | EmpleadoServiceImpTest           | Servicio         | 4       | ✅ PASS   | 100%      | Alta      |
| 11        | EmpleadoTest                     | Modelo           | 3       | ✅ PASS   | 100%      | Media     |
| 12        | InvalidCredentialsExceptionTest  | Excepción        | 3       | ✅ PASS   | 100%      | Media     |
| 13        | JwtAuthenticationFilterTest      | Filtro           | 11      | ✅ PASS   | 100%      | Crítica   |
| 14        | JwtResponseTest                  | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| 15        | JwtServiceImpTest                | Servicio         | 11      | ✅ PASS   | 100%      | Crítica   |
| 16        | LoginRequestTest                 | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| 17        | NominaControllerTest             | Controlador      | 15      | ✅ PASS   | 100%      | Crítica   |
| 18        | NominaRequestTest                | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| 19        | NominaServiceImpTest             | Servicio         | 7       | ✅ PASS   | 100%      | Crítica   |
| 20        | NominaTest                       | Modelo           | 3       | ✅ PASS   | 100%      | Media     |
| 21        | NotFoundExceptionTest            | Excepción        | 3       | ✅ PASS   | 100%      | Media     |
| 22        | PeriodoValidatorTest             | Validador        | 5       | ✅ PASS   | 100%      | Alta      |
| 23        | RegisterRequestTest              | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| 24        | RFCExistsValidatorTest           | Validador        | 4       | ✅ PASS   | 100%      | Alta      |
| 25        | UniqueEmailValidatorTest         | Validador        | 4       | ✅ PASS   | 100%      | Alta      |
| 26        | UniqueRFCValidatorTest           | Validador        | 4       | ✅ PASS   | 100%      | Alta      |
| 27        | UserDetailsAdapterTest           | Servicio         | 7       | ✅ PASS   | 100%      | Alta      |
| 28        | NominaResponseTest               | DTO              | 2       | ✅ PASS   | 100%      | Media     |
| **TOTAL** | **28 archivos**                  | **8 categorías** | **226** | **100%** | **99%**   |           |

### Matriz por Funcionalidad

#### Seguridad y Autenticación (83 pruebas)

| Componente          | Archivo                          | Pruebas | Aspectos Probados                                       |
|---------------------|----------------------------------|---------|---------------------------------------------------------|
| Login/Register      | AuthControllerTest               | 42      | Validaciones, redirecciones, cookies, manejo de errores |
| Autenticación       | AuthServiceImpTest               | 9       | Login, registro, encriptación, roles                    |
| JWT Service         | JwtServiceImpTest                | 11      | Generación, validación, extracción, expiración          |
| JWT Filter          | JwtAuthenticationFilterTest      | 11      | Filtrado, autenticación, redirección, cookies           |
| UserDetails         | CustomUserDetailsServiceTest     | 4       | Carga de usuario, excepciones                           |
| UserDetails Adapter | UserDetailsAdapterTest           | 7       | Roles, credenciales, estados de cuenta                  |
| Password Validator  | ConditionalPasswordValidatorTest | 12      | Complejidad, confirmación, reglas admin                 |

**Subtotal**: 96 pruebas | Cobertura: 100%

#### Lógica de Negocio (82 pruebas)

| Componente           | Archivo                      | Pruebas | Aspectos Probados                                   |
|----------------------|------------------------------|---------|-----------------------------------------------------|
| Controlador Nómina   | NominaControllerTest         | 15      | CRUD nómina, validaciones, autorizaciones           |
| Cálculo ISR 2025     | CalculoNominaService2025Test | 56      | Tablas SAT, 11 rangos, porcentajes, excedentes      |
| Gestión Nómina       | NominaServiceImpTest         | 7       | Creación, eliminación, cálculos, relaciones         |
| Gestión Empleados    | EmpleadoServiceImpTest       | 4       | Búsqueda, listado, excepciones                      |

**Subtotal**: 82 pruebas | Cobertura: 100%

#### Validaciones (29 pruebas)

| Validador         | Archivo                          | Pruebas | Reglas de Validación                               |
|-------------------|----------------------------------|---------|----------------------------------------------------|
| RFC Único         | UniqueRFCValidatorTest           | 4       | Duplicados, null, blanco, integración BD           |
| Email Único       | UniqueEmailValidatorTest         | 4       | Duplicados, null, blanco, integración BD           |
| RFC Existe        | RFCExistsValidatorTest           | 4       | Existencia, null, validación                       |
| Periodo           | PeriodoValidatorTest             | 5       | Fechas inicio/fin, null, lógica temporal           |
| Password Complejo | ConditionalPasswordValidatorTest | 12      | Admin, mayúsculas, minúsculas, números, especiales |

**Subtotal**: 29 pruebas | Cobertura: 100%

#### Controladores y DTOs (30 pruebas)

| Tipo              | Archivos                              | Pruebas | Aspectos Probados                       |
|-------------------|---------------------------------------|---------|-----------------------------------------|
| Controladores API | 3 (Auth, Empleado, Nomina)            | 62      | Endpoints REST, validaciones, seguridad |
| DTOs Request      | 3 (Register, Login, Nomina)           | 6       | Creación, validaciones, records         |
| DTOs Response     | 3 (Jwt, Empleado, Nomina)             | 6       | Creación, mapeo, seguridad              |
| Modelos           | 3 (Empleado, Nomina, Acceso)          | 9       | Builder, relaciones, getters/setters    |
| Excepciones       | 2 (NotFound, InvalidCredentials)      | 6       | Construcción, mensajes, herencia        |
| Aplicación        | 1 (BackendApplication)                | 1       | Context loading, Spring Boot            |

**Subtotal**: 90 pruebas | Cobertura: 100% (controladores y DTOs), 100% (modelos)

---

## Detalle de Archivos de Prueba

### Controladores REST API (62 pruebas)

#### AuthControllerTest.java (42 pruebas)
**Ubicación**: `src/test/java/mx/uaemex/fi/backend/presentation/controller/AuthControllerTest.java`

**Pruebas Clave**:
- Registro exitoso de empleado (POST /auth/register)
- Validación de RFC formato y unicidad
- Validación de email formato y unicidad
- Validación de nombre y apellidos (mayúsculas)
- Validación de password para administradores (12+ caracteres, complejidad)
- Login exitoso retorna token JWT
- Login con credenciales inválidas retorna 400
- Manejo de excepciones InvalidCredentialsException
- Configuración de cookies HttpOnly y seguras
- Todos los campos obligatorios validados

**Cobertura**: 100% instrucciones

#### EmpleadoControllerTest.java (5 pruebas)
**Ubicación**: `src/test/java/mx/uaemex/fi/backend/presentation/controller/EmpleadoControllerTest.java`

**Pruebas Clave**:
- Listar todos los empleados (GET /empleado/) con autenticación
- Obtener empleado por RFC (GET /empleado/{rfc})
- Empleado no encontrado retorna 404
- Requiere autenticación JWT válida
- Manejo de excepción NotFoundException

**Cobertura**: 100% instrucciones

#### NominaControllerTest.java (15 pruebas)
**Ubicación**: `src/test/java/mx/uaemex/fi/backend/presentation/controller/NominaControllerTest.java`

**Pruebas Clave**:
- Crear nómina válida (POST /nomina/)
- Validación de RFC existente
- Validación de fechas (inicio < fin)
- Validación de salario bruto > 0.01
- Listar nóminas por RFC (GET /nomina/?rfc={rfc})
- Parámetro RFC requerido en query
- Eliminar nómina (DELETE /nomina/{id})
- Validaciones de campos obligatorios
- Requiere autenticación JWT

**Cobertura**: 100% instrucciones

### Servicios (94 pruebas)

#### AuthServiceImpTest.java (9 pruebas)
- Login con credenciales válidas retorna JWT
- Login con correo inexistente lanza excepción
- Login con password incorrecto lanza excepción
- Registro de empleado normal sin acceso
- Registro de administrador con acceso
- Password se encripta con BCrypt
- Guardado en repositorio funcional

**Cobertura**: 100%

#### CalculoNominaService2025Test.java (56 pruebas)
**Pruebas más críticas del sistema**

Incluye pruebas para:
- 11 rangos de cuota fija según tabla ISR 2025
- Cálculo de excedente para cada rango
- Porcentajes aplicables por rango (1.92% a 35%)
- Pruebas parametrizadas para valores límite
- Validación de tablas oficiales del SAT
- Casos extremos y bordes

**Cobertura**: 100%

#### EmpleadoServiceImpTest.java (4 pruebas)
- Buscar empleado por RFC existente
- Buscar empleado por RFC inexistente lanza NotFoundException
- Obtener todos los empleados
- Obtener lista vacía cuando no hay empleados

**Cobertura**: 100%

#### JwtServiceImpTest.java (11 pruebas)
- Generar token JWT válido
- Token contiene RFC como subject
- Token contiene fechas de emisión y expiración
- Validar token válido
- Validar token expirado retorna false
- Validar token malformado retorna false
- Extraer RFC del token
- Configuración de secreto y expiración

**Cobertura**: 100%

#### NominaServiceImpTest.java (7 pruebas)
- Generar nómina con empleado existente
- Generar nómina con empleado inexistente lanza excepción
- Calcular ISR e IMSS correctamente
- Obtener todas las nóminas por RFC
- Eliminar nómina existente
- Validar relaciones empleado-nómina

**Cobertura**: 100%

#### CustomUserDetailsServiceTest.java (4 pruebas)
- Cargar usuario por correo existente
- Lanzar excepción cuando usuario no existe
- Mapeo correcto de datos de empleado a UserDetails
- Integración con repositorio

**Cobertura**: 100%

#### UserDetailsAdapterTest.java (7 pruebas)
- Getters de username, password
- Obtener authorities (roles)
- Estados de cuenta (enabled, expired, locked)
- Mapeo desde Empleado y Acceso
- Casos con y sin acceso

**Cobertura**: 100%
- Uso correcto del servicio de cálculo
- Relación bidireccional empleado-nómina
- Persistencia en base de datos
- Eliminar nómina existente
- Obtener nómina por ID

**Cobertura**: 100%

#### CustomUserDetailsServiceTest.java (3 pruebas)
- Cargar usuario por RFC existente
- Cargar usuario por RFC inexistente lanza UsernameNotFoundException
- Retorna instancia de UserDetailsAdapter

**Cobertura**: 100%

#### UserDetailsAdapterTest.java (9 pruebas)
- Empleado sin acceso tiene ROLE_USER
- Empleado con acceso tiene ROLE_USER y ROLE_ADMIN
- Obtener password hasheado
- Username es el RFC
- Cuenta nunca expira
- Credenciales no expiran
- Cuenta siempre habilitada

**Cobertura**: 100%

### Validadores (27 pruebas)

#### ConditionalPasswordValidatorTest.java (11 pruebas)
- No valida si no es administrador
- Validar longitud mínima 12 caracteres
- Validar mayúsculas requeridas
- Validar minúsculas requeridas
- Validar números requeridos
- Validar caracteres especiales requeridos
- Validar confirmación de password
- Manejo de null y strings vacíos

**Cobertura**: 100%

#### PeriodoValidatorTest.java (5 pruebas)
- Fecha inicio antes de fecha fin es válida
- Fecha inicio después de fecha fin es inválida
- Fechas iguales son inválidas
- Manejo de null en fechas

**Cobertura**: 100%

#### RFCExistsValidatorTest.java (3 pruebas)
- RFC existente es válido
- RFC inexistente es inválido
- RFC null es inválido

**Cobertura**: 100%

#### UniqueEmailValidatorTest.java (4 pruebas)
- Email único es válido
- Email duplicado es inválido
- Manejo de null y blancos

**Cobertura**: 100%

#### UniqueRFCValidatorTest.java (4 pruebas)
- RFC único es válido
- RFC duplicado es inválido
- Manejo de null y blancos

**Cobertura**: 100%

### Filtros (11 pruebas)

#### JwtAuthenticationFilterTest.java (11 pruebas)
- No filtrar rutas /auth/*
- No filtrar recursos estáticos (CSS, JS, imágenes)
- Filtrar rutas protegidas (/admin/*)
- Token válido autentica usuario
- Sin token continúa sin autenticar
- Token inválido redirige a login
- Token expirado redirige a login
- Establecer contexto de seguridad
- Extraer JWT desde cookies
- Manejo de cookies ausentes o incorrectas

**Cobertura**: 82%

### DTO (10 pruebas)

#### RegisterRequestTest.java (2 pruebas)
- Record se crea correctamente con todos los campos
- Validación de campos opcionales

**Cobertura**: 100%

#### LoginRequestTest.java (2 pruebas)
- Record se crea con correo y password
- Campos permiten null

**Cobertura**: 100%

#### NominaRequestTest.java (2 pruebas)
- Record se crea con todos los campos de nómina
- Validaciones de datos

**Cobertura**: 100%

#### JwtResponseTest.java (2 pruebas)
- Record se crea con token y tipo
- Token type es "Bearer"

**Cobertura**: 100%

#### EmpleadoResponseTest.java (2 pruebas)
- Record se crea correctamente
- No expone información sensible (password)

**Cobertura**: 100%

### Modelos (15 pruebas)

#### EmpleadoTest.java (7 pruebas)
- Builder de Lombok funciona correctamente
- Getters y setters funcionan
- Relación OneToOne con Acceso
- Relación OneToMany con Nóminas
- Equals y hashCode
- ToString

**Estado**: ✅ Completo

#### NominaTest.java (5 pruebas)
- Builder funciona correctamente
- Getters y setters funcionan
- Relación ManyToOne con Empleado
- Almacenamiento de cálculos
- Manejo de fechas de periodo

**Estado**: ✅ Completo

#### AccesoTest.java (3 pruebas)
- Builder funciona con empleado y password
- Relación OneToOne con Empleado
- Almacenamiento seguro de password hasheado

**Estado**: ✅ Completo

### Excepciones (6 pruebas)

#### NotFoundExceptionTest.java (3 pruebas)
- Constructor con mensaje
- Mensaje se preserva
- Es RuntimeException

**Cobertura**: 100%

#### InvalidCredentialsExceptionTest.java (3 pruebas)
- Constructor con mensaje
- Mensaje se preserva
- Es RuntimeException

**Cobertura**: 100%

---

## Tecnologías y Herramientas Utilizadas

### Framework de Pruebas
- **JUnit 5 (Jupiter)** - Framework principal de pruebas unitarias
- **Mockito 5.x** - Mocking y stubbing de dependencias
- **Mockito con Byte Buddy** - Inline mock maker

### Spring Testing
- **Spring Boot Test 3.5.6** - Integración con Spring Boot
- **Spring Security Test** - Pruebas de seguridad y autorización
- **MockMvc** - Pruebas de controladores web

### Herramientas de Cobertura
- **JaCoCo 0.8.11** - Análisis de cobertura de código
- **Maven Surefire 3.2.5** - Ejecución de pruebas y reportes

### Build Tools
- **Maven 3.x** - Gestión de dependencias y build
- **Java 21.0.5** - Versión de Java utilizada

---

## Comandos Útiles para Desarrolladores

### Ejecutar todas las pruebas
```bash
mvn test
```

### Generar reporte de cobertura JaCoCo
```bash
mvn jacoco:report
```

### Ver reporte HTML de cobertura
El reporte se genera en: `target/site/jacoco/index.html`

### Ejecutar pruebas de un archivo específico
```bash
mvn test -Dtest=AdminControllerTest
mvn test -Dtest=CalculoNominaService2025Test
```

### Ejecutar una prueba específica
```bash
mvn test -Dtest=AdminControllerTest#dashboardMuestraListaDeEmpleadosConAuth
```

### Ejecutar pruebas con logs detallados
```bash
mvn test -X
```

### Limpiar y ejecutar pruebas
```bash
mvn clean test
```

### Generar reporte completo Surefire
```bash
mvn surefire-report:report
```
Reporte en: `target/site/surefire-report.html`

### Ejecutar solo pruebas rápidas (< 1 segundo)
```bash
mvn test -Dgroups=unit
```
