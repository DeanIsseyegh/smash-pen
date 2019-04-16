import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/menus-min.css'
import '../pure-release-1.0.0/menus-core-min.css';
import '../pure-release-1.0.0/menus-dropdown-min.css';

import CharacterRow from "./CharacterRow";
import CharacterDropdown from "./CharacterDropdown";
import {Link, match} from "react-router-dom";
import {CharNotes, OnEditChar, UserCharData} from "../App";

interface CharacterListProps {
    onEditChar: OnEditChar;
    userCharData: UserCharData
    match: match<string>;
}

interface CharacterListState {
    characterList: Character[];
    charDropDownName: string
}

interface Character {
    name: string;
}

export type OnDropDownChange = (e: React.FormEvent<HTMLSelectElement>) => void

class CharacterList extends Component<CharacterListProps, CharacterListState> {
	constructor(props: CharacterListProps) {
		super(props);
		this.onDropDownChange = this.onDropDownChange.bind(this);
        this.state = { charDropDownName: '', characterList: [] };
	}

	componentWillMount() {
	    const headers = new Headers();
	    const token = localStorage.getItem('token');
	    headers.append('Accept', 'application/json');
	    headers.append('Content-Type', 'application/json');
	    headers.append('Authorization', token ? token : '');
		let init: RequestInit = {
			method: 'GET',
			headers: headers
		};
		fetch('http://localhost:8080/characters', init)
			.then(response => response.json())
			.then(result => { this.setState({ characterList: result })});
	}

	onDropDownChange(e: React.FormEvent<HTMLSelectElement>) {
		this.setState({ charDropDownName: e.currentTarget.value.toLowerCase()});
	}

    render() {
		const { match, onEditChar, userCharData } = this.props;
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
					userCharData.charNotesList && userCharData.charNotesList.length > 0 && userCharData.charNotesList.filter((it)=> it.notes)
						.map((it) =>
						<CharacterRow
                            key={it.smashCharacter.name}
                            charNotes={it}
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
