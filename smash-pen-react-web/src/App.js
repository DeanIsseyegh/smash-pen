import React, { Component } from 'react';
import logo from './smashball.svg';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

// react-dom (what we'll use here)
import { BrowserRouter, Route, Link } from 'react-router-dom'

class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to SmashPen</h2>
        </div>
        <p className="App-intro">

        </p>

			  <a class="pure-button" href="#">A Pure Button</a>
			  <button class="pure-button">A Pure Button</button>

			  <button class="pure-button"> <Link to="/brah">Visit the Brah</Link></button>
			  <Route path="/brah" component={NewPage}/>

      </div>
    );
  }
}

const NewPage = () =>
	<div>HiBrah</div>

export default App;
