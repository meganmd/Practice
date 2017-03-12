import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Pie from './Pie.js';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {sector1:30, sector2:10, sector3:10, sector4:10 };
    this.handleChange = this.handleChange.bind(this);
  }
  handleChange(e) {
    const name = e.target.name;
    var value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    if(e.target.type === 'number') {
      value = parseFloat(value);
    }
    this.setState({[name]:value});
  }
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <Pie r={100} includeKey="true" slices={[
          {value:this.state.sector1, color:"red", name:"Bob"},
          {value:this.state.sector2, color:"orange", name:"Charlie"},
          {value:this.state.sector3, color:"yellow", name:"Fred"},
          {value:this.state.sector4, color:"green", name:"Bill"}
        ]}/>
        <Pie r={100} includeKey={false} slices={[
          {value:60, color:"lightblue", name:"thing"},
          {value:40, color:"transparent", name:"not"}
        ]}/>
        <div>
          <input type="number" name="sector1" value={this.state.sector1} onChange={this.handleChange}/>
          <input type="number" name="sector2" value={this.state.sector2} onChange={this.handleChange}/>
          <input type="number" name="sector3" value={this.state.sector3} onChange={this.handleChange}/>
          <input type="number" name="sector4" value={this.state.sector4} onChange={this.handleChange}/>
        </div>

      </div>
    );
  }
}

export default App;
