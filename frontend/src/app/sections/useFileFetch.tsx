import { useEffect, useState } from "react";

const baseURL = "http://localhost:4024"

export type File = {
    name: string
    body: string
}

export const fetchFile = async (fileName: string) => {
    const resp = await fetch(`${baseURL}/file-body/${fileName}`, {
        method: 'GET'
    })
    const body = await resp.json()
    return body
}

export const putFile = async (fileName: string, body: String) => {
    const put = await fetch(`${baseURL}/file-body/${fileName}`, {
        method: 'POST',
        headers:
            new Headers({ 'Content-Type': 'application/json' })
        ,
        body: JSON.stringify({
            name: fileName,
            body
        })
    })
    return put
}
