import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Client from './Client.js';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {users:[]};
  }
  getUsers() {
    Client.getUsers((users) => {
      this.setState({
        users: users,
      });
    });
  }

  render() {
    this.getUsers();
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          {this.state.users.map(function(user, i) {
            return (
              <li>{user.username}</li>
            );
          })}
        </p>
      </div>
    );
  }
}

export default App;
