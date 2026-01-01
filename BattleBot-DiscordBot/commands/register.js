const { SlashCommandBuilder } = require('discord.js');
const { addRegisterRequest, getRegisterStatus, getRegisterResult } = require('../components/RegisterAllyCode'); 
const { isNotFound,isCompleted} = require('../components/StatusWrapper'); 
const { isErrorWithDisplay } = require('../components/Errors');

async function registerAllyCode(userId,allyCode) {
  try{
    const uuid = await addRegisterRequest(userId,allyCode);
    
    const startTime = new Date();
    let status;

    while(!isNotFound(status) && !isCompleted(status)){
      status = await getRegisterStatus(uuid);

      if(new Date() - startTime > 60000){
        throw new Error("Timeout")
      }
    }

    if(isNotFound(status)){
      throw new Error("Request not found")
    }

    const result = await getRegisterResult(uuid);
    return result;
  }catch(error){
    if (isErrorWithDisplay(error)) {
        return `[Error] ${error.message}`;
    }
    console.error(error.message)
    return `[Error]`;
  }
}

module.exports = {
  data: new SlashCommandBuilder()
    .setName('register')
    .setDescription('Rejestruje użytkownika')
    .addStringOption(option =>
      option
        .setName('allycode')
        .setDescription('ally code ze star wars galaxy of heroes')
        .setRequired(true)
    ),
  async execute(interaction) {
    const userId = interaction.user.id;
    const allyCode = interaction.options.getString('allycode');

    await interaction.deferReply({ ephemeral: true });
    const response = await registerAllyCode(userId,allyCode);
    await interaction.editReply(`<@${userId}>: ${response}`);
  },
};
