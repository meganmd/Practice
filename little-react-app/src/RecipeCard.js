import React, { Component } from 'react';
import IngredientList from './IngredientList.js';
import './App.css';

class RecipeCard extends Component {
  render() {
    return (
      <div className="RecipeCard">
        <div className="RecipeCard-header">
          <h2>{this.props.recipe.name}</h2>
        </div>
        <IngredientList ingredientList={this.props.recipe.ingredientList}/>
      </div>
    );
  }
}

export default RecipeCard;
