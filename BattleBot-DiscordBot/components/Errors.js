function getError(errorMessage){
    return new Error(errorMessage || "Error");
}

function getErrorWithDisplay(errorMessage){
    err = getError(errorMessage);
    err.type = "display";
    return err;
}
function isErrorWithDisplay(err){
    return err?.type === "display";
}

module.exports = { getError,getErrorWithDisplay,isErrorWithDisplay };