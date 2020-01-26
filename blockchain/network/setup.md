cryptogen generate --config=./crypto-config.yaml
configtxgen -profile Genesis -outputBlock channel-artifacts/genesis.block -channelID genesis
configtxgen -outputCreateChannelTx channel-artifacts/channel.tx -profile BookChannel -channelID bookchannel
configtxgen -outputAnchorPeersUpdate channel-artifacts/HostAnchorUPdate.tx -profile BookChannel -channelID bookchannel -asOrg Host


change *-cert.pem to cert.pem in ca folder , and other with PRIVATE_KEY

peer channel create -f channel.tx -o orderer:7050 -c bookchannel
peer channel join -b bookchannel.block 
peer channel update -f HostAnchorUPdate.tx -o orderer:7050 -c bookchannel

now enroll the admin then register the client

peer chaincode install -n book -v 0 -p chaincode
peer chaincode instantiate -n book -v 0 -C bookchannel -c '{"args":["init"]}'
peer chaincode upgrade -n book -v 1 -C bookchannel -c '{"args":["init"]}'