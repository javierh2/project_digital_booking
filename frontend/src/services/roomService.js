const BASE_URL = 'http://localhost:8080/api/rooms';

export const getRandomRooms = async () => {
    const response = await fetch(`${BASE_URL}/random`)
    if(!response.ok){
        throw new Error(`Error ${response.status}: no se pueden cargar las habitaciones, intente más tarde`)
    }
    return response.json()
}

export const getAllRooms = async () => {
    const response = await fetch(BASE_URL)
    if (!response.ok){
        throw new Error(`Error ${response.status}: no se pueden cargar las habitaciones, intente más tarde`)
    }
    return response.json()
}

export const getRoomById = async (id) => {
    const response = await fetch(`${BASE_URL}/${id}`)
    if (!response.ok){
        throw new Error(`Error ${response.status}: no se pudo encontrar esa habitación particular`)
    }
    return response.json()
}

export const deleteRoom = async (id) => {
    const response = await fetch (`$(BASE_URL}/${id}`, {
        method: 'DELETE'
    })
    if (!response.ok){
        throw new Error(`Error ${response.status}: no se pudo eliminar la habitación seleccionada`)
    }
    return response
}


export const createRoom = async (roomData) => {
    const response = await fetch(BASE_URL,{
        method:'POST',
        headers:{
            'Content-Type':'aplication/json'
        },
        body:JSON.stringify(roomData)
    })
    if (!response.ok){
        throw new Error(`Eror ${response.status}: no se pudo crear la habitación`)
    }
    return response.json()
}

