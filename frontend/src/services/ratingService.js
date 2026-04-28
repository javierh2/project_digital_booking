import { apiRequest } from "./api"

// trae todas las reseñas de una room — público, sin token
export const getRatingsByRoom = async (roomId) => {
    return apiRequest(`/ratings/room/${roomId}`, {})
}

// consulta si el usuario autenticado puede puntuar esta room
// devuelve { canRate: boolean }
export const canUserRate = async (roomId) => {
    return apiRequest(`/ratings/room/${roomId}/can-rate`, {})
}

// envía una nueva reseña, requiere token
// body: { stars: number, comment: string | null }
export const createRating = async (roomId, stars, comment) => {
    return apiRequest(`/ratings/room/${roomId}`, {
        method: "POST",
        body: JSON.stringify({ stars, comment: comment || null })
    })
}