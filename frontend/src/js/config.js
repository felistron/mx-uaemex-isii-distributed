// Configuración de la API
const API_BASE_URL = 'http://localhost:3000';

const API_ENDPOINTS = {
    AUTH: {
        REGISTER: `${API_BASE_URL}/auth/register`,
        LOGIN: `${API_BASE_URL}/auth/login`
    },
    EMPLEADO: {
        GET_ALL: `${API_BASE_URL}/empleado/`
    },
    NOMINA: {
        CREATE: `${API_BASE_URL}/nomina/`,
        GET_ALL: (rfc) => `${API_BASE_URL}/nomina/?rfc=${rfc}`,
        DELETE: (id) => `${API_BASE_URL}/nomina/${id}`
    }
};

// Nombre de la cookie para el token
const TOKEN_COOKIE_NAME = 'access_token';

