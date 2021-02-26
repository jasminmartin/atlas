import React, { useEffect, useState } from "react";
import logo from './logo.svg';
import './App.css';
import Graph, { Node, Edge } from './app/sections/Graph';
import DataScraper from "./DataScraper";

const baseURL = "http://localhost:4024"

type ServerEdge = {
  firstNode: string
  secondNode: string
}

type ServerGraph = {
  edges: Array<ServerEdge>
  nodes: Array<string>
}




// function NetTemplate(graphBits: Graph) {
//   const EDGES = graphBits.edges.map(edge => { from: edge.firstNode, to: edge.secondNode });

//   const NODES = graphBits.nodes.map(node => { id: node, label: node, width: 144, height: 100 });
// }

const NODES: Node[] = [
  { id: 'sofa', label: 'sofa', width: 144, height: 100 },
  { id: 'chair', label: 'chair', width: 160, height: 100 },
  { id: 'potato', label: 'potato', width: 108, height: 100 },
  { id: 'furniture', label: 'furniture', width: 108, height: 100 },
];

const EDGES: Edge[] = [
  { from: 'sofa', to: 'potato' },
  { from: 'chair', to: 'potato' },
  { from: 'sofa', to: 'furniture' },
  { from: 'chair', to: 'furniture' }
];

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
  //return <DataScraper />
}

export default App;
