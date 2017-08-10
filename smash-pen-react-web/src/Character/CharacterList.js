import React, { Component } from 'react';
import '../pure-release-1.0.0/tables-min.css';

import CharacterRow from "./CharacterRow";

const data = [
	{ name: "pikachu", notes: "kewl" },
	{ name: "mario", notes: "Mario kkk"},
	{ name: "bayonetta", notes: ""}
];

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
				{
					data.map((it) =>
						<CharacterRow data={it} match={match} onEditChar={onEditChar}/>)
				}
			</table>
		</div>
		);
	}
}

export default CharacterList;