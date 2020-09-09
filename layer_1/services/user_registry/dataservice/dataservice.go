package dataservice

import (
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/model"
)

// UserDataInterface represents interface for user data access throught database
type UserDataInterface interface {
	// Insert adds a user to database.
	Insert(u *model.User) (err error)
}
