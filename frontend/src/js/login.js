// Lógica para el inicio de sesión

document.addEventListener('DOMContentLoaded', function() {
    // Redirigir si ya está autenticado
    redirectIfAuthenticated();

    const loginForm = document.getElementById('loginForm');
    const submitBtn = document.getElementById('submitBtn');
    const alertContainer = document.getElementById('alertContainer');

    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Limpiar alertas previas
        alertContainer.innerHTML = '';

        const formData = {
            correo: document.getElementById('correo').value.trim(),
            password: document.getElementById('password').value
        };

        // Deshabilitar el botón de envío
        submitBtn.disabled = true;
        submitBtn.textContent = 'Ingresando...';

        try {
            const response = await fetch(API_ENDPOINTS.AUTH.LOGIN, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData),
                credentials: 'include' // Importante para que se guarden las cookies
            });

            if (response.ok) {
                const data = await response.json();

                // Guardar el token en una cookie
                setCookie(TOKEN_COOKIE_NAME, data.token, data.expiresInMs);

                showAlert('Inicio de sesión exitoso', 'success');

                // Redirigir al dashboard
                setTimeout(() => {
                    window.location.href = 'dashboard.html';
                }, 1000);
            } else {
                const errorData = await response.json().catch(() => ({ message: 'Credenciales inválidas' }));
                showAlert(errorData.message || 'Credenciales inválidas', 'danger');
                submitBtn.disabled = false;
                submitBtn.textContent = 'Ingresar';
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
            submitBtn.disabled = false;
            submitBtn.textContent = 'Ingresar';
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
});

