function isNotFound(statusName){
    if(statusName == undefined) return false;
    return statusName == "not_found";
}
function isCompleted(statusName){
    if(statusName == undefined) return false;
    return statusName == "completed";
}

module.exports = { isNotFound,isCompleted };