import React, {Component} from 'react';
import CharacterImage from "./CharacterImage";

class EditCharacter extends Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.state = {};
	}

	handleSubmit(e) {
		this.setState({ successCharMsg: 'Updated!' });
		this.props.updateCharData(e);
	}

	render() {
		const { charData, handleCharChange } = this.props;
		return (
			<div>
				<CharacterImage name={charData.character.name}/>

				{charData.name}:
				<form onSubmit={this.handleSubmit}>
					<input ref={function(input) { if (input !== null)input.focus()}} onChange={handleCharChange} value={charData.notes} type="text"/>
					<button>Submit</button>
				</form>
				{this.state.successCharMsg}
			</div>
		);
	}
}

export default EditCharacter;