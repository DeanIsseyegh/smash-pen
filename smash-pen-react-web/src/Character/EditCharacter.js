import React, {Component} from 'react';
import CharacterImage from "./CharacterImage";

class EditCharacter extends Component {
	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleCharChange = this.handleCharChange.bind(this);
		this.state = { charData : props.charData };
	}

	handleSubmit(e) {
		e.preventDefault();
		this.setState({ successCharMsg: 'Updated!' });
		this.props.updateCharData(this.state.charData);
	}

	handleCharChange(e) {
	/*	this.setState(
			{
				result: {hits: updatedHits, page},
				results: {
					...results,
					[searchKey]: {hits: updatedHits, page}
				},
				isLoading: false
			}
		);*/
		const updatedCharData = { ...this.state.charData, notes: e.target.value};
		console.log(updatedCharData);
		this.setState({ charData: updatedCharData });
	}

	render() {
		const { charData } = this.props;
		return (
			<div>
				<CharacterImage name={charData.smashCharacter.name}/>
				<form onSubmit={this.handleSubmit}>
					<input onChange={this.handleCharChange} value={this.state.charData.notes} type="text"/>
					<button>Submit</button>
				</form>
				{this.state.successCharMsg}
			</div>
		);
	}
}

export default EditCharacter;