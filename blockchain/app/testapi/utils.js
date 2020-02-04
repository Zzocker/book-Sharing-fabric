const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs')
const network = require('./contract')
const fs = require('fs')
const JWT_KEY = fs.readFileSync('./private.key','UTF-8')
// const JWT_KEY='ThisIsBlockchain'

const generateAuthToken = (email)=>{
    const token = jwt.sign({email: email}, JWT_KEY,{expiresIn: '1h'})
    return token
}

const auth = async(req, res, next) => {
    const token = req.header('Authorization').replace('Bearer ', '')
    const data = jwt.verify(token, JWT_KEY)
    try {
        const contract = await network.contract()
        await contract.evaluateTransaction("checkLogin",data.email)
        req.email = data.email
        next()
    } catch (error) {
        res.status(401).send({ error: 'Not authorized to access this resource' })
    }
}
///
module.exports ={
    auth : auth,
    generateAuthToken: generateAuthToken
}