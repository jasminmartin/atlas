import React, { useEffect, useRef, ReactNode } from "react";
import ReactDOM from "react-dom"

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTimes } from "@fortawesome/free-solid-svg-icons"


type ModalProps = {
    title?: string,
    children: ReactNode,
    onClose: () => void,
}

const capitalizeFirstLetter = ([first, ...rest]: string) => first.toLocaleUpperCase(navigator.language) + rest.join("")

const Modal = ({ children, title, onClose }: ModalProps) => {
    const thisElement = useRef<HTMLDivElement | null>(null)

    if (!thisElement.current) {
        thisElement.current = document.createElement("div")
    }

    useEffect(() => {
        const element = document.getElementById("Modal")
        if (thisElement.current && element) {
            element.appendChild(thisElement.current)
        }
        return function cleanUp() {
            thisElement.current && element?.removeChild(thisElement.current)
        }
    }, [])

    return thisElement.current ? ReactDOM.createPortal(
        <div className="ModalBackground" onClick={onClose}>
            <div className="ModalContent" onClick={e => e.stopPropagation()}>
                <div style={{ display: "flex" }}>
                    <h3 style={{ margin: 0, flex: 10, textAlign: "center" }}>{capitalizeFirstLetter(title || "")}</h3>
                    <FontAwesomeIcon icon={faTimes} onClick={onClose} />
                </div>
                {children}
            </div>
        </div>,
        thisElement.current) : null
}

export default Modal