import React from "react"
import { putFile } from "./useFileFetch"
import Modal from "./Modal"
type Props = {
    buttonName: string,
    onClick: () =>void
}

const newFile = () => {
    putFile("new file", "new body")
    console.log("Created new file")
}
const Button = ({ onClick, buttonName }: Props) => {

    return (
        <button onClick={onClick}> {buttonName} </button> 
    )
}

export default Button;