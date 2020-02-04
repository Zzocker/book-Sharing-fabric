package main

// docTypes
const (
	USER    = "USERTYPE"
	BOOK    = "BOOKTYPE"
	REQUEST = "REQUESTTYPE"
	LOGIN   = "LOGIN" // email
	// USERKEY = email for ensuring single user with a given email
)

type User struct {
	DocType string           `json:"docType"`
	Email   string           `json:"email"`
	Name    string           `json:"name"`
	RoomNo  string           `json:"room_no"`
	PhoneNo string           `json:"phone_no"`
	Owned   map[string]bool  `json:"owned"`
	Current map[string]int64 `json:"current"`
}

type Book struct {
	DocType  string `json:"docType"`
	Weight   int    `json:"weight"`
	ISBN     string `json:"isbn"`
	BookName string `json:"book_name"`
	Author   string `json:"author"`
	Owner    string `json:"owner"`
	Current  string `json:"current"`
	Cover    string `json:"cover"`
}

type Request struct {
	DocType   string `json:"docType"`
	Id        string `json:"id"`
	ISBN      string `json:"isbn"`
	ToEmail   string `json:"to_email"`
	FromEmail string `json:"from_email"`
	Status    string `json:"status"`
	/*
		0 : request to get book
		1 : request for getting book accepted
		2 : rejected
	*/
}
type Login struct {
	Email    string `json:"id"`
	Password string `json:"password"`
}
