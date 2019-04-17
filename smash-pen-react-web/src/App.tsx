import React, {Component} from 'react';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

import {Route, Switch} from 'react-router-dom'
import CharacterList from "./Character/CharacterList";
import PlayerList from "./PlayerList";
import Main from "./Main";
import EditCharacter from "./Character/EditCharacter";
import {Loading} from "./Loading";
import {MainNav} from "./MainNav";
import {fetchGetInit, fetchPostInit, fetchPutInit} from "./FetchUtil";

interface AppProps {
    onEditChar: OnEditChar
}

interface AppState {
    userId?: string;
    isLoggedIn: boolean;
    showSpinner: boolean;
    token: string;
    selectedChar: CharNotes
    userCharData: UserCharData
}

export type OnEditChar = (charNotes: CharNotes) => void;

export type UpdateCharData = (charData: CharNotes) => void;

export interface CharNotes {
    id?: number;
    smashCharacter: SmashCharacter;
    notes: string;
}

export interface SmashCharacter {
    id?: number;
    name: string
}

export interface UserCharData {
    charNotesList: CharNotes[]
}

class App extends Component<AppProps, AppState> {
	constructor(props: AppProps) {
		super(props);
		this.setSelectedChar = this.setSelectedChar.bind(this);
		this.updateCharData = this.updateCharData.bind(this);
        this.mergeSingleCharDataWithFullCharData = this.mergeSingleCharDataWithFullCharData.bind(this);
		this.onLogIn = this.onLogIn.bind(this);
		const emptyCharData: CharNotes = { smashCharacter: {id: 0, name: ''}, notes: '' };
		this.state = { userCharData: { charNotesList: []}, selectedChar: emptyCharData, isLoggedIn: false, token: "", showSpinner: false };
	}

	setSelectedChar(charData: CharNotes): void {
		this.setState({ selectedChar: charData });
	}

    mergeSingleCharDataWithFullCharData(charData: CharNotes, fullCharData: UserCharData): UserCharData {
        const newData = fullCharData.charNotesList;
        newData.forEach( function(it, idx) {
            if (it.id == charData.id) {
                newData[idx] = charData;
            }
        });
        return {charNotesList: newData};
    }

	updateCharData(charData: CharNotes): void {
		fetch('http://localhost:8080/' + this.state.userId + '/character', fetchPutInit(charData))
			.then(response => {
                const newData = this.mergeSingleCharDataWithFullCharData(charData, this.state.userCharData);
        		this.setState({ userCharData: newData });
            });
	}

	onLogIn(username: string, password: string): void {
		this.setState({showSpinner: true});
		const userAndPass = {"username":username,"password":password};
		fetch('http://localhost:8080/login', fetchPostInit(userAndPass))
		.then(response => {
                const authHeader = response ? response.headers.get('authorization') : 'unset';
                const userId = response ? response.headers.get("UserID") : 'unset';
				if (authHeader != 'unset' && userId != 'unset') {
					localStorage.setItem('token', authHeader ? authHeader : '');
					this.setState({showSpinner: false, isLoggedIn: true, userId: userId ? userId : 'unset'});
				} else {
					throw "Login failed"
				}
			}
		)
		.then(() => fetch('http://localhost:8080/' + this.state.userId + '/character', fetchGetInit()))
		.then(response => response.json())
		.then(json => this.setState({userCharData: {charNotesList: json}}))
		.catch(err => this.setState({showSpinner: false, isLoggedIn: false}));
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
                            userCharData={this.state.userCharData}/>}
						/>}

						{this.state.isLoggedIn &&
						<Route exact path="/character/edit" render={(props) =>
							<EditCharacter
                                {...props}
                                charNotes={this.state.selectedChar}
                                updateCharData={this.updateCharData}/>}
						/>}
					</Switch>
				}
			</div>
		);
	}


}



export default App;
