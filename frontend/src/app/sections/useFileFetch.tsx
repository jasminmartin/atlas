import { useEffect, useState } from "react";

const baseURL = "http://localhost:4024"

export type File = {
    name: string
    body: string
}

export const fetchFile = async (fileName: string) => {
    const resp = await fetch(`${baseURL}/file-body/${fileName}`)
    const body = await resp.json()
    return body
}

