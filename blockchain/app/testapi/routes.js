const express = require('express')
const network = require('./contract')
const routes = express.Router()

routes.post('/register',async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
       const response = await contract.submitTransaction("registerUser",rbody.email,rbody.name,rbody.room_no,rbody.phone_no)
        res.status(200).json({
            msg:"User successfully registered"
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getuser/:email',async (req,res)=>{
    try {
        const contract = await network.contract()
        await contract.evaluateTransaction("userGateway",req.params.email)
        res.status(200).json({
            user:JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"Error in get user"
        })
    }
})
module.exports=routes