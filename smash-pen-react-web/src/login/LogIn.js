import React, { Component } from 'react';
import '../App.css';
import '../pure-release-1.0.0/base.css';
import '../pure-release-1.0.0/buttons.css';

class LogIn extends Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleUsernameChange = this.handleUsernameChange.bind(this);
		this.handlePasswordChange = this.handlePasswordChange.bind(this);
		this.state = ({username: '', password: ''});
	}

	handleUsernameChange(e) {
		this.setState({ username: e.target.value });
	}

	handlePasswordChange(e) {
		this.setState({ password: e.target.value })
	}

	handleSubmit(e) {
		e.preventDefault();
		this.props.onLogIn(this.state.username, this.state.password);
	}

	render() {
		return (
			<div>
				<form onSubmit={this.handleSubmit}>
					<label htmlFor="username">Username: </label>
					<input id="username" type="text" onChange={this.handleUsernameChange} value={this.state.username}/>
					<label htmlFor="password">Password: </label>
					<input id="password" type="password" onChange={this.handlePasswordChange} value={this.state.password}/>
					<button className="pure-button" type="submit">Submit</button>
				</form>
			</div>
		);
	}
}

export default LogIn;
