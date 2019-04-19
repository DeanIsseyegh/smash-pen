import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/buttons-min.css';
import {Link, match} from 'react-router-dom'
import CharacterImage from "./CharacterImage";
import {CharNotes, OnEditChar } from "../App";

interface CharacterRowProps {
    charNotes: CharNotes;
    onEditChar: OnEditChar;
    match: match<string>;
}

class CharacterRow extends Component<CharacterRowProps> {
	constructor(props: CharacterRowProps) {
		super(props);
	}

	render() {
		const charNotes: CharNotes = this.props.charNotes;
		const { onEditChar, match } = this.props;
		return (
			<tr>
				<td>
					<CharacterImage name={charNotes.smashCharacter.name}/>
				</td>
				<td>
					{charNotes.notes}
				</td>
				<td>
					<Link to={match.url + "/edit"}>
						<button className="pure-button" onClick={() => onEditChar(charNotes)}>Edit</button>
					</Link>
				</td>
			</tr>
		);
	}

}

export default CharacterRow;
