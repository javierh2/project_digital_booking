
import { BASE_URL, apiRequest } from "./api"

// register llama a la API para crear un nuevo usuario, recibe un objeto con los datos del formulario de registro y devuelve la respuesta de la API
export const registerUser = async (data) => {
    return apiRequest("/auth/register",{
        method: "POST",
        body: JSON.stringify(data)
    })
}

// login llama a la API para iniciar sesión, recibe un objeto con los datos del formulario de login y devuelve la respuesta de la API que incluye el token JWT
export const loginUser = async(data) =>{
    return apiRequest("/auth/login",{
        method: "POST",
        body: JSON.stringify(data)
    })
}