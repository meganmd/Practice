import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class RecipeCard extends Component {
  render() {
    return (
      <div className="RecipeCard">
        <div className="RecipeCard-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>{this.props.title}</h2>
        </div>
        <ls className="IngredientList">
          <li>Ingredient one</li>
        </ls>
      </div>
    );
  }
}

export default App;
