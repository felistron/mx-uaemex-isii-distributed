# Diagramas UML del Sistema de Nómina UAEMex

Este directorio contiene los diagramas UML en formato PlantUML que documentan la arquitectura del sistema.

## Índice de Diagramas

### 1. Diagrama Entidad-Relación
**Archivo:** `01-diagrama-entidad-relacion.puml`

Muestra el modelo de datos del sistema con las tres entidades principales:
- **Empleado**: Información del empleado (RFC, nombre, apellidos, correo)
- **Acceso**: Credenciales de acceso para empleados con rol administrativo
- **Nomina**: Registros de nómina con cálculos ISR 2025

**Relaciones:**
- Empleado 1:1 Acceso (opcional)
- Empleado 1:N Nomina

---

### 2. Diagrama de Clases - Capa de Persistencia
**Archivo:** `02-diagrama-clases-persistencia.puml`

Detalla la capa de persistencia del sistema:
- **Entidades JPA**: Empleado, Acceso, Nomina (con anotaciones)
- **Repositorios**: EmpleadoRepository, NominaRepository
- **Patrón Repository** con Spring Data JPA
- Relaciones ORM (OneToOne, OneToMany, ManyToOne)

---

### 3. Diagrama de Clases - Capa de Servicios
**Archivo:** `03-diagrama-clases-servicios.puml`

Muestra la capa de servicios (Service Layer):
- **Interfaces de Servicio**: AuthService, EmpleadoService, NominaService, JwtService, CalculoNominaService
- **Implementaciones**: AuthServiceImp, EmpleadoServiceImp, NominaServiceImp, JwtServiceImp, CalculoNominaService2025
- **Servicios de Seguridad**: CustomUserDetailsService, UserDetailsAdapter
- **Dependencias** entre servicios y con repositorios
- **DTOs** utilizados en la comunicación

---

### 4. Diagrama de Clases - Capa de Lógica de Negocio
**Archivo:** `04-diagrama-clases-logica-negocio.puml`

Documenta la lógica de negocio y validaciones:
- **Anotaciones de Validación Personalizadas**: @ConditionalPassword, @Periodo, @RFCExists, @UniqueEmail, @UniqueRFC
- **Validadores**: ConditionalPasswordValidator, PeriodoValidator, RFCExistsValidator, UniqueEmailValidator, UniqueRFCValidator
- **Excepciones**: InvalidCredentialsException, NotFoundException
- **Bean Validation** con Jakarta Validation
- **Reglas de Negocio** implementadas

---

### 5. Diagrama de Clases - Capa de Presentación
**Archivo:** `05-diagrama-clases-presentacion.puml`

Detalla la capa de presentación (Controllers + DTOs + Filters):
- **Controladores**: AuthController, AdminController
- **DTOs (Records)**: RegisterRequest, LoginRequest, NominaRequest, JwtResponse, EmpleadoResponse
- **Filtros**: JwtAuthenticationFilter
- **Patrón MVC** con Spring MVC
- **Endpoints** REST y vistas Thymeleaf
- **Validaciones** de formularios

---

### 6. Diagrama de Despliegue
**Archivo:** `06-diagrama-despliegue.puml`

Muestra la arquitectura de despliegue del sistema:
- **Nodos de infraestructura**: Cliente (navegador), Servidor de aplicaciones, Base de datos
- **Componentes de software**: Spring Boot, Tomcat, JVM, H2 Database
- **Protocolos de comunicación**: HTTP/HTTPS, JDBC
- **Configuración de despliegue**: Docker (opcional), variables de entorno
- **Especificaciones de hardware**: CPU, RAM, Disco
- **Puertos**: 8080 (aplicación), H2 Console

---

## Herramientas para Visualizar los Diagramas

### Opción 1: PlantUML Online Server
1. Visita: https://www.plantuml.com/plantuml/uml/
2. Copia el contenido del archivo `.puml`
3. Pégalo en el editor
4. El diagrama se generará automáticamente

### Opción 2: Visual Studio Code + Extensión PlantUML
1. Instala la extensión "PlantUML" de jebbs
2. Abre el archivo `.puml`
3. Presiona `Alt + D` para previsualizar
4. Click derecho > "Export Current Diagram" para exportar como PNG/SVG

### Opción 3: IntelliJ IDEA + Plugin PlantUML
1. Instala el plugin "PlantUML Integration"
2. Abre el archivo `.puml`
3. El diagrama se renderizará automáticamente en el panel lateral
4. Click derecho > "Export to..." para exportar

### Opción 4: Línea de Comandos
```bash
# Instalar PlantUML (requiere Java)
# En Windows con Chocolatey:
choco install plantuml

# En Linux/Mac con apt/brew:
sudo apt install plantuml
# o
brew install plantuml

# Generar imagen PNG
plantuml 01-diagrama-entidad-relacion.puml

# Generar imagen SVG
plantuml -tsvg 01-diagrama-entidad-relacion.puml

# Generar todos los diagramas
plantuml *.puml
```

---

## Exportar Diagramas a Imágenes

### Desde VS Code
1. Abrir archivo `.puml`
2. `Ctrl + Shift + P` > "PlantUML: Export Current Diagram"
3. Seleccionar formato (PNG, SVG, PDF)

### Desde IntelliJ IDEA
1. Click derecho en el archivo `.puml`
2. "PlantUML" > "Export to PNG/SVG"

---

## Convenciones de los Diagramas

### Estereotipos Utilizados
- `<<Entity>>`: Entidad JPA del dominio
- `<<Repository>>`: Repositorio Spring Data
- `<<Service>>`: Interfaz de servicio
- `<<ServiceImpl>>`: Implementación de servicio
- `<<Controller>>`: Controlador Spring MVC
- `<<Filter>>`: Filtro Servlet
- `<<DTO>>`: Data Transfer Object
- `<<Record>>`: Java Record (inmutable)
- `<<Validator>>`: Validador Bean Validation
- `<<Exception>>`: Excepción personalizada

### Tipos de Relaciones
- `--|>`: Herencia/Implementación
- `-->`: Dependencia/Uso
- `..>`: Dependencia temporal
- `o--`: Composición
- `*--`: Agregación
- `--`: Asociación

### Multiplicidades
- `1`: Exactamente uno
- `0..1`: Cero o uno (opcional)
- `0..*`: Cero o muchos
- `1..*`: Uno o muchos
- `*`: Muchos

---

## Referencias

- **PlantUML**: https://plantuml.com/
- **Documentación PlantUML**: https://plantuml.com/guide
- **Galería de Ejemplos**: https://real-world-plantuml.com/
- **Referencia de Diagramas de Clases**: https://plantuml.com/class-diagram

---

## Información

**Proyecto**: Sistema de Nómina UAEMex  
**Versión**: 1.0-SNAPSHOT  
**Fecha de Creación**: 5 de diciembre de 2025  
**Formato**: PlantUML (.puml)  
**Total de Diagramas**: 6  

**Categorías de Diagramas:**
- Diagramas de Estructura: 6 (Entidad-Relación, Clases x4, Despliegue)

---

**Última actualización**: 5 de diciembre de 2025

