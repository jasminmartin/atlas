import React, { useState } from "react"
import { putFile } from "./useFileFetch"



type Props = {
    name: string
    body: string
}


const save = (name: string, body: string) => {
    putFile(name, body)
    console.log(body)

}

const Document = ({ body, name }: Props) => {
    const [content, setContent] = useState(body)


    return (
        <form onSubmit={e => {
            save(name, content)
        }}>
            <textarea value={content} onChange={e => setContent(e.target.value)}
                style={{ minHeight: "250px", width: "100%", resize: "none" }} />
            <input type="submit" value="Submit" />
        </form>
    )
}

export default Document