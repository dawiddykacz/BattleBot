require('dotenv').config(); 

const basicUrl = process.env.BACKEND_URL;
console.log("using backend from "+basicUrl)

function getEnpointWithBasicUrl(endpointName){
    return `${basicUrl}${endpointName}`
}

module.exports = {getEnpointWithBasicUrl};
