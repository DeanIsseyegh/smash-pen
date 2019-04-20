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
import JWT from "jwt-decode";

interface AppState {
    userId: string;
    isLoggedIn: boolean;
    showSpinner: boolean;
    token: string;
    selectedChar: CharNotes
    userCharData: UserCharData
    updateCharMsg: string;
    isLogInErr: boolean;
    showLogIn: boolean;
}

export type OnEditChar = (charNotes: CharNotes) => void;

export type UpdateCharData = (charData: CharNotes) => void;

export type OnLogIn = (username: string, password: string) => void

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

const emptyCharData: CharNotes = {smashCharacter: {id: 0, name: ''}, notes: ''};

const initialState: AppState = {
    userCharData: {charNotesList: []},
    selectedChar: emptyCharData,
    isLoggedIn: false,
    token: '',
    showSpinner: false,
    updateCharMsg: '',
    isLogInErr: false,
    userId: '',
    showLogIn: false
};

const noLogInTokenState: AppState = {...initialState, ...{showLogIn: true}};

class App extends Component<{}, AppState> {
    constructor({}) {
        super({});
        this.setSelectedChar = this.setSelectedChar.bind(this);
        this.updateCharData = this.updateCharData.bind(this);
        this.onLogIn = this.onLogIn.bind(this);
        this.handleExistingUserToken = this.handleExistingUserToken.bind(this);
        this.state = initialState
    }

    componentDidMount() {
        const loginToken = localStorage.getItem('token');
        if (loginToken) {
            this.handleExistingUserToken()
        } else {
            this.setState(noLogInTokenState);
        }
    }

    private handleExistingUserToken() {
        const loginToken = localStorage.getItem('token');
        if (loginToken) {
            const userId: string = this.getUserIdFromToken(loginToken);
            if (userId) {
                this.fetchLatestUserNotes(userId)
                    .then(() => this.setState({
                        userId: userId,
                        isLogInErr: false,
                        isLoggedIn: true,
                        showSpinner: false
                    }))
                    .catch(() => this.setState(noLogInTokenState));
            }
        }
    }

    private fetchLatestUserNotes(userId: string) {
        return fetch(process.env.REACT_APP_SERVER_URL + userId + '/character', fetchGetInit())
            .then(response => this.handleResponse(response))
            .then(response => response.json())
            .then(json => this.setState({userCharData: {charNotesList: json}}));
    }

    setSelectedChar(charData: CharNotes): void {
        this.setState({selectedChar: charData});
    }

    updateCharData(charData: CharNotes): void {
        fetch(process.env.REACT_APP_SERVER_URL + this.state.userId + '/character', fetchPutInit(charData))
            .then(response => this.handleResponse(response))
            .then(() => this.fetchLatestUserNotes(this.state.userId))
            .then(() => this.setState({updateCharMsg: "Character successfully updated!"}))
            .catch(() => this.setState({updateCharMsg: "There was a problem updating your Character!"}));

    }

    onLogIn(username: string, password: string): void {
        this.setState({showSpinner: true});
        const userAndPass = {"username": username, "password": password};
        fetch(process.env.REACT_APP_SERVER_URL + 'login', fetchPostInit(userAndPass))
            .then(response => this.handleResponse(response))
            .then(response => this.handleLogin(response))
            .then(() => this.fetchLatestUserNotes(this.state.userId))
            .then(() => this.setState({isLogInErr: false}))
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
        const userId = response ? this.getUserIdFromToken(authHeader) : null;
        if (authHeader && userId) {
            localStorage.setItem('token', authHeader ? authHeader : '');
            this.setState({showSpinner: false, isLoggedIn: true, userId: userId});
        } else {
            throw Error("Login failed")
        }
    }

    private getUserIdFromToken(token: string | null): string {
        if (token) {
            const jwt: any = JWT(token);
            return jwt.sub;
        } else {
            return '';
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

                        {this.state.showLogIn && <Route exact path="/" render={(props) => <Main
                            {...props}
                            onLogIn={this.onLogIn}
                            isLoggedIn={isLoggedIn}/>}
                        />}

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
