import React from 'react';
import { AppRegistry, Text} from 'react-native';

import {StackNavigator} from 'react-navigation';
import App from "./App";

class CharacterScreen extends React.Component {
	static navigationOptions = {
		title: 'Welcome',
	};

	render() {
		return <Text>Hello, Navigation!</Text>;
	}
}

const SmashPenApp = StackNavigator({
	HomeScreen: {home: App},
	CharacterScreen: {screen: CharacterScreen}
});

AppRegistry.registerComponent('smash-pen-react-native', () => SmashPenApp);