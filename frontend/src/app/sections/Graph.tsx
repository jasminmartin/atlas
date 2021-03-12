import dagre from 'dagre';
import useFileFetch from "./useFileFetch"
import React, { useEffect, useState } from "react";

type NodeId = string;

export interface Node {
  id: NodeId;
  label: string;
  width?: number,
  height?: number,
}

export interface Edge {
  from: NodeId;
  to: NodeId;
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

type FileBodyProps = {
  fileName: string
}

const Loading = () => <p>Loading...</p>

const FileBody = ({ fileName }: FileBodyProps) => {
  const { body, isPending } = useFileFetch(fileName)
  return isPending ? (<Loading />) : <p>{JSON.stringify(body)}</p>
}

export const Graph = ({ nodes, edges }: GraphProps) => {
  const currentGraph = new dagre.graphlib.Graph();

  buildGraph(currentGraph, nodes, edges);
  const nodeWidth = 100
  const nodePadding = 8

  const [lastClicked, setLastClicked] = useState<string | undefined>(undefined)

  return (
    <>
      <svg viewBox="0 0 1000 1000">
        {currentGraph.edges().map((edge) => {
          console.dir(edge);
          const fromNode = currentGraph.node(edge.v);
          const toNode = currentGraph.node(edge.w);
          return (
            <line
              x1={fromNode.x}
              y1={fromNode.y}
              x2={toNode.x}
              y2={toNode.y}
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
                style={{ fill: "pink" }}
                // onClick={() => FileBody(node.label || "")}
                onClick={() => setLastClicked(node.label)}
                cx={node.x}
                cy={node.y}
                r={nodeWidth / 2}>
              </circle>
              <text textLength={nodeWidth - nodePadding * 2} x={node.x - (nodeWidth / 2) + nodePadding} y={node.y}>
                {node.label}
              </text>

            </>
          ))}
      </svg>
      {lastClicked && <FileBody fileName={lastClicked} />}
    </>
  );
};

export default Graph;
