import React, { Component } from 'react';
import './pure-release-1.0.0/tables-min.css';

import pikachu from './icons/characters/chr_00_pikachu_01.png';
import mario from './icons/characters/chr_00_mario_01.png';
import bayonetta from './icons/characters/chr_00_bayonetta_01.png';

import CharacterRow from "./CharacterRow";

const data = {
	pikachu: "pika is noyce",
	mario: "mario is kewl",
	bayonetta: ""
};

class CharacterList extends Component {
	constructor(props) {
		super(props);
	}

	render() {
		const { match, onEditChar } = this.props;

		return (
		<div>
			Characters:
			<br/><br/><br/>
			<table className="pure-table">
				<CharacterRow name="pikachu" image={pikachu} notes={data.pikachu} match={match} onEditChar={onEditChar}/>
				<CharacterRow name="mario" image={mario} notes={data.mario} match={match} onEditChar={onEditChar}/>
				<CharacterRow name="bayonetta" image={bayonetta} notes={data.bayonetta} match={match} onEditChar={onEditChar}/>
			</table>
		</div>
		);
	}
}

export default CharacterList;