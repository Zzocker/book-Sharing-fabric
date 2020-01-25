package main

import (
	"fmt"
	"encoding/json"
	"time"

	jwt "github.com/dgrijalva/jwt-go"
	"github.com/hyperledger/fabric-chaincode-go/shim"
)
type Claim struct{
	Email string `json:"email"`
	jwt.StandardClaims
}
func getUserKey(stub shim.ChaincodeStubInterface, email string) string {
	key, _ := stub.CreateCompositeKey(USER, []string{email})
	return key
}
func getUserLoginKey(stub shim.ChaincodeStubInterface, email string) string {
	key, _ := stub.CreateCompositeKey(LOGIN, []string{email})
	return key
}
func getBookKey(stub shim.ChaincodeStubInterface,isbn string) string{
	key, _ := stub.CreateCompositeKey(BOOK, []string{isbn})
	return key
}
func getRequestKey(stub shim.ChaincodeStubInterface,email,isbn string) string  {
	key,_:= stub.CreateCompositeKey(REQUEST,[]string{email,isbn})
	return key
}
func createJWT(email string) (string, error) {
	claim := &Claim{
		Email: email,
		StandardClaims: jwt.StandardClaims{
			ExpiresAt: time.Now().Add(5 * time.Hour).Unix(),
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claim)
	tokenString, err := token.SignedString([]byte("pritam"))
	if err != nil {
		return "", err
	}
	return tokenString, nil
}
func authToken(tokenString string) (bool,string) {
	c:=&Claim{}
	tkn,_:= jwt.ParseWithClaims(tokenString,c,func(token *jwt.Token) (interface{}, error) {
		return []byte("pritam"), nil
	})
	return tkn.Valid ,c.Email
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
func getBook(stub shim.ChaincodeStubInterface,key string) (Book,error){
	BByte, err:= getStateByte(stub, key)
	if err!=nil{
		return Book{},err
	}
	var book Book
	json.Unmarshal(BByte,&book)
	return book,nil
}
func getRequest(stub shim.ChaincodeStubInterface, key string) (Request,error){
	RByte, err:= getStateByte(stub, key)
	if err!=nil{
		return Request{},err
	}
	var request Request
	json.Unmarshal(RByte,&request)
	return request,nil
}
func getUser(stub shim.ChaincodeStubInterface, key string) (User,error){
	RByte, err:= getStateByte(stub, key)
	if err!=nil{
		return User{},err
	}
	var user User
	json.Unmarshal(RByte,&user)
	return user,nil
}