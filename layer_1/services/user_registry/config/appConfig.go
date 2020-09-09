package config

const (

	// datasource
	MONGODB = "mongodb"

	// ContactPoint system types
	PHONE     = "PHONE"
	EMAIL     = "EMAIL"
	LINKEDIN  = "LINKEDIN"
	TWITTER   = "TWITTER"
	INSTAGRAM = "INSTAGRAM"

	// Gender type
	FEMALE = "FEMALE"
	MALE   = "MALE"
	OTHER  = "OTHER"
)

type LogConfig struct {
	Code         string `yaml:"code"`
	Lavel        string `yaml:"lavel"`
	EnableCaller bool   `yaml:"enableCaller"`
}

type DataStoreConfig struct {
	Code       string `yaml:"code"`
	URL        string `yaml:"url"`
	Username   string `yaml:"username"`
	Password   string `yaml:"password"`
	DbName     string `yaml:"dbName"`
	Collection string `yaml:"collection"`
}
