// Lógica para el dashboard de administrador

document.addEventListener('DOMContentLoaded', function() {
    // Proteger la página
    if (!protectPage()) return;

    const logoutBtn = document.getElementById('logoutBtn');
    const adminRFCElement = document.getElementById('adminRFC');
    const loadingSpinner = document.getElementById('loadingSpinner');
    const empleadosContainer = document.getElementById('empleadosContainer');
    const empleadosTable = document.getElementById('empleadosTable');
    const alertContainer = document.getElementById('alertContainer');

    // Obtener información del usuario
    const userInfo = getUserInfo();
    if (userInfo && userInfo.sub) {
        adminRFCElement.textContent = userInfo.sub; // El RFC está en el 'sub' del JWT
    }

    // Manejar el cierre de sesión
    logoutBtn.addEventListener('click', function() {
        logout();
    });

    // Cargar la lista de empleados
    loadEmpleados();

    async function loadEmpleados() {
        try {
            const response = await fetch(API_ENDPOINTS.EMPLEADO.GET_ALL, {
                method: 'GET',
                credentials: 'include'
            });

            if (response.ok) {
                const empleados = await response.json();
                displayEmpleados(empleados);
            } else if (response.status === 401 || response.status === 403) {
                showAlert('Sesión expirada. Por favor, inicie sesión nuevamente.', 'warning');
                setTimeout(() => logout(), 2000);
            } else {
                showAlert('Error al cargar los empleados', 'danger');
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
        } finally {
            loadingSpinner.style.display = 'none';
            empleadosContainer.style.display = 'block';
        }
    }

    function displayEmpleados(empleados) {
        empleadosTable.innerHTML = '';

        if (empleados.length === 0) {
            empleadosTable.innerHTML = `
                <tr>
                    <td colspan="5" class="text-center">No hay empleados registrados</td>
                </tr>
            `;
            return;
        }

        empleados.forEach(empleado => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${empleado.rfc || '-'}</td>
                <td>${empleado.nombre || '-'}</td>
                <td>${empleado.apellidos || '-'}</td>
                <td>${empleado.correo || '-'}</td>
                <td>
                    <div class="btn-group btn-group-sm" role="group">
                        <button type="button" class="btn btn-success" onclick="calcularNomina('${empleado.rfc}', '${empleado.nombre}', '${empleado.apellidos}')">
                            Calcular Nómina
                        </button>
                        <button type="button" class="btn btn-info" onclick="consultarNominas('${empleado.rfc}', '${empleado.nombre}', '${empleado.apellidos}')">
                            Consultar Nóminas
                        </button>
                    </div>
                </td>
            `;
            empleadosTable.appendChild(row);
        });
    }

    function showAlert(message, type) {
        const alert = document.createElement('div');
        alert.className = `alert alert-${type} alert-dismissible fade show`;
        alert.role = 'alert';
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        alertContainer.appendChild(alert);
    }

    // Hacer las funciones globales para que puedan ser llamadas desde los botones
    window.calcularNomina = function(rfc, nombre, apellidos) {
        // Guardar en sessionStorage para usar en la siguiente página
        sessionStorage.setItem('empleadoRFC', rfc);
        sessionStorage.setItem('empleadoNombre', nombre);
        sessionStorage.setItem('empleadoApellidos', apellidos);
        window.location.href = 'calcular-nomina.html';
    };

    window.consultarNominas = function(rfc, nombre, apellidos) {
        // Guardar en sessionStorage para usar en la siguiente página
        sessionStorage.setItem('empleadoRFC', rfc);
        sessionStorage.setItem('empleadoNombre', nombre);
        sessionStorage.setItem('empleadoApellidos', apellidos);
        window.location.href = 'consultar-nominas.html';
    };
});

