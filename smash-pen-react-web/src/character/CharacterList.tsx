import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';
import '../pure-release-1.0.0/menus-min.css'
import '../pure-release-1.0.0/menus-core-min.css';
import '../pure-release-1.0.0/menus-dropdown-min.css';

import CharacterRow from "./CharacterRow";
import {match} from "react-router-dom";
import {OnEditChar, UserCharData} from "../App";
import {fetchGetInit} from "../FetchUtil";
import AddCharacter from "./AddCharacter";

interface CharacterListProps {
    onEditChar: OnEditChar;
    userCharData: UserCharData
    match: match<string>;
}

interface CharacterListState {
    characterList: Character[];
    charDropDownName: string
}

export interface Character {
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
		fetch(process.env.REACT_APP_SERVER_URL + 'characters', fetchGetInit())
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
			<AddCharacter onDropDownChange={this.onDropDownChange}
                          characterList={this.state.characterList}
                          onEditChar={onEditChar}
                          charDropDownName={this.state.charDropDownName}
                          match={match}/>
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
