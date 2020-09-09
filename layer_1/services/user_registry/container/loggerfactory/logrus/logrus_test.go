package logrus

import (
	"testing"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/stretchr/testify/assert"
)

func TestRegisterLog(t *testing.T) {
	err := RegisterLog(config.LogConfig{
		Lavel:        "info",
		EnableCaller: true,
	})
	assert.Equal(t, nil, err, "no error should be generated")
	assert.NotNil(t, logger.Log, "Log object should be not be nil")
}
