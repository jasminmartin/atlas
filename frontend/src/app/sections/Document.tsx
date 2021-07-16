import React, { useState } from "react"
import { putFile, deleteFile } from "./useFileFetch"



type Props = {
    name: string
    body?: string
}


const save = (oldName: string, newName: string, body: string) => {
    deleteFile(oldName)
    putFile(newName, body)
    console.log(body)
}

const Document = ({ body = "", name: oldName }: Props) => {
    const [content, setContent] = useState(body)
    const [newName, setTitle] = useState(oldName)

    return (
        <form onSubmit={e => {
            save(oldName, newName, content)
        }}>
            <textarea value={newName} onChange={e => setTitle(e.target.value)}
                style={{ minHeight: "30px", width: "65%", resize: "none" }} />
            <textarea value={content} onChange={e => setContent(e.target.value)}
                style={{ minHeight: "250px", width: "100%", resize: "none" }} />
            <input type="submit" value="Submit" />
        </form>
    )
}

export default Document