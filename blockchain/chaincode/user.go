package main

import (
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-protos-go/peer"
	"time"
)

func userGateway(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) < 2 {
		return shim.Error("Require more than two args for user Gateway")
	}
	auth := args[0]
	newargs := args[1:]
	if auth == "login" {
		return login(stub, newargs)
	} else {
		is, email := authToken(auth)
		if !is {
			return shim.Error("Invaild credentials")
		}
		key := getUserKey(stub, email)
		UByte, err := getStateByte(stub, key)
		if err != nil {
			return shim.Error(err.Error())
		}
		var user User
		json.Unmarshal(UByte, &user)
		/*1*/
		if newargs[0]=="addBook"{
			err:=addBook(stub,newargs[1:],&user)
			if err!=nil{
				return shim.Error(err.Error())
			}
			UByte,_= json.Marshal(user)
			stub.PutState(key,UByte)
			return shim.Success(UByte)
		}
		/*2*/
		if newargs[0]=="changeCover"{
			err:= changeCover(stub,newargs[1:],user.Email)
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(nil)
		}
		/*3*/
		if newargs[0]=="removeBook"{
			err:=removeBook(stub,newargs[1:],&user)
			if err!=nil{
				return shim.Error(err.Error())
			}
			UByte,_= json.Marshal(user)
			stub.PutState(key,UByte)
			return shim.Success(UByte)
		}
		/*4*/
		if newargs[0]=="requestBook"{
			err:= requestBook(stub,newargs[1:],user.Email)
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(nil)
		}
		/*5*/
		if newargs[0]=="respondRequest"{
			err:= respondRequest(stub,newargs[1:],user.Email)
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(nil)
		}
		/*6*/
		if newargs[0]=="transferBook"{
			err:= transferBook(stub,newargs[1:],&user)
			if err!=nil{
				return shim.Error(err.Error())
			}
			UByte,_= json.Marshal(user)
			stub.PutState(key,UByte)
			return shim.Success(UByte)
		}
		/*7*/
		if newargs[0]=="getRequest"{
			if len(newargs[1:])!=1{
				return shim.Error("")
			}
			RByte,err:= getStateByte(stub,args[0])
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(RByte)
		}
		/*8*/
		if newargs[0]=="getUser"{
			if len(newargs[1:])!=1{
				return shim.Error("")
			}
			UByte,err:= getStateByte(stub,getUserKey(stub,args[0]))
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(UByte)
		}
		/*9*/
		if newargs[0]=="getBook"{
			if len(newargs[1:])!=1{
				return shim.Error("")
			}
			BByte,err:= getStateByte(stub,getBookKey(stub,args[0]))
			if err!=nil{
				return shim.Error(err.Error())
			}
			return shim.Success(BByte)
		}
		/*10*/
		if newargs[0]=="getAllOwnedBook"{
			return getAllOwnedBook(stub,user.Owned)
		}
		/*11*/
		if newargs[0]=="getAllCurentBook"{
			return getAllCurentBook(stub,user.Current)
		}
		/*12*/
		if newargs[0]=="getAllRequest"{
			return getAllRequest(stub,user.Email)
		}
		return shim.Error("")
		
	}
	
}
func getAllRequest(stub shim.ChaincodeStubInterface,email string) peer.Response  {
	result := []Request{}
	Riterator,err:= stub.GetStateByPartialCompositeKey(REQUEST,[]string{email})
	if err!=nil{
		return shim.Error(err.Error())
	}
	defer Riterator.Close()
	for Riterator.HasNext(){
		res ,err:= Riterator.Next()
		if err!=nil{
			return shim.Error(err.Error())
		}
		var temp Request
		json.Unmarshal(res.Value,&temp)
		result = append(result,temp)
	}
	output,_:= json.Marshal(result)
	return shim.Success(output)
}
func getAllCurentBook(stub shim.ChaincodeStubInterface,books map[string]int64) peer.Response  {
	result:= []struct{
		BookDetail Book `json:"book_details"`
		GotOn int64 `json:"got_on"`
	}{}
	for isbn,value := range books{
		b,err:= getBook(stub,getBookKey(stub,isbn))
		if err!=nil{
			return shim.Error(err.Error())
		}
		temp:=struct {
			BookDetail Book `json:"book_details"`
			GotOn int64 `json:"got_on"`
		}{
			BookDetail: b,
			GotOn: value,
		}
		result = append(result,temp)
	}
	output,_:= json.Marshal(result)
	return shim.Success(output)
}
func getAllOwnedBook(stub shim.ChaincodeStubInterface,books map[string]bool) peer.Response{
	var results []Book
	for isbn,_ := range books{
		temp,err:= getBook(stub,getBookKey(stub,isbn))
		if err!=nil{
			return shim.Error(err.Error())
		}
		results = append(results,temp)
	}
	output,_:= json.Marshal(results)
	return shim.Success(output)
}
func transferBook(stub shim.ChaincodeStubInterface,args []string,user *User) error  {
	// args[0]= request ID
	if len(args)!=1{
		return fmt.Errorf("Require 2 args to respond to request")
	}
	_,attr,_:=stub.SplitCompositeKey(args[0])
	EfromR:=attr[0]
	if EfromR!=user.Email{
		return fmt.Errorf("owner miss-match")
	}
	request,err:= getRequest(stub,args[0])
	if err!=nil{
		return err
	}
	if request.Status!="1"{
		return fmt.Errorf("Request is not accepted")
	}
	isbn := attr[1]
	book,err:= getBook(stub,getBookKey(stub,isbn))
	if err!=nil{
		return err
	}
	Touser,err:= getUser(stub,getUserKey(stub,request.FromEmail))
	if err!=nil{
		return err
	}
	Touser.Current[isbn]=time.Now().Unix()
	BByte,_:=json.Marshal(Touser)
	stub.PutState(getUserKey(stub,request.FromEmail),BByte)
	book.Current=request.FromEmail
	BByte,_=json.Marshal(book)
	stub.PutState(getBookKey(stub,isbn),BByte)
	delete(user.Current,isbn)
	stub.DelState(args[0])
	return nil
}
func respondRequest(stub shim.ChaincodeStubInterface,args[]string,email string)  error{
	//args[0]= request ID , args[1]= response
	if len(args)!=2{
		return fmt.Errorf("Require 2 args to respond to request")
	}
	_,attr,_:=stub.SplitCompositeKey(args[0])
	EfromR:=attr[0]
	if EfromR!=email{
		return fmt.Errorf("owner miss-match")
	}
	request,err:= getRequest(stub,args[0])
	if err!=nil{
		return err
	}
	if args[1]=="accept"{
		request.Status="1"
	}
	if args[1]=="reject"{
		request.Status="2"
	}
	RByte,_:= json.Marshal(request)
	stub.PutState(args[0],RByte)
	return nil
}
func requestBook(stub shim.ChaincodeStubInterface,args []string,email string) error {
	// args[0]=isbn
	if len(args)!=1{
		return fmt.Errorf("Require 1 args to make book request")
	}
	Bookkey:= getBookKey(stub,args[0])
	book,err:=getBook(stub,Bookkey)
	if err!=nil{
		return fmt.Errorf("Book doesn't exists!!")
	}
	if book.Current==email{
		return fmt.Errorf("Cannot request ownself")
	}
	requestKey := getRequestKey(stub,book.Current,args[0])
	request := Request{
		ID: requestKey,
		ISBN: args[0],
		FromEmail: email,
	}
	RByte,_:= json.Marshal(request)
	stub.PutState(requestKey,RByte)
	return nil
}
func removeBook(stub shim.ChaincodeStubInterface,args []string,user *User) error  {
	// args[0]=isbn
	if len(args)!=1{
		return fmt.Errorf("Require 1 args to remove book")
	}
	key := getBookKey(stub , args[0])
	book ,err:= getBook(stub ,key)
	if err!=nil{
		return err
	}
	if book.Owner==user.Email && book.Current==user.Email{
		err = stub.DelState(key)
		if err!=nil{
			return err
		}
		delete(user.Owned,args[0])
		delete(user.Current,args[0])
		return nil
	}
	return fmt.Errorf("Owner mis-match")
}
func changeCover(stub shim.ChaincodeStubInterface,args []string,email string) error{
	//args[0]=isbn , args[1]=image byte code
	if len(args)!=2{
		return fmt.Errorf("Require 2 args to add/change cover of book")
	}
	key := getBookKey(stub , args[0])
	book ,err:= getBook(stub ,key)
	if err!=nil{
		return err
	}
	if book.Owner!=email{
		return fmt.Errorf("Owner mis-match")
	}
	book.Cover=[]byte(args[1])
	BByte,_ := json.Marshal(book)
	err = stub.PutState(key,BByte)
	if err!=nil{
		return err
	}
	return nil
}
func addBook(stub shim.ChaincodeStubInterface, args []string, user *User) error {
	// args[0]=ISBN,args[1]=Bookname,args[2]=author
	if len(args) != 3 {
		return fmt.Errorf("Require 3 args for adding new book to platform")
	}
	key := getBookKey(stub, args[0])
	_, err := getStateByte(stub, key)
	if err == nil {
		return fmt.Errorf("Book Already exists")
	}
	book := Book{
		ISBN:     args[0],
		BookName: args[1],
		Author:   args[2],
		Owner:    user.Email,
		Current:  user.Email,
		Cover:    []byte{},
	}
	BByte, _ := json.Marshal(book)
	stub.PutState(key, BByte)
	user.Current[args[0]] = time.Now().Unix()
	user.Owned[args[0]]=true
	return nil
}
func login(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// args[0]=email,args[1]=password
	if len(args) != 2 {
		shim.Error("require two args to login")
	}
	key := getUserLoginKey(stub, args[0])
	LByte, err := getStateByte(stub, key)
	if err != nil {
		return shim.Error(err.Error())
	}
	var login Login
	json.Unmarshal(LByte, &login)
	if login.Password == args[1] && login.Email == args[0] {
		token, err := createJWT(login.Email)
		if err != nil {
			return shim.Error(err.Error())
		}
		return shim.Success([]byte(token))
	}
	return shim.Error("")
}
func registerUser(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	// args[0]=email , args[1]=name, args[2]=roomNO args[3]=phoneNO args[4]=password
	if len(args) != 5 {
		return shim.Error("To register new user please provide 5 args")
	}
	key := getUserKey(stub, args[0])
	_, err := getStateByte(stub, key)
	if err == nil {
		return shim.Error(err.Error())
	}
	user := User{
		Email:   args[0],
		Name:    args[1],
		RoomNo:  args[2],
		PhoneNo: args[3],
		Owned:   make(map[string]bool),
		Current: make(map[string]int64),
	}
	uByte, _ := json.Marshal(user)
	err = stub.PutState(key, uByte)
	if err != nil {
		return shim.Error(err.Error())
	}
	key = getUserLoginKey(stub, user.Email)
	login := Login{
		Email:    args[0],
		Password: args[4],
	}
	uByte, _ = json.Marshal(login)
	err = stub.PutState(key, uByte)
	if err != nil {
		return shim.Error(err.Error())
	}
	// return shim.Success(nil)
	return shim.Success(uByte) // for testing
}
