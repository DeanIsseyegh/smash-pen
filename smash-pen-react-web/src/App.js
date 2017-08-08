import React, {Component} from 'react';
import logo from './smashball.svg';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

import {Route, Link} from 'react-router-dom'
import CharacterList from "./CharacterList";
import PlayerList from "./PlayerList";
import Main from "./Main";
import EditCharacter from "./EditCharacter";

class App extends Component {
	constructor(props) {
		super(props);
		this.setChar = this.setChar.bind(this);
		this.state = { char: '' };
	}

	setChar(char) {
		this.setState({ char: char });
	}

	render() {
		return (
			<div className="App">
				<div className="App-header">
					<img src={logo} className="App-logo" alt="logo"/>
					<h2>Welcome to SmashPen</h2>
				</div>
				<p className="App-intro">
					<Link to="/player"><button className="pure-button">Players</button></Link>
					<Link to="/"><button className="pure-button">Main</button></Link>
					<Link to="/character"><button className="pure-button">Characters</button></Link>
				</p>

				<Route path="/player" component={PlayerList}/>
				<Route exact path="/" component={Main}/>
				<Route exact path="/character" component={(props) => <CharacterList {...props} onEditChar={this.setChar}/> }/>
				<Route exact path="/character/edit" component={(props) => <EditCharacter {...props} char={this.state.char}/>} />

			</div>
		);
	}
}

export default App;
