import React, { Component } from 'react';
import RepositoryList from './RepositoryList.js';
import './App.css';

class MemberCard extends Component {
  constructor(props) {
    super(props);
    this.state = {repos:[]};
    this.getRepos = this.getRepos.bind(this);
  }
  getRepos() {
    return fetch(this.props.member.repos_url, {
      accept: 'application/json',
    }).then((response) =>  response.json())
    .then((response) => this.setState({repos: response}));
  }

  componentWillMount() {
    this.getRepos();
  }

  render() {
    return (
      <div className='MemberCard'>
        <div className='identity'>
          <img src={this.props.member.avatar_url}></img>
          <div>
            <a href={this.props.member.html_url}>{this.props.member.login}</a>
          </div>
        </div>
        <RepositoryList repos={this.state.repos}/>
      </div>
    );
  }

}

export default MemberCard;
