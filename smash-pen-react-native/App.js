import React from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';

export default class App extends React.Component {
	constructor(props) {
		super(props)

	}

	updateButton() {
		console.log('this');
	}



  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.test}>Open up App.js to start working on your app!</Text>
        <Text>Changes you make will automatically reload.</Text>
        <Text id="texttochange">Shake your phone to open the developer menu.</Text>

		  <Button
			  onPress={this.updateButton}
			  title='Learn More'
			  color="#841584"
			  accessibilityLabel="Learn more about this purple button"
		  />
      </View>
	);
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
	test: {
  		flex: 2,
		fontSize: 50
	}
});
