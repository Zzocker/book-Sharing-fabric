# Book-Sharing-fabric

## Overview
As most of us know that hard-copy books are very costly and 
in college,there is always some student who has a certain book that is needed by another student.
In this project,we are trying to build a decentralized platform for students to share their books

## Features
1. owner of book can track his/her book in realtime.
2. Books will be secured using the fabric network.
3. The owner can remove his/her book from the platform.

# Start The network
## Generate the channel artifacts and crypto files
        cryptogen generate --config=./crypto-config.yaml 
        configtxgen -profile Genesis -outputBlock channel-artifacts/genesis.block -channelID genesis 
        configtxgen -outputCreateChannelTx channel-artifacts/channel.tx -profile BookChannel -channelID bookchannel 
        configtxgen -outputAnchorPeersUpdate channel-artifacts/HostAnchorUPdate.tx -profile BookChannel -channelID bookchannel -asOrg Host
## Start Docker Containers and setup the peers
1. Change the *-cert.pem to ``cert.pem`` in ca folder of peerOrganizations , and private key to ```PRIVATE_KEY```
2.      cd blockchain/network/dockerblockchain
        docker-compose up -d
3.      docker exec -it cli bash
        cd /channel-artifacts
        peer channel create -f channel.tx -o orderer:7050 -c bookchannel
        peer channel join -b bookchannel.block
        peer channel update -f HostAnchorUPdate.tx -o orderer:7050 -c bookchannel
        peer chaincode install -n book -v 0 -p chaincode
        peer chaincode instantiate -n book -v 0 -C bookchannel -c '{"args":[]}'

## Fire up the AIPs
1. cd blockchain/app/
2.      node ./client/enrollAdmin.js
        node ./client/clientRegister.js
3.      nodemon ./testapi/api.js

# Description of APIs
Route | Method| Description
-------|-------|-----------|
/register| POST | Register the users to the platform 
/login | POST | Login the user to the platdorm 
/addbook | POST | AddBook
/changecover | PUT | Change the cover of book 
/removebook/:isbn | DELETE | delete the book from platform 
/requestbook/:isbn |POST | Request book from current user
/respondrequest | PUT | respond to the request 
/transferbook | PUT| Transfer book between the users
/getrequest/:isbn | GET | get request with given isbn of book
/getuser/:email | GET| get user 
/getbook/:isbn | GET | get book
/me | GET |get the logined user
/getallownedbook | GET | get all the owned book
/getallcurentbook | GET | get current book are with user
/getallrequest |GET | get all the pending request


## Language and Technology in use
1. Golang
2. Nodejs
3. Android App Development (Java)
4. Hyperledger Fabric


# License
```MIT License

Copyright (c) 2020 Pritam Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```
