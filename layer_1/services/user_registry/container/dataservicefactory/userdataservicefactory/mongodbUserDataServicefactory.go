package userdataservicefactory

import (
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/datastorefactory"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/dataservice"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/dataservice/userdata/mongodb"
	"go.mongodb.org/mongo-driver/mongo"
)

type mongodbUserDataServiceFactory struct{}

func (m *mongodbUserDataServiceFactory) Build(cfg *config.DataStoreConfig) (dataservice.UserDataInterface, error) {
	logger.Log.Debug("mongodbUserDataServiceFactory")

	db, err := datastorefactory.GetDataStoreFb(cfg.Code).Build(cfg)
	if err != nil {
		return nil, err
	}
	return &mongodb.UserDataMongoDB{DB: db.(*mongo.Collection)}, nil
}
