import React, { Component } from 'react';
import './App.css';
import Client from './Client.js';

class AddUserCard extends Component {
  constructor(props) {
    super(props);
    this.state = {username : "hello", password : "world"};
    this.handleChange = this.handleChange.bind(this);
    this.addUser = this.addUser.bind(this);
  }

  handleChange(event) {
    //console.log(event.target);
    this.setState({username : event.target.value});
  }

  addUser() {
    console.log("adding");
    Client.addUser(this.state.username, this.state.password, function(){});
  }

  render() {
    return (
      <div className="AddUserCard">
        <div>
          Username:
          <input type="text" value={this.state.username} onChange={this.handleChange}/>
          Password:
          <input/>
          <button onClick={this.addUser}>Add</button>
        </div>
      </div>
    );
  }
}

export default AddUserCard;
