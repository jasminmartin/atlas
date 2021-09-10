import dagre from 'dagre'
import React, { useEffect, useState } from "react"
import Modal from "./Modal"

import Document from "./Document"
import { fetchFile, File } from "./useFileFetch"

type NodeId = string

export interface Node {
  id: NodeId,
  label: string,
  width?: number,
  height?: number,
}

export interface Edge {
  from: NodeId,
  to: NodeId,
}

function buildGraph(g: dagre.graphlib.Graph, nodes: Node[], edges: Edge[]) {
  // Set an object for the graph label
  g.setGraph({});

  // Default to assigning a new object as a label for each new edge.
  g.setDefaultEdgeLabel(function () {
    return {};
  });

  // Add nodes to the graph. The first argument is the node id. The second is
  // metadata about the node. In this case we're going to add labels to each of
  // our nodes.
  nodes.forEach((node) => g.setNode(node.id, node));
  edges.forEach((edge) => g.setEdge(edge.from, edge.to));

  dagre.layout(g);

  return g;
}

type GraphProps = {
  nodes: Node[];
  edges: Edge[];
};


const Loading = () => <p>Loading...</p>

export const Graph = ({ nodes, edges }: GraphProps) => {
  console.log("Rendering Graph")
  const currentGraph = new dagre.graphlib.Graph();
  
  buildGraph(currentGraph, nodes, edges);

  const [zoomFactor, setZoomFactor] = useState(1.0)
  const [xOffset, setXOffSet] = useState(0)
  const [yOffset, setYOffSet] = useState(0)

  const handlWheel = event => {
    console.dir(event)
    event.preventDefault()
    const {pageX, pageY} = event

    
    const newFactor = zoomFactor + event.deltaY * - 0.01
    setZoomFactor(Math.min(Math.max(.125, newFactor), 4))
  }

  const nodeWidth = 100 * zoomFactor
  const nodePadding = nodeWidth / 10
  const [lastClicked, setLastClicked] = useState<string | undefined>(undefined)
  const [fetch, setFetch] = useState<File | undefined>(undefined)

  useEffect(() => {
    if (!lastClicked) {
      if (fetch)
        setFetch(undefined)
      return
    }
    const f = async () => {
      setFetch(await fetchFile(lastClicked))
    }
    f()
  }, [lastClicked])

  console.dir(fetch)
  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }} onWheel={handlWheel}>
        <svg viewBox="0 0 1000 1000" >
          {currentGraph.edges().map((edge) => {
            const fromNode = currentGraph.node(edge.v);
            const toNode = currentGraph.node(edge.w);
            return (
              <line
                x1={(fromNode.x * zoomFactor) + xOffset}
                y1={fromNode.y * zoomFactor}
                x2={toNode.x * zoomFactor}
                y2={toNode.y * zoomFactor}
                stroke="black"
              />
            );
          })}
          {currentGraph
            .nodes()
            .map((id) => currentGraph.node(id))
            .map((node) => (
              <>
                <circle
                  style={circleStyle}
                  cx={node.x * zoomFactor}
                  cy={node.y * zoomFactor}
                  r={nodeWidth / 2}>
                </circle>
                <foreignObject
                  onClick={() => setLastClicked(node.label)}
                  x={node.x * zoomFactor - (nodeWidth / 4)}
                  y={node.y * zoomFactor - (nodeWidth / 4)}
                  width={nodeWidth / 2}
                  height={nodeWidth / 2}>
                  <div
                    onClick={() => setLastClicked(node.label)}
                     style={{
                      width: '100%',
                      height: '100%',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      cursor: "pointer",
                    }}>
                    <label style={{
                      cursor: "pointer",
                      fontSize: `${zoomFactor * 60}%`,
                      overflow:"hidden",
                      whiteSpace:"nowrap",
                      textOverflow:"ellipsis"
                    }}
                    >{node.label}</label>
                  </div>
                </foreignObject>
              </>
            ))}
        </svg>
        {lastClicked && (
          <Modal title={lastClicked} onClose={() => setLastClicked(undefined)}>
            {
              fetch ? <Document key={1} {...fetch} /> : <Document key={0} name={lastClicked} />
            }
          </Modal>
        )}
      </div>
    </>
  );
};

export default Graph;

const circleStyle = {
  fill: "#00B2ED",
  border: "none",
  color: "white",
  textAlign: "center" as const,
  textDecoration: "none",
  display: "flex",
  fontSize: "16px",
  cursor: "pointer",
  margin: "4px 2px",
  padding: "100px",
  borderRadius: "50"
};
