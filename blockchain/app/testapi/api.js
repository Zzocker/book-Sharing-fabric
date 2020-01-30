const express = require('express')
const routes = require('./routes')
const PORT = "3000"
const api = express()
const logger= (req,res,next)=>{
    console.log(`${req.protocol}://${req.get('host')}${req.originalUrl}:${res.body}`)
    next()
}
api.use(express.json())
api.use(logger)
api.use('/api/user',routes)
api.listen(PORT,()=>{
    console.log(`Listening on port : ${PORT}`)
})
