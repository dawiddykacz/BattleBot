const CHUNK_SIZE = 1900;
function splitMessage(msg) {
  const chunks = [];
  for (let i = 0; i < msg.length; i += CHUNK_SIZE) {
    chunks.push("\n"+msg.slice(i, i + CHUNK_SIZE));
  }
  return chunks;
}


module.exports = { splitMessage };