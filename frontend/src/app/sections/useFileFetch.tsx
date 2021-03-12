import React, { useEffect, useState } from "react";

const baseURL = "http://localhost:4024"

type Body = {
    name: string
    body: string
}

function useFileFetch(fileName: string) {

    const [body, setBody] = useState<Body | undefined>(undefined)

    useEffect(() => {
        async function f(file: string): Promise<void> {
            const resp = await fetch(`${baseURL}/file-body/${file}`)
            const body = await resp.json()
            setBody(body)
        }
        f(fileName)
    }, [fileName, baseURL, setBody])

    return {
        body,
        isPending: body == undefined
    }
}
export default useFileFetch;
