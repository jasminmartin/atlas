import React, { useEffect, useRef, ReactNode } from "react";
import ReactDOM from "react-dom"


type ModalProps = {
    children: ReactNode
}

const Modal = (props: ModalProps) => {
    const thisElement = useRef<HTMLDivElement | null>(null)
    useEffect(() => {
        if (!thisElement.current) {
            thisElement.current = document.createElement("div")
        }
        const element = document.getElementById("Modal")
        if (element) {
            element.appendChild(thisElement.current)
        }
    }, [])
    return thisElement.current ? ReactDOM.createPortal(
        <>
            {props.children}
        </>,
        thisElement.current) : null
}

export default Modal