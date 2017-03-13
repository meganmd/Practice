import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {Pie, PercentPie} from './Pie.js';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {sector1:30, sector2:10, sector3:10, sector4:80 };
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
        <Pie title="STUFF" r={100} includeKey="true" slices={[
          {value:this.state.sector1, color:"red", name:"Bob"},
          {value:this.state.sector2, color:"orange", name:"Charlie"},
          {value:this.state.sector3, color:"yellow", name:"Fred"},
          {value:this.state.sector4, color:"green", name:"Bill"}
        ]}/>
        <PercentPie r={100} percent={this.state.sector4} color="lightblue"/>
        <PercentPie r={20} percent={60} color="lightgreen"/>
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
