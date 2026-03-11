import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { getRoomById } from "../../services/roomService"
import './RoomDetail.css'



const RoomDetail = () => {

    const { id } = useParams()

    const navigate = useNavigate()

    const [room, setRoom] = useState(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    useEffect(() => {
        const fetchRoom = async () => {
            try {
                const data = await getRoomById(id)
                setRoom(data)
            } catch (error) {
                setError(error.message)
            } finally {
                setLoading(false)
            }
        }
        fetchRoom()
    }, [id])

    if (loading) {
        return (
            <div className="room-detail">
                <div className="room-detail__state">
                    <div className="room-detail__spinner" />
                    <p>Cargando habitación...</p>
                </div>
            </div>
        )
    }
    if (error) {
        return (
            <div className="room-detail">
                <div className="room-detail__state">
                    <span style={{ fontSize: '40px' }}>⚠️</span>
                    <p className="room-detail__error-text">
                        No encontramos la habitación que buscás.
                    </p>
                    <button
                        className="room-detail__back-btn"
                        onClick={() => navigate('/')}
                    >
                        ← Volver al inicio
                    </button>

                </div>
            </div>
        )
    }

    return (
        <div className="room-detail">

            {/* ── HERO CON IMAGEN ── */}
            <div className="room-detail__hero">

                {room.imageRoom ? (
                    <img
                        src={room.imageRoom}
                        alt={room.name}
                        className="room-detail__hero-image"
                    />
                ) : (
                    <div className="room-detail__hero-placeholder">
                        🏨
                    </div>
                )}

                <div className="room-detail__hero-overlay" />

                {room.category && (
                    <span className="room-detail__hero-badge">
                        {room.category}
                    </span>
                )}

                <button
                    className="room-detail__back-btn"
                    onClick={() => navigate('/')}
                >
                    ← Volver
                </button>

            </div>

            {/* ── CONTENIDO ── */}
            <div className="room-detail__content">

                <div className="room-detail__header">
                    <h1 className="room-detail__name">{room.name}</h1>
                    <div className="room-detail__price-row">
                        <span className="room-detail__price">${room.price}</span>
                        <span className="room-detail__price-label">por noche</span>
                    </div>
                </div>

                <div className="room-detail__divider" />

                <p className="room-detail__section-title">Descripción</p>
                <p className="room-detail__description">{room.description}</p>

                {/* ── INFO CARDS ── */}
                <div className="room-detail__info-grid">
                    <div className="room-detail__info-card">
                        <span className="room-detail__info-icon">🏷️</span>
                        <p className="room-detail__info-label">Categoría</p>
                        <p className="room-detail__info-value">{room.category}</p>
                    </div>
                    <div className="room-detail__info-card">
                        <span className="room-detail__info-icon">💰</span>
                        <p className="room-detail__info-label">Precio por noche</p>
                        <p className="room-detail__info-value">${room.price}</p>
                    </div>
                    <div className="room-detail__info-card">
                        <span className="room-detail__info-icon">✅</span>
                        <p className="room-detail__info-label">Disponibilidad</p>
                        <p className="room-detail__info-value">
                            {room.active ? 'Disponible' : 'No disponible'}
                        </p>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default RoomDetail