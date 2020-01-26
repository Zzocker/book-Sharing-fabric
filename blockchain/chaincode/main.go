package main

import (
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
