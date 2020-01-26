package main

import (
	"encoding/json"
	"fmt"

	"github.com/hyperledger/fabric-chaincode-go/shim"
)

func getUserKey(stub shim.ChaincodeStubInterface, email string) string {
	key, _ := stub.CreateCompositeKey(USER, []string{email})
	return key
}
func getBookKey(stub shim.ChaincodeStubInterface, isbn string) string {
	key, _ := stub.CreateCompositeKey(BOOK, []string{isbn})
	return key
}
func getRequestKey(stub shim.ChaincodeStubInterface, email, isbn string) string {
	key, _ := stub.CreateCompositeKey(REQUEST, []string{email, isbn})
	return key
}
func getStateByte(stub shim.ChaincodeStubInterface, key string) ([]byte, error) {
	SByte, err := stub.GetState(key)
	if err != nil {
		return nil, err
	}
	if len(SByte) == 0 {
		return nil, fmt.Errorf("State doesn't exists")
	}
	return SByte, nil
}
func getUser(stub shim.ChaincodeStubInterface, key string) (User, error) {
	RByte, err := getStateByte(stub, key)
	if err != nil {
		return User{}, err
	}
	var user User
	json.Unmarshal(RByte, &user)
	return user, nil
}
