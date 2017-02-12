import React, { Component } from 'react';
import RecipeCard from './RecipeCard.js';
import squiggle from './squiggle.svg';
import './App.css';

class App extends Component {
  render() {
    const recipes = [
      {
        "name" : "Cake",
        "ingredientList" : [
          {
            "amount" : "3.5 cups",
            "name" : "Sugar"
          },
          {
            "amount" : "3",
            "name" : "Eggs"
          },
          {
            "amount" : "3 cups",
            "name" : "Flour"
          }
        ],
        "directions" : "Cook cake"
      },
      {
        "name" : "Pie",
        "ingredientList" : [
          {
            "amount" : "3.5 cups",
            "name" : "Sugar"
          },
          {
            "amount" : "3",
            "name" : "Eggs"
          },
          {
            "amount" : "3 cups",
            "name" : "Flour"
          }
        ],
        "directions" : "Cook pie"
      }
    ];
    return (
      <div className="App">
        <div className="App-header">
          <ul className="HeaderList">
            <li><img src={squiggle} className="Squiggle" alt="squiggle" /></li>
            <li><h1>My Recipes</h1></li>
          </ul>
        </div>
        <ul className="RecipeList">
          {recipes.map(function(recipe, i) {
            return (
              <li><RecipeCard recipe={recipe}/></li>
            );
          })}
        </ul>
      </div>
    );
  }
}

export default App;
