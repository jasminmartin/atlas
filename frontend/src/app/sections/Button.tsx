import React from "react"
import { putFile } from "./useFileFetch"
import Modal from "./Modal"
import CSS from 'csstype';

type Props = {
    buttonName: string,
    onClick: () =>void
}

const newFile = () => {
    putFile("new file", "new body")
}
const Button = ({ onClick, buttonName }: Props) => {

    return (
        <button style ={buttonStyle} onClick={onClick}> {buttonName} </button> 
    )
}

export default Button;

const buttonStyle = {
        border: "none",
        color: "white",
        textAlign: "center" as const,
        textDecoration: "none",
        display: "flex",
        fontSize: "16px",
        cursor: "pointer",
        backgroundColor: "#008CBA",
        padding: "15px 32px",
        margin: "4px 2px",
        justifyContent: "flex-end"  };
