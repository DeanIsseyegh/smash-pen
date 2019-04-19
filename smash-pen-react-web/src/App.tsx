import React, {Component} from 'react';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';

import {Route, Switch} from 'react-router-dom'
import CharacterList from "./character/CharacterList";
import PlayerList from "./PlayerList";
import Main from "./Main";
import EditCharacter from "./character/EditCharacter";
import {Loading} from "./Loading";
import {MainNav} from "./MainNav";
import {fetchGetInit, fetchPostInit, fetchPutInit} from "./FetchUtil";
import LogInError from "./login/LogInError";

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
    updateCharMsg: string;
    isLogInErr: boolean;
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
        this.onLogIn = this.onLogIn.bind(this);
        const emptyCharData: CharNotes = {smashCharacter: {id: 0, name: ''}, notes: ''};
        this.state = {
            userCharData: {charNotesList: []},
            selectedChar: emptyCharData,
            isLoggedIn: false,
            token: '',
            showSpinner: false,
            updateCharMsg: '',
            isLogInErr: false
        };
    }

    setSelectedChar(charData: CharNotes): void {
        this.setState({selectedChar: charData});
    }

    updateCharData(charData: CharNotes): void {
        fetch('http://localhost:8080/' + this.state.userId + '/character', fetchPutInit(charData))
            .then(response => this.handleResponse(response))
            .then(() => fetch('http://localhost:8080/' + this.state.userId + '/character', fetchGetInit()))
            .then(response => this.handleResponse(response))
            .then(response => response.json())
            .then(json => this.setState({userCharData: {charNotesList: json}}))
            .then(() => this.setState({updateCharMsg: "Character successfully updated!"}))
            .catch(() => this.setState({updateCharMsg: "There was a problem updating your Character!"}));

    }

    onLogIn(username: string, password: string): void {
        this.setState({showSpinner: true});
        const userAndPass = {"username": username, "password": password};
        fetch('http://localhost:8080/login', fetchPostInit(userAndPass))
            .then(response => this.handleResponse(response))
            .then(response => this.handleLogin(response))
            .then(() => fetch('http://localhost:8080/' + this.state.userId + '/character', fetchGetInit()))
            .then(response => response.json())
            .then(json => this.setState({userCharData: {charNotesList: json}, isLogInErr: false}))
            .catch(() => this.setState({showSpinner: false, isLoggedIn: false, isLogInErr: true}));
    }

    private handleResponse(response: Response): Response {
        if (!response.ok) {
            throw Error(response.statusText)
        }
        return response
    }

    private handleLogin(response: Response) {
        const authHeader = response ? response.headers.get('authorization') : null;
        const userId = response ? response.headers.get("UserID") : null;
        if (authHeader && userId) {
            localStorage.setItem('token', authHeader ? authHeader : '');
            this.setState({showSpinner: false, isLoggedIn: true, userId: userId ? userId : 'unset'});
        } else {
            throw Error("Login failed")
        }
    }

    render() {
        const {isLoggedIn, isLogInErr} = this.state;
        return (
            <div className="App">
                <MainNav isLoggedIn={isLoggedIn}/>
                <LogInError isLogInErr={isLogInErr}/>
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
                        <Route exact path="/character/edit" render={() =>
                            <EditCharacter
                                updateCharMsg={this.state.updateCharMsg}
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
