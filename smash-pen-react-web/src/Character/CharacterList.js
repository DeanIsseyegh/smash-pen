import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/menus-min.css'
import '../pure-release-1.0.0/menus-core-min.css';
import '../pure-release-1.0.0/menus-dropdown-min.css';

import CharacterRow from "./CharacterRow";
import CharacterDropdown from "./CharacterDropdown";
import {Link} from "react-router-dom";

class CharacterList extends Component {
	constructor(props) {
		super(props);
		this.onDropDownChange = this.onDropDownChange.bind(this);
        this.state = { charDropDownName: '' };
	}

	componentWillMount() {
		var init = {
			method: 'GET',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Authorization': localStorage.getItem('token')
			}
		};
		fetch('http://localhost:8080/characters', init)
			.then(response => response.json())
			.then(result => { this.setState({ characterList: result })})
	}

	onDropDownChange(e) {
		this.setState({ charDropDownName: e.target.value.toLowerCase()});
	}

	render() {
		const { match, onEditChar, data } = this.props;

		return (
		<div>
			Characters:
			<br/><br/><br/>
			<table className="pure-table">
                <tbody>
					<tr>
						<td>Add a new character</td>
						<td>
							<CharacterDropdown
								onDropDownChange={this.onDropDownChange}
								characters={this.state.characterList}/>
						</td>
						<td>
							<Link to={match.url + "/edit"}>
								<button className="pure-button"
										onClick={() =>
											onEditChar({notes: 'newly created character!', smashCharacter: {name: this.state.charDropDownName}})}
								>Add</button>
							</Link>
						</td>
					</tr>
                </tbody>
			</table>
			<br/><br/>
			<table className="pure-table">
                <tbody>
				{
					data && data.length > 0 && data.filter((it)=> it.notes)
						.map((it) =>
						<CharacterRow
							key={it.smashCharacter.name}
							data={it}
							match={match}
							onEditChar={onEditChar}/>)
				}
                </tbody>
			</table>
		</div>
		);
	}
}

export default CharacterList;
