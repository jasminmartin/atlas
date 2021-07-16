import React, { useEffect, useState } from "react";
import './App.css';
import Graph, { Node, Edge } from './app/sections/Graph';
import Button from './app/sections/Button';
import Modal from "./app/sections/Modal";
import Document from "./app/sections/Document";

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

  const [isOpen, setIsOpen] = useState<Boolean>(false)

  useEffect(() => {
    async function f(): Promise<void> {
      const resp = await fetch(`${baseURL}/local-link`)
      const body = await resp.json()
      setGraph(body)
    }
    f()
  }, [baseURL, setGraph])


  return (
  <>
  {isOpen && <Modal title="New File" onClose={()=>setIsOpen(false)}>
  <Document body="new body" name="new name"/>
  </Modal>}
  <Graph nodes={graph.nodes.map(name => ({ id: name, label: name, width: 100, height: 100 }))} edges={graph.edges.map(edge => ({
    from: edge.firstNode,
    to: edge.secondNode,
  }))} />
  <Button buttonName="Create New File" onClick={()=>setIsOpen(true)}/>
  </>);
}

export default App;
