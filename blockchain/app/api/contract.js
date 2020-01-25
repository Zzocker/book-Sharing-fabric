const fs = require('fs')
const yaml = require('js-yaml')
const {FileSystemWallet,Gateway} = require('fabric-network')
const CONNECTION_PROFILE_PATH= "../client/connection.yaml"
const WALLET_PATH="../client/wallet"
const IDENTITY_NAME = "AppClient"
const CHANNEL_NAME = "bookchannel"
const CONTRACT_NAME="book"

async function contract(){
       const ccp = yaml.safeLoad(fs.readFileSync(CONNECTION_PROFILE_PATH))
       const wallet = new FileSystemWallet(WALLET_PATH)
       const is = await wallet.exists(IDENTITY_NAME) 
       if (!is){
           return
       }
       const gateway = new Gateway()
       await gateway.connect(ccp,{wallet:wallet,identity:IDENTITY_NAME,discovery:{enabled:false,asLocalhost:true}})
       const network = await gateway.getNetwork(CHANNEL_NAME)
       const contract = network.getContract(CONTRACT_NAME)
       return contract
}
module.exports = {contract}