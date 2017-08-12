import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/menus-min.css'
import '../pure-release-1.0.0/menus-core-min.css';
import '../pure-release-1.0.0/menus-dropdown-min.css';

import CharacterRow from "./CharacterRow";
import CharacterDropdown from "./CharacterDropdown";
import {Link} from "react-router-dom";

const characters = [
	"Pikachu",
	"Gannondorf",
	"Jigglypuff",
	"Zelda",
	"Shiek"

];

class CharacterList extends Component {
	constructor(props) {
		super(props);
		this.onDropDownChange = this.onDropDownChange.bind(this);
		this.state = { charDropDownName: '' };
	}

	componentWillMount() {
		fetch('http://localhost:8080/characters')
			.then(response => response.json())
			.then(result => { this.setState({ characterList: result }); return result })
			.then(result => console.log(result));
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
				<form className="pure-form">
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
											onEditChar(data.find((it) =>
												it.character.name.toLowerCase() === this.state.charDropDownName.toLowerCase()
											))}
								>Add</button>
							</Link>
						</td>
					</tr>
				</form>
			</table>
			<br/><br/>
			<table className="pure-table">
				{
					data.filter((it)=> it.notes)
						.map((it) =>
						<CharacterRow
							key={it.character.name}
							data={it}
							match={match}
							onEditChar={onEditChar}/>)
				}
			</table>
		</div>
		);
	}
}

export default CharacterList;