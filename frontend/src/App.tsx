import React from 'react';
import logo from './logo.svg';
import './App.css';
import FileCircle from './app/sections/FileCircle';
import LineConnection from './app/sections/LineConnection';

function App() {
  return (
    <div>
      <FileCircle fileName="hi"
        leftDelta={20}
        rightDelta={40}
        bottomDelta={39}
        topDelta={30} />

      <LineConnection angle="10"
        leftDelta={20}
        rightDelta={40}
        bottomDelta={39}
        topDelta={30} />

      <LineConnection angle="123"
        leftDelta={48}
        rightDelta={23}
        bottomDelta={56}
        topDelta={12} />

    </div>
  );
}

export default App;
