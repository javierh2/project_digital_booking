import { apiRequest } from "./api"

// GET público, sin token
// usado en Admin para listar las features y en RoomForm para el selector
export const getAllFeatures = async () => {
    return apiRequest("/features", {})
}

// POST requiere token de ADMIN
// dto = { name: string, icon: string }
export const createFeature = async (dto) => {
    return apiRequest("/features", {
        method: "POST",
        body: JSON.stringify(dto)
    })
}

// DELETE requiere token de ADMIN
// no devuelve body, el backend responde 204 No Content
export const deleteFeature = async (id) => {
    return apiRequest(`/features/${id}`, {
        method: "DELETE"
    })
}