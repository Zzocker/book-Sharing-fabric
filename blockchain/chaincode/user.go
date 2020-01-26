package main

import (
	"encoding/json"

	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-protos-go/peer"
)

func userGateway(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) < 1 {
		return shim.Error("Need email id of user.")
	}
	user, err := getUser(stub, getUserKey(stub, args[0]))
	if err != nil {
		return shim.Error(err.Error())
	}
	UByte, _ := json.Marshal(user)
	return shim.Success(UByte)
}
func registerUser(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// args[0]=email , args[1]= name , args[2]= room_no , args[3]=phone_no
	if len(args) != 4 {
		return shim.Error("provide 4 args To register new user")
	}
	key := getUserKey(stub, args[0])
	_, err := getStateByte(stub, key)
	if err == nil {
		return shim.Error("User already registered")
	}
	user := User{
		Email:   args[0],
		Name:    args[1],
		RoomNo:  args[2],
		PhoneNo: args[3],
		Owned:   make(map[string]bool),
		Current: make(map[string]int64),
	}
	UByte, _ := json.Marshal(user)
	err = stub.PutState(key, UByte)
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

