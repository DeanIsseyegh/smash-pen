import React from 'react';
import '../pure-release-1.0.0/forms-min.css';
import {Character, OnDropDownChange} from "./CharacterList";

interface CharacterDropdownProps {
    characters: Character[];
    onDropDownChange: OnDropDownChange;
}

const CharacterDropdown = ({characters, onDropDownChange}: CharacterDropdownProps) =>
		<select id="charDropdown" onChange={onDropDownChange}>
			{characters && characters.map((item) => <option key={item.name}>{item.name}</option>)}
		</select>;

export default CharacterDropdown;
