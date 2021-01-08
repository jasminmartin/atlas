import React from 'react';
import logo from './logo.svg';
import './App.css';
import FileCircle from './app/sections/FileCircle';

function App() {
  return (
    <div style={{ display: "flex" }}>
      <FileCircle fileName="hi"
        leftDelta={20}
        rightDelta={40}
        bottomDelta={39}
        topDelta={30} />
    </div>
  );
}

export default App;
