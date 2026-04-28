import { apiRequest } from "./api"

// trae las rooms favoritas completas del usuario logueado
export const getFavoriteRooms = async () => {
    return apiRequest("/favorites", {})
}

// trae solo los ids de rooms favoritas — para inicializar los corazones activos
// más eficiente que traer los objetos completos
export const getFavoriteIds = async () => {
    return apiRequest("/favorites/ids", {})
}

// agrega una room a favoritos
export const addFavorite = async (roomId) => {
    return apiRequest(`/favorites/${roomId}`, {
        method: "POST"
    })
}

// quita una room de favoritos
export const removeFavorite = async (roomId) => {
    return apiRequest(`/favorites/${roomId}`, {
        method: "DELETE"
    })
}