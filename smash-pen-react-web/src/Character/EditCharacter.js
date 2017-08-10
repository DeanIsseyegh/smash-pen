import React, {Component} from 'react';

class EditCharacter extends Component {

	render() {
		const data = this.props.data;
		console.log(this.props.data);
		return (
			<div>
				{data.name}
				<input type="text"></input>
			</div>
		);
	}
}

export default EditCharacter;