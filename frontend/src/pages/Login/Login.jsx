import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { loginUser } from '../../services/authService'
import './Login.css'

// Página de login
const Login = () => {

    const navigate = useNavigate()
    const { login } = useAuth()

    const [formData, setFormData] = useState({
        email: "",
        password: ""
    })

    const [errors, setErrors] = useState({})
    const [submitting, setSubmitting] = useState(false)

    // función para manejar el cambio en los campos del formulario, actualiza el estado del formulario y limpia los errores correspondientes
    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData(prev => ({ ...prev, [name]: value }))
        if (errors[name]) {
            setErrors(prev => ({ ...prev, [name]: '' }))
        }
    }

    // función para validar el formulario, verifica que el email y la contraseña no estén vacíos, y actualiza el estado de errores
    const validate = () => {
        const newErrors = {}
        if (!formData.email.trim()) {
            newErrors.email = "El email es obligatorio"
        }
        if (!formData.password) {
            newErrors.password = "La contraseña es obligatoria"
        }
        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const handleSubmit = async () => {
        if (!validate()) return
        setSubmitting(true)
        try {
            const data = await loginUser(formData)
            login(data)
            navigate("/")
        } catch (error) {
            // mensaje de error  cuando las credenciales son incorrectas
            setErrors({ general: error.message })
        } finally {
            setSubmitting(false)
        }
    }

    // permite enviar el formulario con Enter
    const handleKeyDown = (e) => {
        if (e.key === "Enter") handleSubmit()
    }

    return (
        <div className="login">
            <div className="login__card">

                <div className="login__header">
                    <h1 className="login__title">Iniciar sesión</h1>
                    <p className="login__subtitle">Ingresá con tu email y contraseña</p>
                </div>

                <div className="login__form">

                    {/* error general — aparece cuando las credenciales son incorrectas */}
                    {errors.general && (
                        <div className="login__error-general">
                            {errors.general}
                        </div>
                    )}

                    <div className="login__group">
                        <label className="login__label">
                            Email <span className="login__required">*</span>
                        </label>
                        <input
                            type="email"
                            name="email"
                            className={`login__input ${errors.email ? 'login__input--error' : ''}`}
                            placeholder="Ej: juan@email.com"
                            value={formData.email}
                            onChange={handleChange}
                            onKeyDown={handleKeyDown}
                        />
                        {errors.email && (
                            <span className="login__error">{errors.email}</span>
                        )}
                    </div>

                    <div className="login__group">
                        <label className="login__label">
                            Contraseña <span className="login__required">*</span>
                        </label>
                        <input
                            type="password"
                            name="password"
                            className={`login__input ${errors.password ? 'login__input--error' : ''}`}
                            placeholder="Tu contraseña"
                            value={formData.password}
                            onChange={handleChange}
                            onKeyDown={handleKeyDown}
                        />
                        {errors.password && (
                            <span className="login__error">{errors.password}</span>
                        )}
                    </div>

                    <button
                        className="login__btn-submit"
                        onClick={handleSubmit}
                        disabled={submitting}
                    >
                        {submitting ? 'Ingresando...' : 'Iniciar sesión'}
                    </button>

                    <p className="login__register-link">
                        ¿No tenés cuenta?{' '}
                        <span
                            className="login__register-link-action"
                            onClick={() => navigate('/register')}
                        >
                            Registrate
                        </span>
                    </p>

                </div>
            </div>
        </div>
    )
}

export default Login