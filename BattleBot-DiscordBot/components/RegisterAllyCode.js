const { sendRequest,sendGetRequest } = require('./connection'); 
const { getEnpointWithBasicUrl } = require('./UrlRepository'); 

async function addRegisterRequest(discordId, allyCode){
    const url = getEnpointWithBasicUrl("register");
    const data = {
        discordId,
        allyCode
    };

    const response = await sendRequest(url,'POST',data)
    return response['requestUUID']
}

async function getRegisterStatus(uuid) {
    const url = getEnpointWithBasicUrl("register");
    const data = {
        uuid
    };

    const response = await sendGetRequest(url,data);
    return response['status']
}

async function getRegisterResult(uuid) {
    const url = getEnpointWithBasicUrl("register/result");
    const data = {
        uuid
    };

    const response = await sendGetRequest(url,data);
    return response['result']
}

module.exports = { addRegisterRequest,getRegisterStatus,getRegisterResult};