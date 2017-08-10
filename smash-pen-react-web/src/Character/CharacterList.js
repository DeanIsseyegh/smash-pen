import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';

import CharacterRow from "./CharacterRow";

const data = {
	pikachu: { name: "pikachu", notes: "kewl" },
	mario: { name: "mario", notes: "Mario kkk"},
	bayonetta: { name: "bayonetta", notes: ""}
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
				<CharacterRow name="pikachu" image={iconDir + data.pikachu.name + imgExt} notes={data.pikachu.notes} match={match} onEditChar={onEditChar}/>
				<CharacterRow name="mario" image={iconDir + data.mario.name + imgExt} notes={data.mario.notes} match={match} onEditChar={onEditChar}/>
				<CharacterRow name="bayonetta" image={iconDir + data.bayonetta.name + imgExt} notes={data.bayonetta.notes} match={match} onEditChar={onEditChar}/>
			</table>
		</div>
		);
	}
}

export default CharacterList;