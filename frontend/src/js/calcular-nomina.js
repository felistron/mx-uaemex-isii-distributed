// Lógica para calcular nómina

document.addEventListener('DOMContentLoaded', function() {
    // Proteger la página
    if (!protectPage()) return;

    const logoutBtn = document.getElementById('logoutBtn');
    const adminRFCElement = document.getElementById('adminRFC');
    const nominaForm = document.getElementById('nominaForm');
    const submitBtn = document.getElementById('submitBtn');
    const alertContainer = document.getElementById('alertContainer');
    const resultadoNomina = document.getElementById('resultadoNomina');

    // Obtener información del usuario
    const userInfo = getUserInfo();
    if (userInfo && userInfo.sub) {
        adminRFCElement.textContent = userInfo.sub;
    }

    // Manejar el cierre de sesión
    logoutBtn.addEventListener('click', function() {
        logout();
    });

    // Cargar datos del empleado desde sessionStorage
    const empleadoRFC = sessionStorage.getItem('empleadoRFC');
    const empleadoNombre = sessionStorage.getItem('empleadoNombre');
    const empleadoApellidos = sessionStorage.getItem('empleadoApellidos');

    if (!empleadoRFC) {
        showAlert('No se ha seleccionado ningún empleado', 'warning');
        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 2000);
        return;
    }

    // Llenar los campos con los datos del empleado
    document.getElementById('rfc').value = empleadoRFC;
    document.getElementById('nombreCompleto').value = `${empleadoNombre} ${empleadoApellidos}`;

    // Manejar el envío del formulario
    nominaForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Limpiar alertas previas
        alertContainer.innerHTML = '';
        resultadoNomina.style.display = 'none';

        // Validar fechas
        const fechaInicio = document.getElementById('fechaInicio').value;
        const fechaFin = document.getElementById('fechaFin').value;

        if (new Date(fechaInicio) > new Date(fechaFin)) {
            showAlert('La fecha de inicio no puede ser posterior a la fecha de fin', 'danger');
            return;
        }

        // Preparar los datos
        const formData = {
            rfc: empleadoRFC,
            salario: parseFloat(document.getElementById('salario').value),
            fechaInicio: fechaInicio,
            fechaFin: fechaFin
        };

        // Deshabilitar el botón de envío
        submitBtn.disabled = true;
        submitBtn.textContent = 'Calculando...';

        try {
            const response = await fetch(API_ENDPOINTS.NOMINA.CREATE, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json();

                // Limpiar errores previos
                clearFieldErrors();

                showAlert('Nómina calculada exitosamente', 'success');
                displayResultado(data);

                // Limpiar solo los campos editables
                document.getElementById('salario').value = '';
                document.getElementById('fechaInicio').value = '';
                document.getElementById('fechaFin').value = '';
            } else if (response.status === 401 || response.status === 403) {
                showAlert('Sesión expirada. Por favor, inicie sesión nuevamente.', 'warning');
                setTimeout(() => logout(), 2000);
            } else {
                const errorData = await response.json().catch(() => ({ message: 'Error al calcular la nómina' }));

                // Limpiar errores previos
                clearFieldErrors();

                // Si es un error 400 con errores de validación
                if (response.status === 400 && errorData.errors && Array.isArray(errorData.errors)) {
                    // Mostrar errores específicos por campo
                    errorData.errors.forEach(error => {
                        showFieldError(error.field, error.defaultMessage);
                    });

                    // Mostrar alerta general
                    showAlert('Por favor, corrija los errores en el formulario', 'danger');
                } else {
                    // Error genérico
                    showAlert(errorData.message || 'Error al calcular la nómina', 'danger');
                }
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Calcular Nómina';
        }
    });

    function displayResultado(data) {
        document.getElementById('resSalario').textContent = formatCurrency(data.salario);
        document.getElementById('resExcedente').textContent = formatCurrency(data.excedente);
        document.getElementById('resCuotaFija').textContent = formatCurrency(data.cuotaFija);
        document.getElementById('resPorcentaje').textContent = data.porcentaje.toFixed(2);
        document.getElementById('resPeriodoInicio').textContent = formatDate(data.periodoInicio);
        document.getElementById('resPeriodoFin').textContent = formatDate(data.periodoFin);

        resultadoNomina.style.display = 'block';
        resultadoNomina.scrollIntoView({ behavior: 'smooth' });
    }

    function formatCurrency(value) {
        return new Intl.NumberFormat('es-MX', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(value);
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('es-MX');
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

    function showFieldError(fieldName, message) {
        const field = document.getElementById(fieldName);
        if (!field) return;

        // Agregar clase de error al campo
        field.classList.add('is-invalid');

        // Crear o actualizar el mensaje de error
        let errorDiv = field.parentElement.querySelector('.invalid-feedback');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            field.parentElement.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
    }

    function clearFieldErrors() {
        // Remover todas las clases de error
        document.querySelectorAll('.is-invalid').forEach(field => {
            field.classList.remove('is-invalid');
        });

        // Remover todos los mensajes de error
        document.querySelectorAll('.invalid-feedback').forEach(errorDiv => {
            errorDiv.remove();
        });
    }
});

