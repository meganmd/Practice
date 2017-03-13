import React, { Component } from 'react';
import './App.css';
import './Pie.css';

class Slice extends Component {
  constructor(props) {
    super(props);
    this.state = {load:0};
    this.getCoordinates = this.getCoordinates.bind(this);
  }


  getCoordinates(r, a, q, t) { //radius, angle/\(past quantity), quantity, total
    //console.log([r, a, q, t]);
    return ({
      start:{
        x:(r * Math.cos((2 * Math.PI * (a/t)) * this.state.load-(Math.PI/2)) + r*1.5),
        y:(r * Math.sin((2 * Math.PI * (a/t)) * this.state.load-(Math.PI/2)) + r*1.5)
      },
      end:{
        x:(r * Math.cos((2 * Math.PI * ((q+a)/t)) * this.state.load-(Math.PI/2)) + r*1.5),
        y:(r * Math.sin((2 * Math.PI * ((q+a)/t)) * this.state.load-(Math.PI/2)) + r*1.5)
      }
    });
  }
  increment() {
    if(this.state.load < 1) {
      this.setState({load:this.state.load + .04});
    }
  }

  componentDidMount() {
    /*if(this.state.load < .5) {
      console.log("hi")
      this.increment();
      setTimeout(
        this.componentDidMount(),
        6000
      )
    }*/
    this.timerID = setInterval(
      () => this.increment(),
      15
    );
  }

  render() {
    var coordinates = this.getCoordinates(this.props.r, this.props.a, this.props.quantity, this.props.total)
    //console.log(coordinates);
    var path = [
      'M', this.props.r*1.5, this.props.r*1.5,
      'L', coordinates.start.x, coordinates.start.y,
      'A',  this.props.r, this.props.r, 0,
            (this.props.quantity/this.props.total > .5 ? 1 : 0), 1,
            coordinates.end.x, coordinates.end.y,
      'Z'
    ].join(' ');

    return(
      <path className="Slice" style={{"transformOrigin":this.props.r*1.5 + "px " + this.props.r*1.5 + "px"}}
        fill={this.props.color}
        d={path}
      />
    )
  }
}

function Key(props) {
  var total = 0;
  for(var i = 0; i < props.slices.length; i++) {
    total +=props.slices[i].quantity;
  }
  return(
    <g>
      {props.slices.map((slice, i) => {
        return(
          <g key={i}>
            <rect x={props.startX + 10}
              y={props.startY + props.height/2 - (props.slices.length * 40 / 2) + i*40}
              height={10} width={10} fill={slice.color}/>
            <text x={props.startX + 30}
              y={props.startY + props.height/2 - (props.slices.length * 40 / 2) + i*40 + 10}
              >
                {Math.round(slice.quantity/total*100)}{'% '}{slice.name}
              </text>
          </g>
        )
      })}
    </g>
  )
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
    //console.log(slices);
    return (
      <svg width={this.props.includeKey ? this.props.r*3+200 : this.props.r*3} height={this.props.r*3}>
        {slices.map(function(element, i){
          return(
            <Slice key={i} color={element.color} quantity={element.quantity} r={element.r} a={element.a} total={a}/>
          )
        })}
        {this.props.includeKey ?
          <Key slices={slices} height={this.props.r*3} startX={300} startY={0}/>

      : ""}
      </svg>
    );
  }
}

export default Pie;
