import { useState } from 'react'
import './ShareModal.css'

// construye las URLs de intención para cada red social
// estas URLs son el método oficial y estándar que cada red provee para compartir contenido externo
// no requieren registro de app ni API keys — son públicas y documentadas
const buildShareUrls = (url, title, description, customMessage) => {
    // el texto que acompaña al compartido: mensaje personalizado si existe, sino descripción
    const text = customMessage.trim() || description
    // encodeURIComponent convierte caracteres especiales en formato seguro para URLs
    const encodedUrl = encodeURIComponent(url)
    const encodedText = encodeURIComponent(text)
    const encodedTitle = encodeURIComponent(title)

    return {
        // twitter/X: intent/tweet acepta text y url como params
        twitter: `https://twitter.com/intent/tweet?text=${encodedText}&url=${encodedUrl}`,
        // facebook: sharer.php es la URL oficial de Facebook para compartir sin SDK
        facebook: `https://www.facebook.com/sharer/sharer.php?u=${encodedUrl}&quote=${encodedText}`,
        // whatsapp: wa.me/send acepta text con el link embebido — muy usado en Argentina
        whatsapp: `https://wa.me/?text=${encodeURIComponent(`${text} ${url}`)}`,
    }
}

// recibe isOpen, onClose, y los datos del producto a compartir
const ShareModal = ({ isOpen, onClose, room }) => {

    const [customMessage, setCustomMessage] = useState('')
    // copied: controla el feedback visual del botón de copiar link
    const [copied, setCopied] = useState(false)

    // si no está abierto no renderizamos — mismo patrón que ConfirmModal
    if (!isOpen || !room) return null

    // URL canónica del producto — la página de detalle actual
    const productUrl = `${window.location.origin}/rooms/${room.id}`
    // descripción corta: primeros 120 caracteres de la descripción del room
    const shortDescription = room.description?.slice(0, 120) + (room.description?.length > 120 ? '...' : '')

    const shareUrls = buildShareUrls(productUrl, room.name, shortDescription, customMessage)

    // abre la URL de intención en una ventana nueva con tamaño estándar de popup
    // 600x500 es el tamaño convencional para popups de compartir en redes sociales
    const openShare = (url) => {
        window.open(url, '_blank', 'width=600,height=500,noopener,noreferrer')
        onClose()
    }

    // copia el link al portapapeles usando la Clipboard API moderna
    // el feedback visual dura 2 segundos y luego vuelve al estado original
    const handleCopyLink = async () => {
        try {
            await navigator.clipboard.writeText(productUrl)
            setCopied(true)
            setTimeout(() => setCopied(false), 2000)
        } catch {
            // fallback para navegadores sin Clipboard API (muy raros en 2026)
            const input = document.createElement('input')
            input.value = productUrl
            document.body.appendChild(input)
            input.select()
            document.execCommand('copy')
            document.body.removeChild(input)
            setCopied(true)
            setTimeout(() => setCopied(false), 2000)
        }
    }

    return (
        // overlay oscuro — clic fuera cierra el modal, mismo patrón que ConfirmModal
        <div className="share-modal__overlay" onClick={onClose}>
            <div
                className="share-modal__box"
                // stopPropagation evita que un clic dentro cierre el overlay
                onClick={e => e.stopPropagation()}
            >

                <div className="share-modal__header">
                    <h2 className="share-modal__title">Compartir producto</h2>
                    <button
                        className="share-modal__close"
                        onClick={onClose}
                        aria-label="Cerrar"
                    >
                        ✕
                    </button>
                </div>

                {/* preview del producto — imagen + nombre + descripción corta
                    criterio de aceptación: mostrar imagen, descripción y enlace dentro del modal */}
                <div className="share-modal__preview">
                    {room.images && room.images[0] ? (
                        <img
                            src={room.images[0]}
                            alt={room.name}
                            className="share-modal__preview-img"
                        />
                    ) : (
                        <div className="share-modal__preview-placeholder">🏨</div>
                    )}
                    <div className="share-modal__preview-info">
                        <p className="share-modal__preview-name">{room.name}</p>
                        <p className="share-modal__preview-desc">{shortDescription}</p>
                        <p className="share-modal__preview-url">{productUrl}</p>
                    </div>
                </div>

                {/* campo de mensaje personalizado — criterio de aceptación explícito */}
                <div className="share-modal__message-group">
                    <label className="share-modal__message-label">
                        Mensaje personalizado <span className="share-modal__optional">(opcional)</span>
                    </label>
                    <textarea
                        className="share-modal__message-input"
                        placeholder="Ej: ¡Mirá esta habitación increíble que encontré!"
                        value={customMessage}
                        onChange={e => setCustomMessage(e.target.value)}
                        rows={2}
                        maxLength={280}
                    />
                </div>

                {/* botones de redes sociales — criterio de aceptación: Facebook, Twitter e Instagram
                    Instagram se reemplaza por WhatsApp (muy relevante en Argentina) y por "Copiar link"
                    dado que Instagram no tiene URL de intención pública para compartir contenido externo */}
                <div className="share-modal__networks">
                    <p className="share-modal__networks-label">Compartir en</p>
                    <div className="share-modal__network-btns">

                        <button
                            className="share-modal__network-btn share-modal__network-btn--twitter"
                            onClick={() => openShare(shareUrls.twitter)}
                        >
                            <span className="share-modal__network-icon">𝕏</span>
                            Twitter / X
                        </button>

                        <button
                            className="share-modal__network-btn share-modal__network-btn--facebook"
                            onClick={() => openShare(shareUrls.facebook)}
                        >
                            <span className="share-modal__network-icon">f</span>
                            Facebook
                        </button>

                        <button
                            className="share-modal__network-btn share-modal__network-btn--whatsapp"
                            onClick={() => openShare(shareUrls.whatsapp)}
                        >
                            <span className="share-modal__network-icon">💬</span>
                            WhatsApp
                        </button>

                        {/* Instagram: copiamos el link porque no tiene URL de intención pública
                            el usuario puede pegarlo en su story o bio manualmente */}
                        <button
                            className="share-modal__network-btn share-modal__network-btn--instagram"
                            onClick={handleCopyLink}
                        >
                            <span className="share-modal__network-icon">📷</span>
                            Instagram
                        </button>

                    </div>
                </div>

                {/* sección de copiar link directo — criterio: enlace directo al producto */}
                <div className="share-modal__copy-row">
                    <span className="share-modal__copy-url">{productUrl}</span>
                    <button
                        className={`share-modal__copy-btn ${copied ? 'share-modal__copy-btn--copied' : ''}`}
                        onClick={handleCopyLink}
                    >
                        {copied ? '✓ Copiado' : 'Copiar link'}
                    </button>
                </div>

                {/* feedback cuando se copia para Instagram — explica que debe pegarlo manualmente */}
                {copied && (
                    <p className="share-modal__copy-feedback">
                        ¡Link copiado! Podés pegarlo en tu historia o bio de Instagram.
                    </p>
                )}

            </div>
        </div>
    )
}

export default ShareModal