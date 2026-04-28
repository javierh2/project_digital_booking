import { apiRequest } from "./api"

export const getRandomRooms = async () => {
    return apiRequest("/rooms/random",{})
}

export const getAllRooms = async () => {
    return apiRequest("/rooms", {})
}

export const getRoomById = async (id) => {
    return apiRequest(`/rooms/${id}`,{})
}

// requiere ROLE_ADMIN — manda el token en Authorization
export const deleteRoom = async (id) => {
    return apiRequest(`/rooms/${id}`, {
        method: "DELETE"
    })
}

// requiere ROLE_ADMIN — manda el token en Authorization
export const createRoom = async (roomData) => {
    return apiRequest("/rooms", {
        method: "POST",
        body: JSON.stringify(roomData)
    })
}

// requiere ROLE_ADMIN — PUT /api/rooms/{id}
// reemplaza todos los campos editables de la habitación existente
export const updateRoom = async (id, roomData) => {
    return apiRequest(`/rooms/${id}`, {
        method: "PUT",
        body: JSON.stringify(roomData)
    })
}

// GET /api/rooms/available?checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD
// público, no requiere token
// checkIn y checkOut son strings en formato ISO (YYYY-MM-DD) que vienen del date picker
export const getAvailableRooms = async (checkIn, checkOut) => {
    return apiRequest(`/rooms/available?checkIn=${checkIn}&checkOut=${checkOut}`, {})
}