import React from "react";

import Post from "./components/Post";

import "./App.scss";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Instagram++</h1>
        <Post />
      </header>
    </div>
  );
}

export default App;
