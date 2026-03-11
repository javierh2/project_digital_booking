import { useEffect, useState } from 'react';
import { deleteRoom, getAllRooms } from '../../services/roomService'
import RoomForm from '../../components/RoomForm/RoomForm'
import '../Admin/Admin.css';

const Admin = () => {

    const [rooms, setRooms] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const [showForm, setShowForm] = useState(false)


    const fetchRooms = async () => {
        setLoading(true)
        setError(null)
        try {
            const data = await getAllRooms()
            setRooms(data)
        } catch (error) {
            setError(error)
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        fetchRooms()
    }, [])

    const handleRoomCreated = (newRoom) => {
        setRooms(prev => [...prev, newRoom])
    }

    const handleDelete = async (id, name) => {
        const confirmation = window.confirm(
            `¿Estás seguro de querer eliminar "${name}"?\
            Esta acción no se puede revertir.`
        )
        if (!confirmation) return

        try {
            await deleteRoom(id)
            setRooms(prev => prev.filter(room => room.id !== id))
        } catch (error) {
            alert(`Error al eliminar: ${error.message}`)
            console.error(`Error al eliminar habitación: `, error)
        }
    }

    const totalRooms = rooms.length
    const averagePrice = rooms.length > 0 ? Math.round(rooms.reduce((acc, room) => acc + room.price, 0) / rooms.length) : 0

    const minimumPrice = rooms.length > 0 ? Math.min(...rooms.map(room => room.price)) : 0

    return (
        <div className="admin">
            <div className="admin__content">

                <div className="admin__header">
                    <div className="admin__header-info">
                        <h1>Panel de Administración</h1>
                        <p>Gestioná las habitaciones de Digital Booking</p>
                    </div>

                    <button
                        className="admin__btn-add"
                        onClick={() => setShowForm(true)}
                    >
                        + Nueva habitación
                    </button>
                </div>

                {!loading && !error && (
                    <div className="admin__stats">
                        <div className="admin__stat-card">
                            <div className="admin__stat-number">{totalRooms}</div>
                            <div className="admin__stat-label">Habitaciones totales</div>
                        </div>
                        <div className="admin__stat-card">
                            <div className="admin__stat-number">${averagePrice}</div>
                            <div className="admin__stat-label">Precio promedio</div>
                        </div>
                        <div className="admin__stat-card">
                            <div className="admin__stat-number">${minimumPrice}</div>
                            <div className="admin__stat-label">Precio más bajo</div>
                        </div>
                    </div>
                )}

                {loading ? (
                    <div className="admin__state">
                        <div className="admin__spinner" />
                        <p>Cargando habitaciones...</p>
                    </div>

                ) : error ? (
                    <div className="admin__state">
                        <p>⚠️ {error}</p>
                        <button onClick={fetchRooms}>Reintentar</button>
                    </div>

                ) : (
                    <div className="admin__table-wrapper">
                        <table className="admin__table">
                            <thead>
                                <tr>
                                    <th>Imagen</th>
                                    <th>Nombre</th>
                                    <th className="hide-mobile">Categoría</th>
                                    <th className="hide-mobile">Precio</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                {rooms.map(room => (
                                    <tr key={room.id}>

                                        <td>
                                            {room.imageRoom ? (
                                                <img
                                                    src={room.imageRoom}
                                                    alt={room.name}
                                                    className="admin__room-img"
                                                    onError={(e) => {
                                                        e.target.style.display = 'none'
                                                        e.target.nextSibling.style.display = 'flex'
                                                    }}
                                                />
                                            ) : null}
                                            <div
                                                className="admin__room-img-placeholder"
                                                style={{ display: room.imageRoom ? 'none' : 'flex' }}
                                            >
                                                🏨
                                            </div>
                                        </td>

                                        <td>{room.name}</td>

                                        <td className="hide-mobile">
                                            <span className="admin__badge">{room.category}</span>
                                        </td>

                                        <td className="hide-mobile">
                                            <span className="admin__price">${room.price}</span>
                                        </td>

                                        <td>
                                            <button
                                                className="admin__btn-delete"
                                                onClick={() => handleDelete(room.id, room.name)}
                                            >
                                                Eliminar
                                            </button>
                                        </td>

                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}

            </div>

            {showForm && (
                <RoomForm
                    onClose={() => setShowForm(false)}
                    onRoomCreated={handleRoomCreated}
                />
            )}

        </div>
    )
}

export default Admin