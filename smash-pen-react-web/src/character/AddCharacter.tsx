import * as React from 'react';
import CharacterDropdown from "./CharacterDropdown";
import {Link, match} from "react-router-dom";
import {Character, OnDropDownChange} from "./CharacterList";
import {OnEditChar} from "../App";

interface AddCharacterProps {
    onDropDownChange: OnDropDownChange;
    characterList: Character[]
    onEditChar: OnEditChar
    charDropDownName: string;
    match: match<string>;
}

function AddCharacter({ onDropDownChange, characterList, onEditChar, charDropDownName, match }: AddCharacterProps) {
    return (
        <table className="pure-table">
            <tbody>
            <tr>
                <td>Add a new character</td>
                <td>
                    <CharacterDropdown
                        onDropDownChange={onDropDownChange}
                        characters={characterList}/>
                </td>
                <td>
                    <Link to={match.url + "/edit"}>
                        <button className="pure-button"
                                onClick={() =>
                                    onEditChar({notes: 'newly created character!', smashCharacter: {name: charDropDownName}})}
                        >Add</button>
                    </Link>
                </td>
            </tr>
            </tbody>
        </table>
    );
}

export default AddCharacter;
