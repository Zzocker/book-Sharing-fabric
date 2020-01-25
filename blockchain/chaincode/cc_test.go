package main

import (
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-chaincode-go/shimtest"
	"testing"
)
const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVtYWlsIiwiZXhwIjoxNTc5OTIyMzgyfQ.vPyN9WZZA0bXlNi6z_TGZ__aN0C06rhQunljDfE-78w"
func TestRegister(t *testing.T){
	stub := initChaincode(t)
	response := stub.MockInvoke("invoke",SetupArgsArray("registerUser","email","Pritam Singh","A-226","911","pw"))
	t.Log(string(response.Payload))
	// login
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway","login","email","pw"))
	t.Log(string(response.Payload))
	// add book
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"addBook","12","ZeroToOne","Pritam"))
	t.Log(string(response.Payload))
	// change cover
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"changeCover","12","Hello cover"))
	t.Log(string(response.Payload))
	// print all owned books
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"getAllOwnedBook"))
	t.Log(string(response.Payload))
	// print all current books
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"getAllCurentBook"))
	t.Log(string(response.Payload))
	// remove book
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"removeBook","12"))
	t.Log(string(response.Payload))
	// request book
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"requestBook","12"))
	t.Log(string(response.Message))
	// request book
	response = stub.MockInvoke("invoke",SetupArgsArray("userGateway",token,"getAllRequest"))
	t.Log(string(response.Payload))
}

func initChaincode(t *testing.T) *shimtest.MockStub {
	stub := shimtest.NewMockStub("book", new(BookChaincode))
	response := stub.MockInit("init", nil)
	t.Logf("Chaincode init")
	if response.Status != shim.OK {
		t.FailNow()
	}
	return stub
}

func SetupArgsArray(funcName string, args ...string) [][]byte {
	ccArgs := make([][]byte, 1+len(args))
	ccArgs[0] = []byte(funcName)
	for i, arg := range args {
		ccArgs[i+1] = []byte(arg)
	}

	return ccArgs
}
