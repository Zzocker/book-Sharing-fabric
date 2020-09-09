package datastorefactory

import (
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
)

var (
	dsFbMap = map[string]dsFbInterface{
		config.MONGODB: &monogdbFactory{},
	}
)

type DataStoreInterface interface{}

type dsFbInterface interface {
	Build(cfg *config.DataStoreConfig) (DataStoreInterface, error)
}

func GetDataStoreFb(code string) dsFbInterface {
	return dsFbMap[code]
}
