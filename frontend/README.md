# Frontend - Sistema de Cálculo de Nómina

Aplicación web frontend para el sistema de gestión y cálculo de nóminas de empleados.

## Tecnologías Utilizadas

- HTML5
- CSS3
- JavaScript (Vanilla)
- Bootstrap 5

## Estructura del Proyecto

```
src/
├── index.html              # Página de inicio
├── login.html              # Página de inicio de sesión
├── register.html           # Página de registro de empleados
├── dashboard.html          # Dashboard de administrador
├── calcular-nomina.html    # Página para calcular nóminas
├── consultar-nominas.html  # Página para consultar nóminas
├── css/
│   ├── bootstrap.min.css   # Framework Bootstrap
│   └── styles.css          # Estilos personalizados
└── js/
    ├── bootstrap.bundle.min.js  # JavaScript de Bootstrap
    ├── config.js                # Configuración de la API
    ├── auth.js                  # Utilidades de autenticación
    ├── register.js              # Lógica de registro
    ├── login.js                 # Lógica de inicio de sesión
    ├── dashboard.js             # Lógica del dashboard
    ├── calcular-nomina.js       # Lógica de cálculo de nómina
    └── consultar-nominas.js     # Lógica de consulta de nóminas
```

## Funcionalidades

### Rutas Públicas (No requieren autenticación)

1. **Registrar Empleado** (`register.html`)
   - Formulario para registrar nuevos empleados
   - Campos: RFC, Nombre(s), Apellido(s), Correo electrónico, Es administrador
   - Si es administrador, requiere contraseña
   - Validación de contraseñas

2. **Iniciar Sesión** (`login.html`)
   - Formulario de autenticación
   - Campos: Correo electrónico, Contraseña
   - Guarda el token JWT en cookies

### Rutas Protegidas (Requieren autenticación)

3. **Dashboard de Administrador** (`dashboard.html`)
   - Muestra lista de todos los empleados
   - Muestra el RFC del administrador conectado
   - Botones para calcular nómina y consultar nóminas por empleado
   - Botón de cerrar sesión

4. **Calcular Nómina** (`calcular-nomina.html`)
   - Formulario para calcular nómina de un empleado
   - Campos de solo lectura: RFC, Nombre y Apellidos
   - Campos editables: Salario bruto, Fecha inicio periodo, Fecha fin periodo
   - Muestra resultado del cálculo con desglose

5. **Consultar Nóminas** (`consultar-nominas.html`)
   - Lista todas las nóminas de un empleado
   - Muestra información completa de cada nómina
   - Botón para eliminar nóminas con confirmación

## Configuración

### Configurar la URL del Backend

Editar el archivo `src/js/config.js` para cambiar la URL del backend:

```javascript
const API_BASE_URL = 'http://localhost:8080';
```

## Ejecución

### Opción 1: Servidor Local Simple

Usando Python:
```bash
cd src
python -m http.server 8000
```

Usando Node.js:
```bash
cd src
npx http-server -p 8000
```

Luego abrir en el navegador: `http://localhost:8000`

### Opción 2: Con Docker

Ver el archivo principal README.md para instrucciones de Docker.

## Uso del Sistema

1. **Primer uso - Registrar un administrador:**
   - Ir a "Registrar Empleado"
   - Llenar todos los campos
   - Marcar "Es administrador"
   - Proporcionar contraseña
   - Hacer clic en "Registrar"

2. **Iniciar sesión:**
   - Ir a "Iniciar Sesión"
   - Ingresar correo y contraseña
   - Hacer clic en "Ingresar"

3. **Gestionar empleados:**
   - Desde el dashboard se puede ver todos los empleados
   - Usar los botones para calcular o consultar nóminas

4. **Calcular nómina:**
   - Desde el dashboard, hacer clic en "Calcular Nómina" del empleado deseado
   - Ingresar salario bruto y fechas del periodo
   - Hacer clic en "Calcular Nómina"
   - Ver el resultado con el desglose

5. **Consultar nóminas:**
   - Desde el dashboard, hacer clic en "Consultar Nóminas" del empleado deseado
   - Ver todas las nóminas del empleado
   - Eliminar nóminas si es necesario

## Autenticación

El sistema utiliza JWT (JSON Web Tokens) para la autenticación:
- El token se guarda en una cookie llamada `access_token`
- El token se envía automáticamente en las peticiones protegidas
- La sesión expira según la configuración del backend
- Al cerrar sesión, se elimina la cookie

## Características de Seguridad

- Protección de rutas mediante validación de token
- Redirección automática a login si no está autenticado
- Validación de formularios en el cliente
- Manejo de errores de autenticación (401, 403)
- Cierre de sesión seguro

## Características de UI/UX

- Diseño responsivo con Bootstrap
- Mensajes de alerta para feedback al usuario
- Spinners de carga durante peticiones
- Confirmación antes de eliminar nóminas
- Validación de formularios en tiempo real
- Navegación intuitiva

## Notas Adicionales

- Asegúrese de que el backend esté ejecutándose antes de usar el frontend
- Los campos de solo lectura están visualmente diferenciados
- Las fechas se validan para evitar periodos inválidos
- Los valores monetarios se formatean con separadores de miles
- El sistema está en español (es-MX)

