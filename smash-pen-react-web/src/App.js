import React, {Component} from 'react';
import logo from './smashball.svg';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

import {Route, Link} from 'react-router-dom'
import CharacterList from "./Character/CharacterList";
import PlayerList from "./PlayerList";
import Main from "./Main";
import EditCharacter from "./Character/EditCharacter";

const data = [
	{ character: {name: "pikachu"}, notes: "kewl" },
	{ character: {name: "mario"}, notes: "Mario kkk"},
	{ character: {name: "bayonetta"}, notes: "Sexay"},
	{ character: {name: "jigglypuff"}, notes: ""},
	{ character: {name: "gannondorf"}, notes: ""}
];

class App extends Component {
	constructor(props) {
		super(props);
		this.setSelectedChar = this.setSelectedChar.bind(this);
		this.updateCharData = this.updateCharData.bind(this);
		this.handleCharChange = this.handleCharChange.bind(this);
		this.onCharAdd = this.onCharAdd.bind(this);
		this.state = { data, selectedChar: "" };
		fetch('http://localhost:8080/1/character', {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data[0])
		})
	}

	setSelectedChar(charData) {
		this.setState({ selectedChar: charData });
	}

	updateCharData(e) {
		e.preventDefault();
		//aja request to update data or something
		this.setState({ data });
	}

	handleCharChange(e) {
		var updatedData = data.map(it => it === this.state.selectedChar ? it.notes = e.target.value : it );
		this.setState({ updatedData });
	}

	onCharAdd() {

	}

	render() {
		console.log(this.state.successCharMsg);
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

				<Route exact path="/character" render={(props) => <CharacterList
					{...props}
					onEditChar={this.setSelectedChar}
					data={this.state.data}/> }
				/>

				<Route exact path="/character/edit" render={(props) =>
					<EditCharacter
								   charData={this.state.selectedChar}
								   updateCharData={this.updateCharData}
								   handleCharChange={this.handleCharChange}
								   successCharMsg={this.state.successCharMsg}/>}
				/>
			</div>
		);
	}


}



export default App;
