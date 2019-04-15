import * as React from 'react';

type CharacterImageProps = {
    name: string;
}

function CharacterImage(props: CharacterImageProps) {
    const iconDir = "/icons/characters/";
    const imgExt = ".png";
    console.log("Name is: ");
    console.log(props.name);
    return <img className="" alt={props.name} src={iconDir + props.name + imgExt} />;
}

export default CharacterImage;
