// Lógica para consultar nóminas

document.addEventListener('DOMContentLoaded', function() {
    // Proteger la página
    if (!protectPage()) return;

    const logoutBtn = document.getElementById('logoutBtn');
    const adminRFCElement = document.getElementById('adminRFC');
    const empleadoNombreElement = document.getElementById('empleadoNombre');
    const empleadoRFCElement = document.getElementById('empleadoRFC');
    const loadingSpinner = document.getElementById('loadingSpinner');
    const nominasContainer = document.getElementById('nominasContainer');
    const nominasTable = document.getElementById('nominasTable');
    const emptyMessage = document.getElementById('emptyMessage');
    const alertContainer = document.getElementById('alertContainer');
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

    let nominaIdToDelete = null;

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

    // Mostrar información del empleado
    empleadoNombreElement.textContent = `${empleadoNombre} ${empleadoApellidos}`;
    empleadoRFCElement.textContent = empleadoRFC;

    // Cargar las nóminas
    loadNominas();

    async function loadNominas() {
        try {
            const response = await fetch(API_ENDPOINTS.NOMINA.GET_ALL(empleadoRFC), {
                method: 'GET',
                credentials: 'include'
            });

            if (response.ok) {
                const nominas = await response.json();
                displayNominas(nominas);
            } else if (response.status === 401 || response.status === 403) {
                showAlert('Sesión expirada. Por favor, inicie sesión nuevamente.', 'warning');
                setTimeout(() => logout(), 2000);
            } else {
                showAlert('Error al cargar las nóminas', 'danger');
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
        } finally {
            loadingSpinner.style.display = 'none';
            nominasContainer.style.display = 'block';
        }
    }

    function displayNominas(nominas) {
        nominasTable.innerHTML = '';

        if (nominas.length === 0) {
            emptyMessage.style.display = 'block';
            return;
        }

        emptyMessage.style.display = 'none';

        nominas.forEach(nomina => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${nomina.id || '-'}</td>
                <td>$${formatCurrency(nomina.salario)}</td>
                <td>$${formatCurrency(nomina.excedente)}</td>
                <td>$${formatCurrency(nomina.cuotaFija)}</td>
                <td>${(nomina.porcentaje * 100).toFixed(2)}%</td>
                <td>$${formatCurrency(nomina.salario - (nomina.cuotaFija + nomina.excedente * nomina.porcentaje))}</td>
                <td>${nomina.periodoInicio}</td>
                <td>${nomina.periodoFin}</td>
                <td>
                    <button type="button" class="btn btn-danger btn-sm" onclick="confirmDelete(${nomina.id})">
                        Eliminar
                    </button>
                </td>
            `;
            nominasTable.appendChild(row);
        });
    }

    // Confirmar eliminación
    window.confirmDelete = function(nominaId) {
        nominaIdToDelete = nominaId;
        deleteModal.show();
    };

    // Manejar la eliminación
    confirmDeleteBtn.addEventListener('click', async function() {
        if (!nominaIdToDelete) return;

        try {
            const response = await fetch(API_ENDPOINTS.NOMINA.DELETE(nominaIdToDelete), {
                method: 'DELETE',
                credentials: 'include',
            });

            if (response.ok) {
                showAlert('Nómina eliminada exitosamente', 'success');
                deleteModal.hide();
                nominaIdToDelete = null;

                // Recargar la lista de nóminas
                loadingSpinner.style.display = 'block';
                nominasContainer.style.display = 'none';
                await loadNominas();
            } else if (response.status === 401 || response.status === 403) {
                showAlert('Sesión expirada. Por favor, inicie sesión nuevamente.', 'warning');
                deleteModal.hide();
                setTimeout(() => logout(), 2000);
            } else {
                showAlert('Error al eliminar la nómina', 'danger');
                deleteModal.hide();
            }
        } catch (error) {
            console.error('Error:', error);
            showAlert('Error de conexión con el servidor', 'danger');
            deleteModal.hide();
        }
    });

    function formatCurrency(value) {
        return new Intl.NumberFormat('es-MX', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(value);
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

        // Auto-cerrar después de 5 segundos
        setTimeout(() => {
            alert.remove();
        }, 5000);
    }
});

