import React, { useEffect, useState } from "react";

const baseURL = "http://localhost:4024"

type Edge = {
    firstNode: String
    secondNode: String
}

type Graph = {
    edges: Array<Edge>
    nodes: Array<String>
}

export default function DataScraper() {
    const [graphBits, setGraph] = useState({ edges: [], nodes: [] } as Graph);
    useEffect(() => {
        fetch(`${baseURL}/local-link`)
            .then(results => results.json())
            // .then(c => console.log(c))
            .then((graphBits: Graph) => {
                setGraph(graphBits)
            })
    }, [baseURL]);

    return (
        <div>
            <text>Zettelkasten Maybe?!</text>
            <div>{JSON.stringify(graphBits)}</div>
        </div>
    );
}
