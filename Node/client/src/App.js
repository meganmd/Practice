import React, { Component } from 'react';
import logo from './logo.svg';
import AddUserCard from './AddUserCard.js';
import './App.css';
import Client from './Client.js';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {users:[]};
    this.getUsers = this.getUsers.bind(this);
  }
  
  getUsers() {
    Client.getUsers((users) => {
      this.setState({
        users: users,
      });
    });
  }

  componentWillMount() {
    this.getUsers();
  }

  render() {
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
        <AddUserCard
          updateUsers={this.getUsers}
        />
      </div>
    );
  }
}

export default App;
