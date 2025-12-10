# Guía de Despliegue con Docker - Backend

## Descripción

Esta guía describe cómo construir y ejecutar el módulo backend (API REST) del sistema de nómina utilizando Docker. El backend forma parte de una arquitectura distribuida y se puede desplegar de forma independiente o junto con el frontend usando Docker Compose.

---

## Construcción y Ejecución Individual

### 1. Construir la imagen Docker

Desde el directorio `backend/`:

```bash
docker build -t backend-nomina:latest .
```

### 2. Ejecutar el contenedor

#### Opción A: Con PostgreSQL externo

```bash
docker run -d -p 3000:3000 \
  -e PORT=3000 \
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits \
  -e JWT_EXPIRATION_MS=86400000 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/nomina \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=tu-password \
  --name backend-nomina-app \
  backend-nomina:latest
```

#### Opción B: Con H2 en memoria (solo para pruebas)

```bash
docker run -d -p 3000:3000 \
  -e PORT=3000 \
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits \
  -e JWT_EXPIRATION_MS=86400000 \
  -e DB_URL=jdbc:h2:mem:testdb \
  -e DB_USERNAME=sa \
  -e DB_PASSWORD= \
  --name backend-nomina-app \
  backend-nomina:latest
```

#### Opción C: Usando archivo .env

1. Crear archivo `.env` en el directorio backend:

```env
PORT=3000
JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits
JWT_EXPIRATION_MS=86400000
DB_URL=jdbc:postgresql://host.docker.internal:5432/nomina
DB_USERNAME=postgres
DB_PASSWORD=tu-password
```

2. Ejecutar el contenedor:

```bash
docker run -d -p 3000:3000 \
  --env-file .env \
  --name backend-nomina-app \
  backend-nomina:latest
```

### 3. Verificar que el contenedor está corriendo

```bash
docker ps
```

Deberías ver algo como:

```
CONTAINER ID   IMAGE                    COMMAND               CREATED         STATUS         PORTS                    NAMES
abc123def456   backend-nomina:latest    "java -jar app.jar"   10 seconds ago  Up 9 seconds   0.0.0.0:3000->3000/tcp   backend-nomina-app
```

### 4. Ver los logs de la aplicación

```bash
docker logs backend-nomina-app

# Para seguir los logs en tiempo real
docker logs -f backend-nomina-app
```

### 5. Probar la API

La API REST estará disponible en: `http://localhost:3000`

Prueba con:

```bash
# intenta registrar un usuario
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

### 6. Detener y eliminar el contenedor

```bash
# Detener
docker stop backend-nomina-app

# Eliminar
docker rm backend-nomina-app

# Detener y eliminar en un solo comando
docker rm -f backend-nomina-app
```

---

## Despliegue con Docker Compose (Recomendado)

Para desplegar el sistema completo (backend + frontend) junto con una base de datos PostgreSQL, usa Docker Compose desde el directorio raíz del proyecto.

### 1. Crear archivo .env en la raíz del proyecto

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

# PostgreSQL
POSTGRES_DB=nomina
POSTGRES_USER=postgres
POSTGRES_PASSWORD=tu-password-seguro
```

### 2. Iniciar todos los servicios

Desde el directorio raíz del proyecto:

```bash
docker-compose up -d
```

### 3. Ver estado de los servicios

```bash
docker-compose ps
```

Deberías ver los servicios backend y frontend corriendo.

### 4. Acceder a los servicios

- **Backend API:** http://localhost:3000
- **Frontend:** http://localhost:3001

### 5. Ver logs

```bash
# Logs de todos los servicios
docker-compose logs

# Solo del backend
docker-compose logs backend

# Solo del frontend
docker-compose logs frontend

# Seguir logs en tiempo real
docker-compose logs -f
```

### 6. Detener los servicios

```bash
# Detener sin eliminar
docker-compose stop

# Detener y eliminar contenedores
docker-compose down

# Detener, eliminar contenedores y volúmenes
docker-compose down -v
```

---

## Comandos PowerShell (Windows)

### Construcción

```powershell
docker build -t backend-nomina:latest .
```

