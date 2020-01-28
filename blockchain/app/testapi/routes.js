const express = require('express')
const network = require('./contract')
const mongo = require('./mongo')
const routes = express.Router()

routes.post('/register',async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
        await contract.submitTransaction("registerUser",rbody.email,rbody.name,rbody.room_no,rbody.phone_no,()=>{
            const user = new mongo.User(req.body) /// from mongo
            await user.save() /// from mongo
       })
        res.status(200).json({
            msg:"User successfully registered"
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.post('/login', async(req, res) => {
    //Login a registered user
    try {
        const { email, password } = req.body
        const user = await mongo.User.findByCredentials(email, password)
        if (!user) {
            return res.status(401).send({error: 'Login failed! Check authentication credentials'})
        }
        const token = await user.generateAuthToken()
        res.send({ token })
    } catch (error) {
        res.status(400).send(error)
    }

})
routes.post('/addbook',mongo.auth,async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
       const response = await contract.submitTransaction("userGateway",req.email,"addBook",rbody.isbn,rbody.name,rbody.author)
        res.status(200).json({
            msg:"successfully added book to platform"
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/changecover',mongo.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'changeCover',rbody.isbn,rbody.cover)
        res.status(200).json({   
            msg:`successfully updated you book's cover image`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.delete('/removebook/:isbn',mongo.auth,async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'removeBook',req.params.isbn)
        res.status(200).json({   
            msg:`successfully removed you book from our network`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.post('/requestbook/:isbn',mongo.auth,async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        await contract.submitTransaction("userGateway",req.email,'requestBook',req.params.isbn,email)
        res.status(200).json({   
            msg:`Request has been sent to current owner of book`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/respondrequest',mongo.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'respondRequest',rbody.isbn,email,rbody.response)
        res.status(200).json({   
            msg:`Responeded the request`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/transferbook',mongo.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'transferBook',rbody.isbn,email)
        res.status(200).json({   
            msg:`Transfered the book`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////QUERY///////////////////////////////////////////////////////////////

routes.get('/getrequest/:isbn',mongo.auth,async (req,res)=>{
    try {
        const email = req.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getTheRequest",req.params.isbn,email)
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getuser/:email',mongo.auth,async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getTheUser",req.params.email)
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getbook/:isbn',mongo.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getTheBook",req.params.isbn)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/me',mongo.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getUser")
        res.status(200).json({
            msg:JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error
        })
    }
})
routes.get('/getallownedbook',mongo.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getAllOwnedBook")
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error.message
        })
    }
})
routes.get('/getallcurentbook',mongo.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getAllCurentBook")
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error.message
        })
    }
})
routes.get('/getallrequest',mongo.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getAllRequest")
        res.status(200).json({   
            requests: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error.message
        })
    }
})
module.exports=routes