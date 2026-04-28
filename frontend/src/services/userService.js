import { apiRequest } from "./api"

// GET /api/users — devuelve todos los usuarios registrados
// solo ROLE_ADMIN puede llamar este endpoint — SecurityConfig lo garantiza
export const getAllUsers = async () => {
    return apiRequest("/users", {})
}

// PUT /api/users/{id}/role — cambia el rol de un usuario
// newRole es el string del enum: "ROLE_ADMIN" o "ROLE_USER"
export const updateUserRole = async (id, newRole) => {
    return apiRequest(`/users/${id}/role`, {
        method: "PUT",
        body: JSON.stringify({ role: newRole })
    })
}