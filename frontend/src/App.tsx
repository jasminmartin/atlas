import React, { useEffect, useState } from "react";
import './App.css';
import Graph, { Node, Edge } from './app/sections/Graph';

const baseURL = "http://localhost:4024"

type ServerEdge = {
  firstNode: string
  secondNode: string
}

type ServerGraph = {
  edges: Array<ServerEdge>
  nodes: Array<string>
}

interface AppProps { }

function App({ }: AppProps) {

  const [graph, setGraph] = useState<ServerGraph>({
    nodes: [],
    edges: [],
  })


  useEffect(() => {
    async function f(): Promise<void> {
      const resp = await fetch(`${baseURL}/local-link`)
      const body = await resp.json()
      setGraph(body)
    }
    f()
  }, [baseURL, setGraph])


  return <Graph nodes={graph.nodes.map(name => ({ id: name, label: name, width: 100, height: 100 }))} edges={graph.edges.map(edge => ({
    from: edge.firstNode,
    to: edge.secondNode,
  }))} />;
}

export default App;
