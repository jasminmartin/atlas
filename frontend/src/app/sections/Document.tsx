import React from "react"




type Props = {
    name: string
    body: string
}


const Document = ({body}: Props) => 
        <div>
            <textarea style={{ minHeight: "250px", width: "100%", resize: "none" }}>{body}</textarea>
        </div >

export default Document