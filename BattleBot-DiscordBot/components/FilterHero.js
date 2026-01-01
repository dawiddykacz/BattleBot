const { sendRequest,sendGetRequest } = require('./connection'); 
const { getEnpointWithBasicUrl } = require('./UrlRepository'); 

async function addFilterHeroRequest(discordId, heroNames){
    const url = getEnpointWithBasicUrl("filter/hero");
    const data = {
        discordId,
        heroNames
    };

    const response = await sendRequest(url,'POST',data)
    return response['requestUUID']
}

async function getFilterStatus(uuid) {
    const url = getEnpointWithBasicUrl("filter/hero");
    const data = {
        uuid
    };

    const response = await sendGetRequest(url,data);
    return response['status']
}

async function getFilterResult(uuid) {
    const url = getEnpointWithBasicUrl("filter/hero/result");
    const data = {
        uuid
    };

    return await sendGetRequest(url,data);
}

module.exports = { addFilterHeroRequest,getFilterResult,getFilterStatus};