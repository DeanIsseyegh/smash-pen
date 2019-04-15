import React, {Component} from 'react';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

import {Route} from 'react-router-dom'
import CharacterList from "./Character/CharacterList";
import PlayerList from "./PlayerList";
import Main from "./Main";
import EditCharacter from "./Character/EditCharacter";
import Switch from "react-router-dom/es/Switch";
import {Loading} from "./Loading";
import {MainNav} from "./MainNav";
import {fetchGetInit, fetchPostInit, fetchPutInit} from "./FetchUtil";

class App extends Component {
	constructor(props) {
		super(props);
		this.setSelectedChar = this.setSelectedChar.bind(this);
		this.updateCharData = this.updateCharData.bind(this);
        this.mergeSingleCharDataWithFullCharData = this.mergeSingleCharDataWithFullCharData.bind(this);
		this.onCharAdd = this.onCharAdd.bind(this);
		this.onLogIn = this.onLogIn.bind(this);
		this.state = { data: {}, selectedChar: "", isLoggedIn: false, token: "" };
	}

	setSelectedChar(charData) {
		this.setState({ selectedChar: charData });
	}

    mergeSingleCharDataWithFullCharData(charData, fullCharData) {
        const newData = fullCharData;
        fullCharData.forEach( function(it, idx) {
            if (it.id == charData.id) {
                newData[idx] = charData;
            }
        });
        return newData;
    }

	updateCharData(charData) {
        console.log(charData);
        console.log(this.state.data);
		fetch('http://localhost:8080/1/character', fetchPutInit(charData))
			.then(response => {
                const newData = this.mergeSingleCharDataWithFullCharData(charData, this.state.data);
        		this.setState({ data: newData });
            });
	}

	onLogIn(username, password) {
		this.setState({showSpinner: true});
		fetch('http://localhost:8080/login', {
			method: 'POST',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({"username":username,"password":password})
		})
		.then(response => {
				if (response && response.headers.get('authorization')) {
					localStorage.setItem('token', response.headers.get('authorization'));
					this.setState({showSpinner: false, isLoggedIn: true});
				} else {
					throw "Login failed"
				}
			}
		)
		.then(() => fetch("http://localhost:8080/1/character", fetchGetInit()))
		.then(response => response.json())
		.then(json => this.setState({data: json}))

		.catch(err =>
			this.setState({showSpinner: false, isLoggedIn: false})
		)
	}

	onCharAdd() {

	}

	render() {
		const { isLoggedIn } = this.state;
		return (
			<div className="App">
				<MainNav isLoggedIn={isLoggedIn}/>
				{this.state.showSpinner ? <Loading/> :
					<Switch>
						<Route path="/player" component={PlayerList}/>

						<Route exact path="/" render={(props) => <Main
							{...props}
							onLogIn={this.onLogIn}
							isLoggedIn={isLoggedIn}/>}
						/>

						{this.state.isLoggedIn &&
						<Route exact path="/character" render={(props) => <CharacterList
							{...props}
							onEditChar={this.setSelectedChar}
							data={this.state.data}/> }
						/>}

						{this.state.isLoggedIn &&
						<Route exact path="/character/edit" render={(props) =>
							<EditCharacter
							{...props}
							charData={this.state.selectedChar}
							updateCharData={this.updateCharData}
							handleCharChange={this.handleCharUpdate}
							successCharMsg={this.state.successCharMsg}/>}
						/>}
					</Switch>
				}
			</div>
		);
	}


}



export default App;
