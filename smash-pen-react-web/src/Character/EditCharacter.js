import React, {Component} from 'react';

class EditCharacter extends Component {

	render() {
		console.log(this.props.char);
		return (
			<div>
				<input type="text"></input>
			</div>
		);
	}
}

export default EditCharacter;