package model

import "time"

type User struct {
	Username      string         `json:"username"`
	Name          string         `json:"name"`
	Telecom       []ContactPoint `json:"telecom"`
	Gender        string         `json:"gender"` // FEMALE | MALE | OTHER
	BirthDate     time.Time      `json:"birthDate"`
	Address       Address        `json:"address"`
	Photo         Attachment     `json:"photo"`
	Active        bool           `json:"active"`
	RegisterdDate time.Time      `json:"register_on"`
}

type ContactPoint struct {
	System string `json:"system"` // PHONE | EMAIL | LINKEDIN | TWITTER | INSTAGRAM
	Value  string `json:"value"`
}

type Address struct {
	Text       string   `json:"text"`
	Line       []string `json:"line"`
	City       string   `json:"city"`
	District   string   `json:"district"`
	State      string   `json:"state"`
	PostalCode string   `json:"postalCode"`
	Country    string   `json:"country"`
}

type Attachment struct {
	ContentType string `json:"contentType"` // name of attachment
	Data        []byte `json:"data"`        // acctual data
	URL         string `json:"url"`         // URL of data
	Size        string `json:"size"`        // size of data
	Hash        string `json:"hash"`        // hash of data
	Title       string `json:"title"`       // title of data
}
