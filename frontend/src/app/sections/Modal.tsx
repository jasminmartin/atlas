import React, { useEffect, useRef, ReactNode } from "react";
import ReactDOM from "react-dom"

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit, faTimes } from "@fortawesome/free-solid-svg-icons"


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
        <div className="ModalBackground" style={{zIndex: 1}}onClick={onClose}>
            <div className="ModalContent" style={{
                width: "25%", height: "62%", paddingRight: "3%"}} onClick={e => e.stopPropagation()}>
                <div style={{ display: "flex" }}>
                    <FontAwesomeIcon icon={faTimes} onClick={onClose} />
                </div>
                {children}
            </div>
        </div>,
        thisElement.current) : null
}

export default Modal