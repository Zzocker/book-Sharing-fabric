package userdataservicefactory

import (
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/dataservice"
)

var (
	udsfMap = map[string]userDataServiceFactory{
		config.MONGODB: &mongodbUserDataServiceFactory{},
	}
)

type userDataServiceFactory interface {
	Build(cfg *config.DataStoreConfig) (dataservice.UserDataInterface, error)
}

// GetUserDataServiceFactory is accessors for factoryBuilderMap
func GetUserDataServiceFactory(code string) userDataServiceFactory {
	return udsfMap[code]
}
