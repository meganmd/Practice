import React, { Component } from 'react';
import './App.css';
import './Pie.css';

function Slice(props) {
  var coordinates = getCoordinates(props.r, props.a, props.quantity, props.total)
  console.log(coordinates);
  var path = [
    'M', props.r, props.r,
    'L', coordinates.start.x, coordinates.start.y,
    'A',  props.r, props.r, 0,
          (props.quantity/props.total > .5 ? 1 : 0), 1,
          coordinates.end.x, coordinates.end.y,
    'Z'
  ].join(' ');
  return(
    <path className="Slice"
      fill={props.color}
      d={path}
    />
  )
}

function Key(props) {
  return(
    <g>
      <rect x={props.r*2 + 10} y={30 + props.index*40} height={10} width={10} fill={props.color}/>
      <text x={props.r*2 + 30} y={40 + props.index*40}>{Math.round(props.quantity/props.total*100)}{'% '}{props.name}</text>
    </g>
  )
}

function getCoordinates(r, a, q, t) { //radius, angle/\(past quantity), quantity, total
  console.log([r, a, q, t]);
  return ({
    start:{
      x:(r * Math.cos(2 * Math.PI * (a/t)) + r),
      y:(r * Math.sin(2 * Math.PI * (a/t)) + r)
    },
    end:{
      x:(r * Math.cos(2 * Math.PI * ((q+a)/t)) + r),
      y:(r * Math.sin(2 * Math.PI * ((q+a)/t)) + r)
    }
  });
}

class Pie extends Component {
  constructor(props) {
    super(props);
    this.state = {width:50, height:50};
  }



  render() {
    var a = 0;
    var slices = [];
    for(var i = 0; i < this.props.slices.length; i++) {
      slices[i] = {
        quantity:this.props.slices[i].value,
        r:this.props.r,
        a:a,
        color:this.props.slices[i].color,
        name:this.props.slices[i].name,
      };
      a += this.props.slices[i].value;
    }
    console.log(slices);
    return (
      <svg width={this.props.includeKey ? this.props.r*2+200 : this.props.r*2} height={this.props.r*2}>
        {slices.map(function(element, i){
          return(
            <Slice key={i} color={element.color} quantity={element.quantity} r={element.r} a={element.a} total={a}/>
          )
        })}
        {this.props.includeKey ?
          slices.map(function(element, i) {
            return (
              <Key key={i} index={i} name={element.name} color={element.color} quantity={element.quantity} r={element.r} a={element.a} total={a}/>
            )
          }
        )
      : ""}
      </svg>
    );
  }
}

export default Pie;
