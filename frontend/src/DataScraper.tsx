import React, { useEffect, useState } from "react";

const baseURL = "http://localhost:4024"

type Edge = {
    firstNode: String
    secondNode: String
}

type Graph = {
    nodes: Array<String>
    edges: Array<Edge>
}

export default function DataScraper() {
    const [graphBits, setGraph] = useState([] as Graph[]);
    useEffect(() => {
        fetch(`${baseURL}/local-link`)
            .then(results => results.json())
            .then(x => console.log(x))
        // .then((graphBits: Graph[]) => {
        //     setGraph(graphBits)
        // })
    }, [baseURL]);

    return (
        <div>
            <text>Zettelkasten Maybe?!</text>
            {graphBits.map((graphBits: Graph) => <div>{graphBits.edges}</div>)}
        </div>
    );
}
