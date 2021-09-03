import React, { useState } from "react"
import { putFile, deleteFile } from "./useFileFetch"
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'

library.add(fas)

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
        <form style={{ justifyContent: "flex" }} onSubmit={e => {
            save(oldName, newName, content)
        }
        }>
            <textarea value={newName} onChange={e => setTitle(e.target.value)}
                style={{ minHeight: "10px", width: "100%", resize: "none", display: "block", margin: "2%", fontSize: '120%', fontFamily: "system-ui" }} />
            <textarea value={content} onChange={e => setContent(e.target.value)}
                style={{ minHeight: "250px", width: "100%", resize: "none", display: "block", margin: "2%", fontFamily: "system-ui" }} />
            <FontAwesomeIcon icon={faTrashAlt} onClick={() => {
                deleteFile(newName);
                window.location.reload();
            }} />
            <input type="submit" value="Submit"
                style={{ marginLeft: "82%" }}
            />
        </form>
    )
}

export default Document