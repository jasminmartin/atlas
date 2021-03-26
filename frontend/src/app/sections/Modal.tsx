import React, { useEffect, useRef, ReactNode } from "react";
import ReactDOM from "react-dom"


type ModalProps = {
    children: ReactNode,
    onClose: () => void,
}

const Modal = ({ children, onClose }: ModalProps) => {
    const thisElement = useRef<HTMLDivElement | null>(null)
    useEffect(() => {
        if (!thisElement.current) {
            thisElement.current = document.createElement("div")
        }
        const element = document.getElementById("Modal")
        if (element) {
            element.appendChild(thisElement.current)
        }
        return function cleanUp() {
            thisElement.current && element?.removeChild(thisElement.current)
        }
    }, [])
    return thisElement.current ? ReactDOM.createPortal(
        <div className="ModalBackground" onClick={onClose}>
            <div className="ModalContent">
                {children}
            </div>
        </div>,
        thisElement.current) : null
}

export default Modal