package datastorefactory

import (
	"context"
	"time"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type monogdbFactory struct{}

func (m *monogdbFactory) Build(cfg *config.DataStoreConfig) (DataStoreInterface, error) {
	logger.Log.Debug("monogdbFactory")

	clientOptions := options.Client().ApplyURI(cfg.URL)
	clientOptions.SetAuth(options.Credential{
		Username: cfg.Username,
		Password: cfg.Password,
	})
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		logger.Log.Warnf("error connecting : %s ; error : %v", cfg.URL, err)
		return nil, err
	}

	ctx, cancel := context.WithTimeout(context.Background(), 100*time.Millisecond)
	defer cancel()
	err = client.Ping(ctx, nil)
	if err != nil {
		logger.Log.Warnf("ping failure : %s", cfg.URL)
		return nil, err
	}
	
	return client.Database(cfg.DbName).Collection(cfg.Collection), nil
}
