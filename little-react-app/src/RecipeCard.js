import React, { Component } from 'react';
import IngredientList from './IngredientList.js';
import './App.css';

class RecipeCard extends Component {
  constructor(props) {
    super(props);
    this.state = {size:"small"};
  }

  render() {
    return (
      <div className="RecipeCard">
        <div className="RecipeCard-header" contenteditable="true">
          <h2>{this.props.recipe.name}</h2>
        </div>
        <IngredientList ingredientList={this.props.recipe.ingredientList}/>
      </div>
    );
  }
}

export default RecipeCard;
