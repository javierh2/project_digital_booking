import { apiRequest } from "./api"

// público, no necesita token de validación, usado en el Home y Form
export const getAllCategories = async() => {
    return apiRequest("/categories", {})
}

// requiere ROLE_ADMIN
export const createCategory = async(data) =>{
    return apiRequest("/categories", {
        method: "POST",
        body: JSON.stringify(data)
    })
}

// requiere ROLE_ADMIN
export const deleteCategory = async (id) => {
    return apiRequest(`/categories/${id}`, {
        method: "DELETE"
    })
}
