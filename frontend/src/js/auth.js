// Utilidades para manejo de autenticación

// Obtener el valor de una cookie
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
}

// Establecer una cookie
function setCookie(name, value, expiresInMs) {
    const date = new Date();
    date.setTime(date.getTime() + expiresInMs);
    const expires = `expires=${date.toUTCString()}`;
    document.cookie = `${name}=${value};${expires};path=/`;
}

// Eliminar una cookie
function deleteCookie(name) {
    document.cookie = `${name}=;expires=Thu, 01 Jan 1970 00:00:00 UTC;path=/;`;
}

// Verificar si el usuario está autenticado
function isAuthenticated() {
    return getCookie(TOKEN_COOKIE_NAME) !== null;
}

// Obtener el token de autenticación
function getAuthToken() {
    return getCookie(TOKEN_COOKIE_NAME);
}

// Decodificar JWT (simple, solo para obtener el payload)
function decodeJWT(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error('Error decodificando JWT:', error);
        return null;
    }
}

// Obtener información del usuario desde el token
function getUserInfo() {
    const token = getAuthToken();
    if (!token) return null;

    return decodeJWT(token);
}

// Cerrar sesión
function logout() {
    deleteCookie(TOKEN_COOKIE_NAME);
    window.location.href = 'index.html';
}

// Proteger página (redirigir si no está autenticado)
function protectPage() {
    if (!isAuthenticated()) {
        window.location.href = 'login.html';
        return false;
    }
    return true;
}

// Redirigir si ya está autenticado
function redirectIfAuthenticated() {
    if (isAuthenticated()) {
        window.location.href = 'dashboard.html';
        return true;
    }
    return false;
}

