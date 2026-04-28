import { apiRequest } from "./api"


export const getOccupiedDates = async (roomId) => {
    return apiRequest(`/bookings/room/${roomId}/occupied-dates`,{})
}


export const createBooking = async (roomId, checkIn, checkOut) => {
    return apiRequest("/bookings",{
        method: "POST",
        body: JSON.stringify({roomId, checkIn, checkOut})
    })
}


// trae el historial de reservas del usuario autenticado
// el backend ordena por checkIn descendente, el frontend solo renderiza
export const getMyBookings = async () => {
    return apiRequest("/bookings/my-bookings", {})
}