# 📘 Manual de Usuario - API Backend
## Sistema de Gestión de Nómina UAEMex

---

### Bienvenido

Este manual describe la **API REST del Backend** del Sistema de Gestión de Nómina de la Universidad Autónoma del Estado de México. Esta API proporciona servicios para gestionar empleados, calcular nóminas y manejar la autenticación del sistema.

**💡 Nota Importante:** Este es el módulo backend (API REST) del sistema. Para usuarios finales, se recomienda usar la **interfaz web (frontend)** que consume esta API de manera amigable. Consulte la [documentación del frontend](../../frontend/README.md) para más información.

**¿Para quién es este manual?**
- ✅ Desarrolladores que desean integrar la API en otras aplicaciones
- ✅ Administradores de sistemas que necesitan hacer peticiones directas
- ✅ Personal técnico que requiere entender el funcionamiento de la API

**¿Qué puedo hacer con esta API?**
- ✅ Registrar nuevos empleados
- ✅ Autenticar usuarios (login/logout)
- ✅ Consultar la lista de empleados
- ✅ Calcular nóminas automáticamente
- ✅ Ver el historial de nóminas de cada empleado
- ✅ Eliminar nóminas si es necesario

---

## Índice

1. [Requisitos Previos](#requisitos-previos)
2. [Autenticación](#autenticación)
3. [Endpoints Disponibles](#endpoints-disponibles)
4. [Ejemplos de Uso](#ejemplos-de-uso)
5. [Códigos de Respuesta HTTP](#códigos-de-respuesta-http)
6. [Preguntas Frecuentes](#preguntas-frecuentes)
7. [Solución de Problemas](#solución-de-problemas)
8. [Glosario de Términos](#glosario-de-términos)

---

## 1. Requisitos Previos

### ¿Qué necesito para usar la API?

**Requisitos básicos:**
- ✅ Conocimientos básicos de APIs REST
- ✅ Herramienta para hacer peticiones HTTP (Postman, cURL, Insomnia, etc.)
- ✅ La URL base de la API (ej: `http://localhost:3000`)
- ✅ Token JWT válido (para endpoints protegidos)

**Herramientas recomendadas:**
- ✅ [Postman](https://www.postman.com/) - Cliente de API con interfaz gráfica
- ✅ [cURL](https://curl.se/) - Cliente de línea de comandos
- ✅ [Insomnia](https://insomnia.rest/) - Cliente de API alternativo

---

## 2. Autenticación

La API utiliza **JWT (JSON Web Tokens)** para autenticación. 

### Flujo de Autenticación

1. **Registrar un empleado** (si aún no existe) → `POST /auth/register`
2. **Iniciar sesión** para obtener el token JWT → `POST /auth/login`
3. **Usar el token** en los headers de las peticiones protegidas

### Formato del Token

El token debe enviarse en una cookie `access_token` con el valor:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 3. Endpoints Disponibles

### Endpoints Públicos (No requieren autenticación)

#### POST /auth/register - Registrar un nuevo empleado

**URL:** `http://localhost:3000/auth/register`

**Método:** `POST`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "rfc": "PEGJ900101ABC",
  "correo": "juan.perez@example.com",
  "esAdmin": false
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "rfc": "PEGJ900101ABC",
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "correo": "juan.perez@example.com"
}
```

---

#### POST /auth/login - Iniciar sesión

**URL:** `http://localhost:3000/auth/login`

**Método:** `POST`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "correo": "admin@example.com",
  "password": "Admin123456*"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTYzOTg5NzIwMCwiZXhwIjoxNjM5OTgzNjAwfQ.abcd1234...",
  "type": "Bearer",
  "expiresInMs": 86400000
}
```

⚠️ **Importante:** Guarde el token JWT, lo necesitará para las siguientes peticiones.

---

### Endpoints Protegidos (Requieren autenticación)

#### GET /empleado/ - Obtener todos los empleados

**URL:** `http://localhost:3000/empleado/`

**Método:** `GET`

**Headers:**
```
Cookie: access_token={tu-token-jwt}
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "rfc": "PEGJ900101ABC",
    "nombre": "JUAN CARLOS",
    "apellidos": "PEREZ GARCIA",
    "correo": "juan.perez@example.com",
    "esAdmin": false
  },
  {
    "rfc": "LOMA850515XYZ",
    "nombre": "MARIA",
    "apellidos": "LOPEZ MARTINEZ",
    "correo": "maria.lopez@example.com",
    "esAdmin": true
  }
]
```

---

#### GET /empleado/{rfc} - Obtener un empleado por RFC

**URL:** `http://localhost:3000/empleado/PEGJ900101ABC`

**Método:** `GET`

**Headers:**
```
Cookie: access_token={tu-token-jwt}
```

**Respuesta exitosa (200 OK):**
```json
{
  "rfc": "PEGJ900101ABC",
  "nombre": "JUAN CARLOS",
  "apellidos": "PEREZ GARCIA",
  "correo": "juan.perez@example.com",
  "esAdmin": false
}
```

**Respuesta error (404 Not Found):**
```
Empleado no encontrado
```

---

#### GET /nomina/?rfc={rfc} - Obtener nóminas de un empleado

**URL:** `http://localhost:3000/nomina/?rfc=PEGJ900101ABC`

**Método:** `GET`

**Headers:**
```
Cookie: access_token={tu-token-jwt}
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "salario": 15000.00,
    "excedente": 3000.00,
    "cuotaFija": 500.00,
    "porcentaje": 0.14,
    "fechaInicio": "2025-01-01",
    "fechaFin": "2025-01-15"
  },
  {
    "id": 2,
    "salario": 15000.00,
    "excedente": 3000.00,
    "cuotaFija": 500.00,
    "porcentaje": 0.14,
    "fechaInicio": "2025-01-15",
    "fechaFin": "2025-01-31"
  }
]
```

---

#### POST /nomina/ - Crear una nueva nómina

**URL:** `http://localhost:3000/nomina/`

**Método:** `POST`

**Headers:**
```
Cookie: access_token={tu-token-jwt}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "rfc": "PEGJ900101ABC",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-01-15",
  "salario": 15000.00
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "salario": 15000.00,
  "excedente": 3000.00,
  "cuotaFija": 500.00,
  "porcentaje": 0.14,
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-01-15"
}
```

---

#### DELETE /nomina/{id} - Eliminar una nómina

**URL:** `http://localhost:3000/nomina/1`

**Método:** `DELETE`

**Headers:**
```
Cookie: access_token={tu-token-jwt}
```

**Respuesta exitosa (204 No Content):**
```
(Sin contenido en el body)
```

---

## 4. Ejemplos de Uso

### Ejemplo con cURL

#### Registrar un empleado

```bash
curl -X POST http://localhost:3000/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "JUAN CARLOS",
    "apellidos": "PEREZ GARCIA",
    "rfc": "PEGJ900101ABC",
    "correo": "juan.perez@example.com",
    "esAdmin": false
  }'
```

#### Iniciar sesión

```bash
curl -X POST http://localhost:3000/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@example.com",
    "password": "Admin123456*"
  }'
```

#### Obtener todos los empleados (con token)

```bash
curl -X GET http://localhost:3000/empleado/ \
  -H "Cookie: access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### Crear una nómina

```bash
curl -X POST http://localhost:3000/nomina/ \
  -H "Cookie: access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "rfc": "PEGJ900101ABC",
    "fechaInicio": "2025-01-01",
    "fechaFin": "2025-01-15",
    "salario": 15000.00
  }'
```

---

## 5. Códigos de Respuesta HTTP

| Código | Significado                      | Descripción                                        |
|--------|----------------------------------|----------------------------------------------------|
| 200    | OK                               | La petición fue exitosa                            |
| 201    | Created                          | El recurso fue creado exitosamente                 |
| 204    | No Content                       | Operación exitosa sin contenido de respuesta       |
| 400    | Bad Request                      | Datos inválidos en la petición                     |
| 401    | Unauthorized                     | Token JWT inválido o ausente                       |
| 403    | Forbidden                        | No tiene permisos para acceder al recurso          |
| 404    | Not Found                        | Recurso no encontrado                              |
| 500    | Internal Server Error            | Error interno del servidor                         |

---

## 6. Preguntas Frecuentes

### ¿Qué es un token JWT y para qué sirve?

JWT (JSON Web Token) es un estándar de seguridad que permite autenticar usuarios de forma segura. Cuando inicias sesión, recibes un token que debes incluir en cada petición a endpoints protegidos. El token expira después de 24 horas (configurable).

### ¿Qué hago si mi token JWT expiró?

Simplemente vuelve a hacer login (`POST /auth/login`) para obtener un nuevo token.

### ¿Puedo usar la API desde mi aplicación web o móvil?

Sí, esta API REST está diseñada para ser consumida por cualquier cliente HTTP: aplicaciones web (React, Angular, Vue), móviles (iOS, Android), o cualquier otro servicio.

### ¿Cómo calculo el ISR e IMSS de una nómina?

El cálculo se realiza automáticamente cuando creas una nómina mediante `POST /nomina/`. Solo necesitas proporcionar el RFC, las fechas y el salario bruto.

### ¿Puedo probar la API sin instalarla?

Sí, si el equipo de desarrollo ha desplegado una instancia de prueba. Consulta con el administrador del sistema para obtener la URL.

### ¿La API soporta CORS?

El CORS está deshabilitado por defecto. Si necesitas acceder desde un navegador web con un dominio diferente, contacta al administrador para habilitar CORS.

---

## 7. Solución de Problemas

### Error 401 - Unauthorized

**Problema:** Recibes un error 401 al hacer una petición.

**Solución:**
1. Verifica que estés enviando la cookie JWT en el header `Cookie: access_token={tu-token-jwt}`
2. Verifica que el token no haya expirado (duración: 24 horas)
3. Si expiró, haz login nuevamente

### Error 400 - Bad Request

**Problema:** La petición es rechazada con error 400.

**Solución:**
1. Verifica que el formato JSON sea correcto
2. Revisa que todos los campos obligatorios estén presentes
3. Verifica que los datos cumplan con las validaciones:
   - RFC: 13 caracteres alfanuméricos en mayúsculas
   - Nombre/Apellidos: Solo letras mayúsculas
   - Correo: Formato de email válido
   - Fechas: Formato YYYY-MM-DD

### Error 404 - Not Found

**Problema:** El recurso no existe.

**Solución:**
1. Verifica que la URL sea correcta
2. Si buscas un empleado por RFC, verifica que el RFC esté registrado
3. Si buscas una nómina por ID, verifica que el ID exista

### Error 500 - Internal Server Error

**Problema:** Error interno del servidor.

**Solución:**
1. Verifica los logs del servidor
2. Contacta al administrador del sistema
3. Reporta el error con los detalles de la petición

### No puedo conectarme a la API

**Solución:**
1. Verifica que el servidor esté ejecutándose
2. Comprueba que la URL y el puerto sean correctos (default: 8080)
3. Verifica que no haya un firewall bloqueando la conexión

---

## 8. Glosario de Términos

| Término      | Definición                                                                       |
|--------------|----------------------------------------------------------------------------------|
| **API**      | Application Programming Interface - Interfaz para que aplicaciones se comuniquen |
| **REST**     | Representational State Transfer - Estilo de arquitectura para APIs               |
| **JWT**      | JSON Web Token - Token de seguridad para autenticación                           |
| **RFC**      | Registro Federal de Contribuyentes - Identificador fiscal único                  |
| **ISR**      | Impuesto Sobre la Renta - Impuesto que se descuenta del salario                  |
| **IMSS**     | Instituto Mexicano del Seguro Social - Seguro social mexicano                    |
| **Endpoint** | URL específica de la API que realiza una función                                 |
| **Header**   | Información adicional que se envía en una petición HTTP                          |
| **Body**     | Contenido principal de una petición HTTP (generalmente JSON)                     |
| **JSON**     | JavaScript Object Notation - Formato estándar para intercambio de datos          |
| **Token**    | Cadena de caracteres que identifica y autentica a un usuario                     |

---

## Información del Documento

**Nombre del Sistema:** Sistema de Gestión de Nómina  
**Versión del Manual:** 1.0  
**Fecha de Creación:** 09 de diciembre de 2025  
**Última Actualización:** 09 de diciembre de 2025  
**Elaborado por:** Área de Desarrollo de Software  
**Dirigido a:** Personal de Recursos Humanos

---

## Control de Versiones

| Versión | Fecha      | Cambios Realizados              | Autor              |
|---------|------------|---------------------------------|--------------------|
| 1.0     | 09/12/2025 | Creación inicial del manual     | Área de Desarrollo |
| 2.0     | 09/12/2025 | Se cambió el puerto del backend | Área de Desarrollo |

---

## Anexos

### Anexo A: Tabla de Rangos Salariales ISR 2025

El sistema utiliza 11 rangos salariales según las tablas del SAT:

| Rango | Desde       | Hasta       | Cuota Fija  | % sobre Excedente |
|-------|-------------|-------------|-------------|-------------------|
| 1     | $0.01       | $746.04     | $0.00       | 1.92%             |
| 2     | $746.05     | $6,332.05   | $14.32      | 6.40%             |
| 3     | $6,332.06   | $11,128.01  | $371.83     | 10.88%            |
| 4     | $11,128.02  | $12,935.82  | $893.63     | 16.00%            |
| 5     | $12,935.83  | $15,487.71  | $1,182.88   | 17.92%            |
| 6     | $15,487.72  | $31,236.49  | $1,640.18   | 21.36%            |
| 7     | $31,236.50  | $49,233.00  | $5,004.12   | 23.52%            |
| 8     | $49,233.01  | $93,993.90  | $9,236.89   | 30.00%            |
| 9     | $93,993.91  | $125,325.20 | $22,665.17  | 32.00%            |
| 10    | $125,325.21 | $375,975.61 | $32,691.18  | 34.00%            |
| 11    | $375,975.62 | En adelante | $117,912.32 | 35.00%            |

**Nota:** Estos rangos son utilizados automáticamente por el sistema. No es necesario memorizarlos.

### Anexo B: Ejemplos de RFC Válidos

**Formato:** LLLL + AAMMDD + XXX

**Ejemplos correctos:**
- `CABA800101ABC` - 4 letras + fecha + 3 alfanuméricos
- `MEPR850615XYZ` - Todo en mayúsculas
- `LOGA900320DEF` - Sin espacios
- `RODR750825123` - Puede terminar en números

**Ejemplos incorrectos:**
- `caba800101abc` - ❌ En minúsculas
- `CABA 800101 ABC` - ❌ Con espacios
- `CABA800101` - ❌ Incompleto (faltan 3 caracteres)
- `CABAA800101ABC` - ❌ Demasiadas letras

---

**FIN DEL MANUAL DE USUARIO**

💡 **¿Necesita más información?** Contacte al área de TI

