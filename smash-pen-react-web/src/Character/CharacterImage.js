import React from 'react';
import PropTypes from 'prop-types';

const CharacterImage = ({name}) => {
	const iconDir = "/icons/characters/";
	const imgExt = ".png";
	return <img className="" alt={name} src={iconDir + name + imgExt} />;
};

CharacterImage.propTypes = {
	name: PropTypes.string.isRequired
};

export default CharacterImage;