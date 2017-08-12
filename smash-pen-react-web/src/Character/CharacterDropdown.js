import React from 'react';
import '../pure-release-1.0.0/forms-min.css';

const CharacterDropdown = ({characters, onDropDownChange}) =>
		<select id="charDropdown" onChange={onDropDownChange}>
			{characters && characters.map((item) => <option>{item.name}</option>)}
		</select>;

export default CharacterDropdown;