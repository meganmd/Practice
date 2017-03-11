import React, { Component } from 'react';
import MemberCard from './MemberCard.js';
import './App.css';

class Code42MembersList extends Component {
  constructor(props) {
    super(props);
    this.state = {members:[]};
    this.getUsers = this.getUsers.bind(this);
  }
  getUsers() {
    return fetch("https://api.github.com/orgs/code42/public_members", {
      accept: 'application/json',
    }).then((response) =>  response.json())
    .then((response) => this.setState({members: response}));
  }

  componentWillMount() {
    this.getUsers();
  }

  render() {
    return (
      <ul className='Code42MembersList'>
        {this.state.members.map(function(member, i) {
          return <li key={i}><MemberCard member={member} /></li>
        })}
      </ul>
    );
  }

}

export default Code42MembersList;
