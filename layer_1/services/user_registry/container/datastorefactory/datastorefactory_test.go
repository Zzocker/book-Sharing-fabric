package datastorefactory

import (
	"testing"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/loggerfactory/logrus"
	"github.com/stretchr/testify/assert"
	"go.mongodb.org/mongo-driver/mongo"
)

func TestRegisterLog(t *testing.T) {
	err := logrus.RegisterLog(config.LogConfig{
		Lavel:        "info",
		EnableCaller: true,
	})
	assert.Equal(t, nil, err, "no error should be generated")
	assert.NotNil(t, logger.Log, "Log object should be not be nil")
}

func TestMonogdbFactory(t *testing.T) {
	cfg := config.DataStoreConfig{
		Code:     "mongodb",
		URL:      "mongodb://localhost:27017",
		Username: "root",
		Password: "example",
	}
	mgF := &monogdbFactory{}
	client, err := mgF.Build(&cfg)
	assert.Equal(t, nil, err, "no error should be generated")
	assert.NotNil(t, client, "client object should be not be nil")
	_, ok := client.(*mongo.Collection)
	assert.Equal(t, true, ok, "type client should be mongo.Client")
}

func TestGetDataStoreFb(t *testing.T) {
	cfg := config.DataStoreConfig{
		Code:     "mongodb",
		URL:      "mongodb://localhost:27017",
		Username: "root",
		Password: "example",
	}
	db := GetDataStoreFb(cfg.Code)
	assert.NotNil(t, db, "db object should be not be nil")
}
