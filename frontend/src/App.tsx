import React from 'react';
import logo from './logo.svg';
import './App.css';
import Graph from './app/sections/Graph';
import DataScraper from './DataScraper'

const NODES = [
  { id: 'sofa', label: 'sofa', width: 144, height: 100 },
  { id: 'chair', label: 'chair', width: 160, height: 100 },
  { id: 'potato', label: 'potato', width: 108, height: 100 },
  { id: 'furniture', label: 'furniture', width: 108, height: 100 },
];

const EDGES = [
  { from: 'sofa', to: 'potato' },
  { from: 'chair', to: 'potato' },
  { from: 'sofa', to: 'furniture' },
  { from: 'chair', to: 'furniture' }
];

interface AppProps { }

function App({ }: AppProps) {
  return <DataScraper />;
}

export default App;
