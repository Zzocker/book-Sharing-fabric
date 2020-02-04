const express = require('express')
const network = require('./contract')
const validator = require('validator')
const sha256 = require('sha256')
const utils = require('./utils')
const routes = express.Router()

routes.post('/register',async (req,res)=>{
    try {
        rbody = req.body
        if (!validator.isEmail(rbody.email)){
            res.status(500).json({
                msg:"Invaild email ID"
            })
        }
        if (rbody.password.length<8){
            res.status(500).json({
                msg:"length of password should be greater then 7"
            })
        }
        password = sha256(rbody.password)
        const contract = await network.contract()
        await contract.submitTransaction("registerUser",rbody.email,rbody.name,rbody.room_no,rbody.phone_no,password)
        const token = utils.generateAuthToken(rbody.email)
        res.status(200).json({
            msg:"User successfully registered",
            token: token
        })
    } catch (error) {
        res.status(500).json({
            msg: error
        })
    }
})
routes.get('/login', async(req, res) => { // there should some function that should check if connection is established with blockchain
    //Login a registered user
    try {
        const { email, password } = req.body
        Encpassword = sha256(password)
        const contract = await network.contract()
        await contract.evaluateTransaction("login",email,Encpassword)
        const token = utils.generateAuthToken(email)
        res.json({ token })
    } catch (error) {
        res.status(400).json({error,Encpassword})
    }

})
routes.post('/addbook',utils.auth,async (req,res)=>{
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
routes.put('/changecover',utils.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const email = req.email
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
routes.delete('/removebook/:isbn',utils.auth,async (req,res)=>{
    try {
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
routes.post('/requestbook/:isbn',utils.auth,async (req,res)=>{
    try {

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
routes.put('/respondrequest',utils.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'respondRequest',rbody.isbn,req.email,rbody.response)
        res.status(200).json({   
            msg:`Responeded the request`
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.put('/transferbook',utils.auth,async (req,res)=>{
    try {
        const rbody = req.body
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",req.email,'transferBook',rbody.isbn,req.email)
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
//////////////////////////////////////////////QUERY///////////////////////////////////////////////////

routes.get('/getrequest/:request_id',utils.auth,async (req,res)=>{
    try {
        const email = req.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getTheRequest",req.params.request_id)
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getallthebook',utils.auth,async (req,res)=>{
    try {
        const email = req.email
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",email,"getAllTheBook")
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/getuser/:email',utils.auth,async (req,res)=>{
    try {	
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
routes.get('/getbook/:isbn',utils.auth,async (req,res)=>{
    try {
        const contract = await network.contract()
        const response = await contract.evaluateTransaction("userGateway",req.email,"getTheBook",req.params.isbn)
        res.status(200).json({   
            books: JSON.parse(response)
        })
    } catch (error) {
        res.status(500).json({
            msg: error.message
        })
    }
})
routes.get('/me',utils.auth,async (req,res)=>{
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
routes.get('/getallownedbook',utils.auth,async (req,res)=>{
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
routes.get('/getallcurentbook',utils.auth,async (req,res)=>{
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
// routes.get('/getallrequest',utils.auth,async (req,res)=>{
//     try {
//         const contract = await network.contract()
//         const response = await contract.evaluateTransaction("userGateway",req.email,"getAllRequest")
//         res.status(200).json({   
//             requests: JSON.parse(response)
//         })
//     } catch (error) {
//         res.status(500).json({
//             msg:error.message
//         })
//     }
// })
module.exports=routes
