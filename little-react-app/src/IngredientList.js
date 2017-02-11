import React, { Component } from 'react';
import './App.css';

class IngredientList extends Component {
  render() {
    return (
      <ul className="IngredientList">
        {this.props.ingredientList.map(function(ingredient) {
          return(
            <li>{ingredient.amount}  {ingredient.name}</li>
          )
        })}
      </ul>
    );
  }
}

export default IngredientList;
