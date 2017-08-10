import React, { Component } from 'react';

const CharacterImage = ({name}) => {
	const iconDir = "./icons/characters/";
	const imgExt = ".png";
	return <img className="" alt={name} src={iconDir + name + imgExt} />;
};

export default CharacterImage;