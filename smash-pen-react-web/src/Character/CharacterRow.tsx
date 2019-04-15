import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/buttons-min.css';
import {Link, match} from 'react-router-dom'
import CharacterImage from "./CharacterImage";

type CharacterRowProps = {
    data: Data;
    onEditChar: (data: Data) => void;
    match: match<string>;
}

type Data = {
    smashCharacter: SmashCharacter;
    notes: string;
}

type SmashCharacter = {
    name: string
}


class CharacterRow extends Component<CharacterRowProps> {
	constructor(props: CharacterRowProps) {
		super(props);
	}

	render() {
		//TODO Create obj to hold image,name etc. in obj?
		const data: Data = this.props.data;
		const { smashCharacter, notes } = data;
		const { onEditChar, match } = this.props;
		return (
			<tr>
				<td>
					<CharacterImage name={smashCharacter.name}/>
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
