package main

import (
	"encoding/json"

	"github.com/hyperledger/fabric-chaincode-go/shim"

	"github.com/hyperledger/fabric-protos-go/peer"

	"fmt"
)

type BookChaincode struct {
}

func (c *BookChaincode) Init(stub shim.ChaincodeStubInterface) peer.Response {
	return shim.Success(nil)
}
func (c *BookChaincode) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
	funcName, args := stub.GetFunctionAndParameters()
	if funcName == "registerUser" {
		return registerUser(stub, args)
	}
	if funcName == "login" {
		return login(stub, args)
	}
	if funcName == "userGateway" {
		return userGateway(stub, args)
	}
	return shim.Success(nil)
}
func main() {
	err := shim.Start(new(BookChaincode))
	if err != nil {
		fmt.Printf(err.Error())
	}
}
func login(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// args [0] : email args[1] : password
	if len(args) != 2 {
		return shim.Error("please provide vaild email/password")
	}
	LByte, err, ok := getStateByte(stub, getLoginKey(stub, args[0]))
	if ok != true {
		return shim.Error(err.Error())
	}
	var login Login
	json.Unmarshal(LByte, &login)
	if login.Password != args[1] {
		return shim.Error("Incorrect password")
	}
	return shim.Success(nil)
}
