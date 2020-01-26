package main
const (
	USER="USER"
	BOOK="BOOK"
	REQUEST="REQUEST"
)
type User struct {
	Email   string `json:"email"`
	Name    string `json:"name"`
	RoomNo  string `json:"room_no"`
	PhoneNo string `json:"phone_no"`
	Owned map[string]bool `json:"owned"`
	Current map[string]int64 `json:"current"`
}

type Book struct {
	ISBN string `json:"isbn"`
	BookName string `json:"book_name"`
	Author string `json:"author"`
	Owner string `json:"owner"`
	Current string `json:"current"`
	Cover []byte `json:"cover"`
}

type Request struct {
	ISBN string `json:"isbn"`
	ToEmail string `json:"to_email"`
	FromEmail string `json:"from_email"`
	Status string `json:"status"` 
	/*
		0 : request to get book
		1 : request for getting book accepted
		2 : rejected
	*/
}