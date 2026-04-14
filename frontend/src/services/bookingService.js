const BASE_URL = "http://localhost:8080/api/bookings"

const getAuthHeader = () => {
    const stored = localStorage.getItem("db_user")
    if (!stored) return {}
    const user = JSON.parse(stored)
    return { "Authorization": `Bearer ${user.token}` }
}

// trae los rangos de fechas ocupadas para una room
// público, no requiere token
// devuelve [{id, roomId, checkIn, checkOut}] donde checkIn/checkOut son "YYYY-MM-DD"
export const getOccupiedDates = async (roomId) => {
    const response = await fetch(`${BASE_URL}/room/${roomId}/occupied-dates`)
    if (!response.ok) {
        throw new Error(`Error ${response.status}: no se pudo obtener la disponibilidad`)
    }
    return response.json()
}