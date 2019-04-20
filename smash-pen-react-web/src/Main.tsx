import React, { Component } from 'react';
import './App.css';
import './pure-release-1.0.0/base.css';
import './pure-release-1.0.0/buttons.css';
import LogIn from "./login/LogIn";
import {OnLogIn} from "./App";

interface MainProps {
    isLoggedIn: boolean;
    onLogIn: OnLogIn;
}

class Main extends Component<MainProps> {

	render() {
		const { isLoggedIn, onLogIn } = this.props;
		return isLoggedIn ? <div>Welcome to the main page</div> : <LogIn onLogIn={onLogIn}/>;
	}
}

export default Main;
