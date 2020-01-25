const express = require('express')
const network = require('./contract')
const routes = express.Router()

routes.post('/register',async (req,res)=>{
    try {
        rbody = req.body
        const contract = await network.contract()
        await contract.submitTransaction("registerUser",rbody.email,rbody.name,rbody.room_no,rbody.phone_no,rbody.password)
        res.status(200)
    } catch (error) {
        res.status(500)
    }
})
routes.get('/login',async (req,res)=>{
    try {
        const rbody = req.body
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",'login',rbody.email,rbody.password)
        res.status(200).json({
            msg:"Successfully loged in",
            token : response.toString()
        })
    } catch (error) {
        res.status(500)
    }
})
routes.post('/addbook',async (req,res)=>{
    try {
        const rbody = req.body
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,'addBook',rbody.isbn,rbody.book_name,rbody.author)
        res.status(200).json({   
            msg:`successfully added you book to our network`
        })
    } catch (error) {
        res.status(500)
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
        res.status(500)
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
        res.status(500)
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
        res.status(500)
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
        res.status(500)
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
        res.status(500)
    }
})
routes.get('/getrequest/:request_d',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getRequest",req.params.request_id)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
routes.get('/getuser/:email',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getUser",req.params.email)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
routes.get('/getbook/:isbn',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getBook",req.params.isbn)
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
routes.get('/getallownedbook',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getAllOwnedBook")
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
routes.get('/getallcurentbook',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getAllCurentBook")
        res.status(200).json({   
            books: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
routes.get('/getallrequest',async (req,res)=>{
    try {
        const token = req.headers.token
        console.log(token)
        const contract = await network.contract()
        const response = await contract.submitTransaction("userGateway",token,"getAllRequest")
        res.status(200).json({   
            requests: JSON.parse(response.Payload)
        })
    } catch (error) {
        res.status(500)
    }
})
module.exports=routes