import {Link} from 'react-router-dom'
import React from 'react';
import logo from './smashball.svg';

export const MainNav = ({isLoggedIn}) =>
	<div>
		<div className="App-header">
			<img src={logo} className="App-logo" alt="logo"/>
			<h2>Welcome to SmashPen</h2>
		</div>
		<p className="App-intro">
			{isLoggedIn &&
			<Link to="/player">
				<button className="pure-button">Players</button>
			</Link>}
			<Link to="/">
				<button className="pure-button">Main</button>
			</Link>
			{isLoggedIn &&
			<Link to="/character">
				<button className="pure-button">Characters</button>
			</Link>}
		</p>
	</div>;
