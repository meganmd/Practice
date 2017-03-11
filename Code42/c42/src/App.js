import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Code42MembersList from './Code42MembersList.js';

class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>Code42 Githubs</h2>
        </div>
      <Code42MembersList/>
      </div>
    );
  }
}

export default App;
