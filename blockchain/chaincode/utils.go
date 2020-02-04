package main

import (
	"encoding/json"
	"fmt"

	"github.com/hyperledger/fabric-chaincode-go/shim"
)

func getLoginKey(stub shim.ChaincodeStubInterface, email string) string {
	key, _ := stub.CreateCompositeKey(LOGIN, []string{email})
	return key
}
func getStateByte(stub shim.ChaincodeStubInterface, key string) ([]byte, error, bool) {
	SByte, err := stub.GetState(key)
	if err != nil {
		return nil, err, false
	}
	if len(SByte) == 0 {
		return nil, fmt.Errorf("State doesn't exists"), false
	}
	return SByte, nil, true
}
func getUser(stub shim.ChaincodeStubInterface, key string) (User, error) {
	RByte, err, ok := getStateByte(stub, key)
	if ok != true {
		return User{}, err
	}
	var user User
	json.Unmarshal(RByte, &user)
	return user, nil
}
func getBook(stub shim.ChaincodeStubInterface, key string) (Book, error) {
	BByte, err, ok := getStateByte(stub, key)
	if ok != true {
		return Book{}, err
	}
	var book Book
	json.Unmarshal(BByte, &book)
	return book, nil
}
func getRequest(stub shim.ChaincodeStubInterface, key string) (Request, error) {
	RByte, err, ok := getStateByte(stub, key)
	if ok != true {
		return Request{}, err
	}
	var request Request
	json.Unmarshal(RByte, &request)
	return request, nil
}
