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
routes.post('/addbook',async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
       const response = await contract.submitTransaction("userGateway",req.headers.email,"addBook",rbody.isbn,rbody.name,rbody.author)
        res.status(200).json({
            msg:"successfully added book to platform"
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/changecover',async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",email,'changeCover',rbody.isbn,rbody.cover)
        res.status(200).json({   
            msg:`successfully updated you book's cover image`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.delete('/removebook/:isbn',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",email,'removeBook',req.params.isbn)
        res.status(200).json({   
            msg:`successfully removed you book from our network`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.post('/requestbook/:isbn',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        await contract.submitTransaction("userGateway",email,'requestBook',req.params.isbn,email)
        res.status(200).json({   
            msg:`Request has been sent to current owner of book`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/respondrequest',async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",email,'respondRequest',rbody.isbn,email,rbody.response)
        res.status(200).json({   
            msg:`Responeded the request`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/transferbook',async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",email,'transferBook',rbody.isbn,email)
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

routes.get('/getrequest/:isbn',async (req,res)=>{
    try {
        const email = req.headers.email
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
routes.get('/getuser/:email',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getTheUser",req.params.email)
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getbook/:isbn',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getTheBook",req.params.isbn)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
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
        const response = await contract.evaluateTransaction("userGateway",req.params.email,"getUser")
        res.status(200).json({
            msg:JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error
        })
    }
})
routes.get('/getallownedbook',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getAllOwnedBook")
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error.message
        })
    }
})
routes.get('/getallcurentbook',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getAllCurentBook")
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg:error.message
        })
    }
})
routes.get('/getallrequest',async (req,res)=>{
    try {
        const email = req.headers.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getAllRequest")
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