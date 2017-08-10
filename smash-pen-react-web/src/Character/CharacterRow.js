import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import './pure-release-1.0.0/buttons-min.css';
import {Route, Link} from 'react-router-dom'

class CharacterRow extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		//TODO Create obj to hold image,name etc. in obj?
		const { image, name, notes, match, onEditChar } = this.props;
		return (
			<tr>
				<td>
					<img className="" alt={name} src={image} />
				</td>
				<td>
					{notes}
				</td>
				<td>
					<Link to={match.url + "/edit"}>
						<button className="pure-button" onClick={() => onEditChar(name)}>Edit</button>
					</Link>
				</td>
			</tr>
		);
	}

}

export default CharacterRow;