package mongodb

import (
	"context"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/model"
	"go.mongodb.org/mongo-driver/mongo"
)

type UserDataMongoDB struct {
	DB *mongo.Collection
}

func (udm *UserDataMongoDB) Insert(u *model.User) error {
	// TODO implement me
	result, err := udm.DB.InsertOne(context.TODO(), u)
	if err != nil {
		logger.Log.Errorf("failed to save %s", u.Username)
		return err
	}
	logger.Log.Debugf("Insert new User; id : %v", result.InsertedID)
	return nil
}
