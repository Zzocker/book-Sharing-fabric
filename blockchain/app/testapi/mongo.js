const mongoose = require('mongoose')
const  validator= require('validator')
const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs')
const JWT_KEY = 'ThisIsBlockchain'
///// conncect to db
mongoose.connect('mongodb+srv://userjwt:dbUserJWT@cluster0-epj5j.mongodb.net/test?retryWrites=true&w=majority', {
    useNewUrlParser: true,
    useCreateIndex: true,
})
/////////// model the user
const userSchema = mongoose.Schema({
    email: {
        type: String,
        required: true,
        unique: true,
        lowercase: true,
        validate: value => {
            if (!validator.isEmail(value)) {
                throw new Error({error: 'Invalid Email address'})
            }
        }
    },
    password: {
        type: String,
        required: true,
        minLength: 7
    },
})
userSchema.pre('save', async function (next) {
    const user = this
    if (user.isModified('password')) {
        user.password = await bcrypt.hash(user.password, 8)
    }
    next()
})
userSchema.methods.generateAuthToken = async function() {
    const user = this
    const token = jwt.sign({email: user.email}, JWT_KEY)
    return token
}
userSchema.statics.findByCredentials = async (email, password) => {
    const user = await User.findOne({ email} )
    if (!user) {
        throw new Error({ error: 'Invalid login credentials' })
    }
    const isPasswordMatch = await bcrypt.compare(password, user.password)
    if (!isPasswordMatch) {
        throw new Error({ error: 'Invalid login credentials' })
    }
    return user
}
const User = mongoose.model('User',userSchema)

/// auth middleware  
const auth = async(req, res, next) => {
    const token = req.header('Authorization').replace('Bearer ', '')
    const data = jwt.verify(token, JWT_KEY)
    try {
        const user = await User.findOne({ email: data.email})
        if (!user) {
            throw new Error()
        }
        req.email = data.email
        next()
    } catch (error) {
        res.status(401).send({ error: 'Not authorized to access this resource' })
    }
}
///
module.exports ={
    auth : auth,
    User: User
}