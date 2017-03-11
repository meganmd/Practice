import React, { Component } from 'react';
import './App.css';

class RepositoryList extends Component {
  constructor(props) {
    super(props);
    //this.state = {repos:''};
    //this.getRepos = this.getRepos.bind(this);
  }


  componentWillMount() {
    //this.getRepos();
  }

  render() {
    return (
      <ul className='RepositoryList'>
        {this.props.repos.map(function(repo, i) {
          return <li key={i}><a href={repo.html_url}>{repo.name}</a></li>
        })}
      </ul>
    );
  }

}

export default RepositoryList;
