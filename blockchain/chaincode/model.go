package main
type User struct {
	Email   string `json:"email"`
	Name    string `json:"name"`
	RoomNo  string `json:"room_no"`
	PhoneNo string `json:"phone_no"`
}
type Book struct {
	ISBN string `json:"isbn"`
	BookName string `json:"book_name"`
	Author string `json:"author"`
	Owner string `json:"owner"`
	Current string `json:"current"`
	Cover []byte `json:"cover"`
}