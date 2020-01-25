const express = require('express')
const network = require('./contract')
const routes = express.Router()

routes.post('/register',async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
        await contract.submitTransaction("registerUser",rbody.email,rbody.name,rbody.room_no,rbody.phone_no,rbody.password)
        res.status(200).json({
            msg:"Registered"
        })
    } catch (error) {
        res.status(500).json({
            msg:"Error registering"
        })
    }
})
routes.get('/login',async (req,res)=>{
    try {
        const rbody = req.body
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",'login',rbody.email,rbody.password)
        res.status(200).json({
            msg:    "Successfully loged in",
            token : response.toString()
        })
    } catch (error) {
        res.status(500).json({
            msg:"Error!! try again"
        })
    }
})
routes.post('/addbook',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        await contract.submitTransaction("userGateway",token,'addBook',rbody.isbn,rbody.book_name,rbody.author)
        res.status(200).json({   
            msg:`successfully added your book to our network`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.put('/changecover',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'changeCover',rbody.isbn,rbody.cover)
        res.status(200).json({   
            msg:`successfully updated you book's cover image`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.delete('/removebook/:isbn',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'removeBook',req.params.isbn)
        res.status(200).json({   
            msg:`successfully removed you book from our network`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.post('/requestbook/:isbn',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'requestBook',rbody.isbn)
        res.status(200).json({   
            msg:`Request has been sent to current owner of book`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.put('/respondrequest',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'respondRequest',rbody.request_id,rbody.response)
        res.status(200).json({   
            msg:`Responeded the request`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.put('/transferbook/:request_id',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'transferBook',req.params.request_id)
        res.status(200).json({   
            msg:`Transfered the book`
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getrequest/:request_id',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getRequest",req.params.request_id)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getuser/:email',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getUser",req.params.email)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getbook/:isbn',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getBook",req.params.isbn)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getallownedbook',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getAllOwnedBook")
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getallcurentbook',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getAllCurentBook")
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
routes.get('/getallrequest',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",token,"getAllRequest")
        res.status(200).json({   
            requests: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500).json({
            msg:"try again"
        })
    }
})
module.exports=routes