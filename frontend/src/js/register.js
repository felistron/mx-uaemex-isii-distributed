// Lógica para el registro de empleados

document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    const esAdministradorCheckbox = document.getElementById('esAdministrador');
    const passwordFields = document.getElementById('passwordFields');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const submitBtn = document.getElementById('submitBtn');
    const alertContainer = document.getElementById('alertContainer');

    // Mostrar/ocultar campos de contraseña según el checkbox
    esAdministradorCheckbox.addEventListener('change', function() {
        if (this.checked) {
            passwordFields.style.display = 'block';
            passwordInput.required = true;
            confirmPasswordInput.required = true;
        } else {
            passwordFields.style.display = 'none';
            passwordInput.required = false;
            confirmPasswordInput.required = false;
            passwordInput.value = '';
            confirmPasswordInput.value = '';
        }
    });

    // Manejar el envío del formulario
    registerForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Limpiar alertas previas
        alertContainer.innerHTML = '';

        // Validar contraseñas si es administrador
        if (esAdministradorCheckbox.checked) {
            if (passwordInput.value !== confirmPasswordInput.value) {
                showAlert('Las contraseñas no coinciden', 'danger');
                return;
            }
        }

        // Preparar los datos
        const formData = {
            rfc: document.getElementById('rfc').value.trim(),
            nombre: document.getElementById('nombre').value.trim(),
            apellidos: document.getElementById('apellidos').value.trim(),
            correo: document.getElementById('correo').value.trim(),
            esAdministrador: esAdministradorCheckbox.checked,
            password: esAdministradorCheckbox.checked ? passwordInput.value : null,
            confirmPassword: esAdministradorCheckbox.checked ? confirmPasswordInput.value : null
        };

        // Deshabilitar el botón de envío
        submitBtn.disabled = true;
        submitBtn.textContent = 'Registrando...';

        try {
            const response = await fetch(API_ENDPOINTS.AUTH.REGISTER, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json();

                // Limpiar errores previos
                clearFieldErrors();

                showAlert(`Empleado ${data.correo} registrado exitosamente`, 'success');

                // Limpiar el formulario
                registerForm.reset();
                passwordFields.style.display = 'none';

                submitBtn.disabled = false;
                submitBtn.textContent = 'Registrar';
            } else {
                const errorData = await response.json().catch(() => ({ message: 'Error al registrar el empleado' }));

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
                    showAlert(errorData.message || 'Error al registrar el empleado', 'danger');
                }

                submitBtn.disabled = false;
                submitBtn.textContent = 'Registrar';
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
            submitBtn.disabled = false;
            submitBtn.textContent = 'Registrar';
        }
    });

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

