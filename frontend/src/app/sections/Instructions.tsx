import React, { useState } from "react"
import { putFile, deleteFile } from "./useFileFetch"
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'

library.add(fas)

type Props = {}

const Instructions = ({ }: Props) => {

    return (
        <div style={{ justifyContent: "flex" }}>
            <h1 style={{ minHeight: "10px", width: "100%", resize: "none", display: "block", margin: "2%", fontSize: '120%', fontFamily: "system-ui" }}>
                Welcome to Atlas
            </h1>
            <div style={{ fontFamily: "system-ui", fontSize: "small"}}>
            <p >
            Atlas is a browser application that converts file systems into visual structures.
            </p>
            <p>
            Files are represented by each node in the Atlas Web.
            Files are linked by tagged keywords.
            Click on a file to see its content
            </p>
            </div>
        </div>
    )
}

export default Instructions