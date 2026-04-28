const BASE_URL = "http://localhost:8080/api"

// token del localStorage
// Si algún día cambia la key "db_user", solo se cambia aquí
export const getToken = () => {
    const stored = localStorage.getItem("db_user")
    if (!stored) return null
    const user = JSON.parse(stored)
    return user?.token || null
}

// header de autorización
export const getAuthHeader = () => {
    const token = getToken()
    if (!token) return {}
    return { Authorization: `Bearer ${token}` }
}

// Helper para hacer requests con headers comunes
export const apiRequest = async (endpoint, options = {}) => {
    const url = `${BASE_URL}${endpoint}`
    const token = getToken()
    
    // Construir headers base
    const headers = {
        "Content-Type": "application/json"
    }
    
    // Solo agregar Authorization si hay token válido
    if (token) {
        headers["Authorization"] = `Bearer ${token}`
    }

    const response = await fetch(url, {
        ...options,
        headers: {
            ...headers,
            ...options.headers
        }
    })
    if (!response.ok) {
        const error = await response.json().catch(() => ({}))
        throw new Error(error.message || `Error ${response.status}`)
    }
    return response.json()
}

export { BASE_URL }