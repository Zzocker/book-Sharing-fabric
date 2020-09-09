package logrus

import (
	"fmt"
	"os"
	"runtime"
	"strings"

	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/config"
	"github.com/Zzocker/book-Sharing-fabric/layer_1/services/user_registry/container/logger"
	"github.com/sirupsen/logrus"
)

func RegisterLog(cfg config.LogConfig) error {
	log := logrus.New()
	log.SetFormatter(&logrus.TextFormatter{
		CallerPrettyfier: func(f *runtime.Frame) (string, string) {
			return "", fmt.Sprintf(" %s:%d ", formatFilePath(f.File), f.Line)
		},
	})
	log.SetReportCaller(cfg.EnableCaller)
	log.SetOutput(os.Stdout)
	l := &log.Level
	if err := l.UnmarshalText([]byte(cfg.Lavel)); err != nil {
		return err
	}
	log.SetLevel(*l)
	logger.SetLogger(log)
	return nil
}

func formatFilePath(path string) string {
	arr := strings.Split(path, "/")
	return arr[len(arr)-1]
}
