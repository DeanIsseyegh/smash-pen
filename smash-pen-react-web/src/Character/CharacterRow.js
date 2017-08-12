import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/buttons-min.css';
import {Route, Link} from 'react-router-dom'
import CharacterImage from "./CharacterImage";

class CharacterRow extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		//TODO Create obj to hold image,name etc. in obj?
		const data = this.props.data;
		const { character, notes } = data;
		const { onEditChar, match } = this.props;
		return (
			<tr>
				<td>
					<CharacterImage name={character.name}/>
				</td>
				<td>
					{notes}
				</td>
				<td>
					<Link to={match.url + "/edit"}>
						<button className="pure-button" onClick={() => onEditChar(data)}>Edit</button>
					</Link>
				</td>
			</tr>
		);
	}

}

export default CharacterRow;