### Ejecución con variables en línea (PowerShell)

```powershell
docker run -d -p 3000:3000 `
  -e PORT=3000 `
  -e JWT_SECRET=tu-secreto-jwt-super-seguro-de-al-menos-256-bits `
  -e JWT_EXPIRATION_MS=86400000 `
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/nomina `
  -e DB_USERNAME=postgres `
  -e DB_PASSWORD=tu-password `
  --name backend-nomina-app `
  backend-nomina:latest
```

### Ejecución con archivo .env (PowerShell)

```powershell
docker run -d -p 3000:3000 `
  --env-file .env `
  --name backend-nomina-app `
  backend-nomina:latest
```

### Docker Compose (PowerShell)

```powershell
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f backend

# Detener servicios
docker-compose down
```

---

## Dockerfile Multi-etapa

El proyecto utiliza un Dockerfile multi-etapa para optimizar el tamaño de la imagen:

```dockerfile
# Etapa 1: Construcción
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Ventajas:**
- ✅ Imagen final más ligera (solo contiene el JRE y el JAR)
- ✅ No incluye código fuente ni dependencias de desarrollo
- ✅ Mejor seguridad al reducir la superficie de ataque
- ✅ Construcción automática del proyecto durante el build

---

## Variables de Entorno Requeridas

| Variable              | Descripción                       | Ejemplo                                  | Requerido |
|-----------------------|-----------------------------------|------------------------------------------|-----------|
| `PORT`                | Puerto donde escucha el backend   | `3000`                                   | No (8080) |
| `JWT_SECRET`          | Secreto para firma de JWT         | `tu-secreto-seguro-de-256-bits`          | **Sí**    |
| `JWT_EXPIRATION_MS`   | Tiempo de expiración del JWT (ms) | `86400000` (24 horas)                    | **Sí**    |
| `DB_URL`              | URL de conexión a base de datos   | `jdbc:postgresql://postgres:5432/nomina` | **Sí**    |
| `DB_USERNAME`         | Usuario de base de datos          | `postgres`                               | **Sí**    |
| `DB_PASSWORD`         | Contraseña de base de datos       | `tu-password`                            | **Sí**    |

---

## Solución de Problemas

### El contenedor no inicia

```bash
# Ver logs detallados
docker logs backend-nomina-app

# Verificar que las variables de entorno estén configuradas
docker inspect backend-nomina-app | grep -A 20 Env
```

### No se puede conectar a la base de datos

- Si usas `localhost` en `DB_URL`, cámbialo a `host.docker.internal` (Windows/Mac) o la IP del host
- Verifica que PostgreSQL esté ejecutándose y acepte conexiones
- Revisa el firewall y puertos abiertos

### Error de memoria

```bash
# Aumentar memoria del contenedor
docker run -d -p 3000:3000 \
  -m 1g \
  --env-file .env \
  --name backend-nomina-app \
  backend-nomina:latest
```

### Puerto 8080 ya en uso

```bash
# Usa un puerto diferente en el host
docker run -d -p 9090:3000 \
  --env-file .env \
  --name backend-nomina-app \
  backend-nomina:latest

# La API estará en http://localhost:9090
```

---

## Notas Importantes

- 🔒 **Seguridad**: Nunca uses las credenciales de ejemplo en producción. Genera secrets seguros.
- 💾 **Persistencia**: Si usas H2 en memoria, los datos se perderán al detener el contenedor. Usa PostgreSQL para producción.
- 🌐 **Red**: En Docker Compose, los servicios se comunican por nombre (ej: `postgres` en lugar de `localhost`).
- 📊 **Monitoreo**: Considera agregar herramientas de monitoreo como Prometheus/Grafana para producción.
- 🔄 **Actualizaciones**: Para actualizar el backend, reconstruye la imagen y reinicia el contenedor.

---

## Recursos Adicionales

- [Documentación oficial de Docker](https://docs.docker.com/)
- [Guía de Docker Compose](https://docs.docker.com/compose/)
- [Best practices para Dockerfiles](https://docs.docker.com/develop/dev-best-practices/)

---

**Última actualización:** Diciembre 2025

