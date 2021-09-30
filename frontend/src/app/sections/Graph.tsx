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
  const currentGraph = new dagre.graphlib.Graph();
  
  buildGraph(currentGraph, nodes, edges);

  const [zoomFactor, setZoomFactor] = useState(1.0)
  const [xOffset, setXOffSet] = useState(0)
  const [yOffset, setYOffSet] = useState(0)


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


  return (
    <>
      {/* <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}> */}
        <svg viewBox="0 0 1000 1000" style={{width: 1000, height: 1000}} >
          {currentGraph.edges().map((edge) => {
            const fromNode = currentGraph.node(edge.v);
            const toNode = currentGraph.node(edge.w);
            return (
              <line
                x1={(fromNode.x * zoomFactor) + (1 - zoomFactor) * xOffset}
                y1={(fromNode.y * zoomFactor) + (1 - zoomFactor) * yOffset}
                x2={(toNode.x * zoomFactor) + (1 - zoomFactor) * xOffset}
                y2={(toNode.y * zoomFactor) + (1 - zoomFactor) * yOffset}
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
                  cx={(node.x * zoomFactor) + (1 - zoomFactor) * xOffset}
                  cy={(node.y * zoomFactor) + (1 - zoomFactor) * yOffset}
                  r={nodeWidth / 2}>
                </circle>
                <foreignObject
                  onClick={() => setLastClicked(node.label)}
                  x={(node.x * zoomFactor) + (1 - zoomFactor) * xOffset - (nodeWidth / 4)}
                  y={(node.y * zoomFactor)+ (1 - zoomFactor) * yOffset  - (nodeWidth / 4)}
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
