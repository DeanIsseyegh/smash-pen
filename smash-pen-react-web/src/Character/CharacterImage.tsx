import * as React from 'react';

interface CharacterImageProps {
    name: string;
}

function CharacterImage(props: CharacterImageProps) {
    const iconDir = "/icons/characters/";
    const imgExt = ".png";
    return <img className="" alt={props.name} src={iconDir + props.name + imgExt} />;
}

export default CharacterImage;
