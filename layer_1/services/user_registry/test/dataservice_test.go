package dataservice

import (
	"testing"
	"time"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/model"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/dataservice"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/dataservicefactory/userdataservicefactory"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/loggerfactory/logrus"
	"github.com/stretchr/testify/assert"
)

func TestRegisterLog(t *testing.T) {
	err := logrus.RegisterLog(config.LogConfig{
		Lavel:        "debug",
		EnableCaller: true,
	})
	assert.Equal(t, nil, err, "no error should be generated")
	assert.NotNil(t, logger.Log, "Log object should be not be nil")
}

var udInterface dataservice.UserDataInterface

func TestUserDataGetDB(t *testing.T) {
	cfg := config.DataStoreConfig{
		Code:       "mongodb",
		URL:        "mongodb://localhost:27017",
		Username:   "root",
		Password:   "example",
		DbName:     "bookSharing",
		Collection: "user",
	}
	var err error
	udInterface, err = userdataservicefactory.GetUserDataServiceFactory(cfg.Code).Build(&cfg)
	assert.Equal(t, nil, err, "no error should be generated")
	assert.NotNil(t, udInterface, "userdataInterface object should be not be nil")
}

func TestUserInsert(t *testing.T) {
	user := model.User{
		Username: "zzocker",
		Name:     "Pritam Singh",
		Telecom: []model.ContactPoint{
			{
				System: config.EMAIL,
				Value:  "pkspritam10@gmail.com",
			},
		},
		Gender:        config.MALE,
		RegisterdDate: time.Now(),
	}
	err := udInterface.Insert(&user)
	assert.Equal(t, nil, err, "no error should be generated")
}
