import React, {Component} from 'react';
import CharacterImage from "./CharacterImage";

class EditCharacter extends Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleCharUpdate = this.handleCharUpdate.bind(this);
		this.state = { charData : props.charData };
	}

	handleSubmit(e) {
		e.preventDefault();
		this.setState({ successCharMsg: 'Updated!' });
		this.props.updateCharData(this.state.charData);
	}

	handleCharUpdate(e) {
		const updatedCharData = { ...this.state.charData, notes: e.target.value};
		console.log("Updated character...");
		console.log(updatedCharData);
		this.setState({ charData: updatedCharData });
	}

	render() {
		const { charData } = this.props;
		return (
			<div>
				<CharacterImage name={charData.smashCharacter.name}/>
				<form onSubmit={this.handleSubmit}>
					<input onChange={this.handleCharUpdate} value={this.state.charData.notes} type="text"/>
					<button>Submit</button>
				</form>
				{this.state.successCharMsg}
			</div>
		);
	}
}

export default EditCharacter;
