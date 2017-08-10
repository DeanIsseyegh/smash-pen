const CharacterImage = ({name}) => {
	const iconDir = "./icons/characters/";
	const imgExt = ".png";
	<img className="" alt={name} src={iconDir + name + imgExt} />
}