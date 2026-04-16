import { useState } from 'react'
import './ShareModal.css'

// construye las URLs de intención para cada red social
// Facebook: sharer.php solo acepta 'u' desde 2018 — el parámetro quote fue deprecado
// el texto aparece cuando la URL tiene Open Graph tags (og:title, og:description)
// en localhost no hay OG tags, en producción sí funcionaría con meta tags correctos
// Twitter/X: intent/tweet sí acepta text + url — funciona en localhost
// WhatsApp: wa.me acepta texto libre con link embebido
// Instagram: no tiene URL de intención pública — copiamos el link al portapapeles
const buildShareUrls = (url, title, description, customMessage) => {
    const text = customMessage.trim() || description
    const encodedUrl = encodeURIComponent(url)
    const encodedText = encodeURIComponent(`${text} — ${title}`)

    return {
        twitter: `https://twitter.com/intent/tweet?text=${encodedText}&url=${encodedUrl}`,
        // facebook solo acepta la url — el texto viene de los OG tags de la página
        facebook: `https://www.facebook.com/sharer/sharer.php?u=${encodedUrl}`,
        whatsapp: `https://wa.me/?text=${encodeURIComponent(`${text}\n\n${url}`)}`,
    }
}

const ShareModal = ({ isOpen, onClose, room }) => {

    const [customMessage, setCustomMessage] = useState('')
    const [copied, setCopied] = useState(false)

    if (!isOpen || !room) return null

    // construimos productUrl con window.location.origin para que sea correcto
    // tanto en localhost como en producción sin hardcodear el dominio
    const productUrl = `${window.location.origin}/rooms/${room.id}`
    const shortDescription = room.description?.slice(0, 120) + (room.description?.length > 120 ? '...' : '')

    const shareUrls = buildShareUrls(productUrl, room.name, shortDescription, customMessage)

    const openShare = (url) => {
        window.open(url, '_blank', 'width=600,height=500,noopener,noreferrer')
        onClose()
    }

    const handleCopyLink = async () => {
        // copiamos productUrl — la URL del producto construida arriba, no window.location.href
        // esto garantiza que siempre copiamos /rooms/{id} y no la URL actual del browser
        try {
            await navigator.clipboard.writeText(productUrl)
            setCopied(true)
            setTimeout(() => setCopied(false), 2000)
        } catch {
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
        <div className="share-modal__overlay" onClick={onClose}>
            <div className="share-modal__box" onClick={e => e.stopPropagation()}>

                <div className="share-modal__header">
                    <h2 className="share-modal__title">Compartir producto</h2>
                    <button className="share-modal__close" onClick={onClose} aria-label="Cerrar">✕</button>
                </div>

                {/* preview del producto — imagen + nombre + descripción corta + url */}
                <div className="share-modal__preview">
                    {room.images && room.images[0] ? (
                        <img src={room.images[0]} alt={room.name} className="share-modal__preview-img" />
                    ) : (
                        <div className="share-modal__preview-placeholder">🏨</div>
                    )}
                    <div className="share-modal__preview-info">
                        <p className="share-modal__preview-name">{room.name}</p>
                        <p className="share-modal__preview-desc">{shortDescription}</p>
                        <p className="share-modal__preview-url">{productUrl}</p>
                    </div>
                </div>

                {/* mensaje personalizado — criterio de aceptación explícito */}
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

                        {/* Instagram no tiene URL de intención — copiamos el link al portapapeles
                            el usuario puede pegarlo en su story, bio o DM manualmente */}
                        <button
                            className={`share-modal__network-btn share-modal__network-btn--instagram ${copied ? 'share-modal__network-btn--copied' : ''}`}
                            onClick={handleCopyLink}
                        >
                            <span className="share-modal__network-icon">📷</span>
                            {copied ? '¡Link copiado!' : 'Instagram'}
                        </button>

                    </div>

                    {/* feedback contextual solo para Instagram */}
                    {copied && (
                        <p className="share-modal__instagram-tip">
                            Pegá el link en tu historia o bio de Instagram
                        </p>
                    )}
                </div>

                {/* copiar link directo */}
                <div className="share-modal__copy-row">
                    <span className="share-modal__copy-url">{productUrl}</span>
                    <button
                        className={`share-modal__copy-btn ${copied ? 'share-modal__copy-btn--copied' : ''}`}
                        onClick={handleCopyLink}
                    >
                        {copied ? '✓ Copiado' : 'Copiar link'}
                    </button>
                </div>

            </div>
        </div>
    )
}

export default ShareModal