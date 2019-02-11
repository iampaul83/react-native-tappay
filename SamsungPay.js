import React from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';
import { NativeModules } from 'react-native';

async function samsungPay() {
  const prime = await NativeModules.TapPayModule.samsungPay();
  return prime;
}

export function SamsungPayButton({ onGetPrime, onError }) {
  return (
    <View style={{ width: '100%', paddingHorizontal: 20, margin: 10 }}>
      <Button
        style={{ alignSelf: 'stretch' }}
        title="Samsung Pay"
        color="black"
        onPress={() => {
          samsungPay()
            .then(onGetPrime)
            .catch(onError);
        }}
      />
    </View>
  );
}

export function SamsungPayStatus({ status, prime, error }) {
  if (!status) return null;

  if (status === 'SUCCESS') {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>Samsung Pay get prime success!</Text>
        <Text>Prime = {prime}</Text>
      </View>
    );
  } else {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>Samsung Pay get prime failed!</Text>
        <Text>Error Code = {error.code}</Text>
        <Text>Error Message = {error.message}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: 20,
    marginRight: 20,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
