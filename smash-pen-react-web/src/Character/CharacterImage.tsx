// import * as React from 'react';
import * as PropTypes from 'prop-types';
import * as React from 'react';


function CharacterImage(props) {
    const iconDir = "/icons/characters/";
    const imgExt = ".png";
    console.log("Name is: ");
    console.log(props.name);
    return <img className="" alt={props.name} src={iconDir + props.name + imgExt} />;
}

CharacterImage.propTypes = {
	name: PropTypes.string.isRequired
};

export default CharacterImage;
