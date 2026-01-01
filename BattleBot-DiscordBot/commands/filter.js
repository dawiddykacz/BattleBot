const { SlashCommandBuilder } = require('discord.js');
 
const { isNotFound,isCompleted} = require('../components/StatusWrapper'); 
const { addFilterHeroRequest, getFilterStatus, getFilterResult } = require('../components/FilterHero');
const { isErrorWithDisplay } = require('../components/Errors');
const { splitMessage } = require('../components/ChunksMessage');

async function filterHeroes(userId,heroNames) {
  try{
    const uuid = await addFilterHeroRequest(userId,heroNames);
    
    const startTime = new Date();
    let status;

    while(!isNotFound(status) && !isCompleted(status)){
      status = await getFilterStatus(uuid);

      if(new Date() - startTime > 120000){
        throw new Error("Timeout")
      }
    }

    if(isNotFound(status)){
      throw new Error("Request not found")
    }

    const result = await getFilterResult(uuid);
    return formatHeroes(result);
  }catch(error){
    if (isErrorWithDisplay(error)) {
        return `[Error] ${error.message}`;
    }
    console.error(error.message)
    return `[Error]`;
  }
}

function formatHeroes(result) {
  let output = '';

  for (const [heroName, heroData] of Object.entries(result.heroesMap)) {
    output += `🦸 **${heroName.toUpperCase()}**\n`;

    // RELIC
    if (heroData.relicMap && Object.keys(heroData.relicMap).length > 0) {
      output += `\n🔮 **Relic:**\n`;
      for (const [level, players] of Object.entries(heroData.relicMap)
        .sort((a, b) => Number(b[0]) - Number(a[0]))) {
        output += `• R${level}: ${players.map(p => p.name).join(', ')}\n`;
      }
    }

    // GEAR
    if (heroData.gearMap && Object.keys(heroData.gearMap).length > 0) {
      output += `\n⚙️ **Gear:**\n`;
      for (const [level, players] of Object.entries(heroData.gearMap)
        .sort((a, b) => Number(b[0]) - Number(a[0]))) {
        output += `• G${level}: ${players.map(p => p.name).join(', ')}\n`;
      }
    }

    // WITHOUT HERO
    if (heroData.playerWithoutHeroMap?.length) {
      output += `\n👤 **Bez bohatera:**\n`;
      output += heroData.playerWithoutHeroMap
        .map(p => `${p.name}`)
        .join(', ');
      output += '\n';
    }

    output += '\n────────────────────\n';
  }

  return output.trim();
}


module.exports = {
  data: new SlashCommandBuilder()
    .setName('hero_filter')
    .setDescription('Wypisuje postacie w gildii')
    .addStringOption(option =>
      option
        .setName('hero1')
        .setDescription('nazwa bochatera z gry')
        .setRequired(true)
    )
    .addStringOption(option =>
      option
        .setName('hero2')
        .setDescription('nazwa bochatera z gry')
        .setRequired(false)
    )
    .addStringOption(option =>
      option
        .setName('hero3')
        .setDescription('nazwa bochatera z gry')
        .setRequired(false)
    ),
  async execute(interaction) {
    const channel = interaction.channel;
    const userId = interaction.user.id;
    const heroNames = ['hero1', 'hero1', 'hero1']
        .map(name => interaction.options.getString(name))
        .filter(Boolean);

    await interaction.deferReply({ ephemeral: true });
    const response = await filterHeroes(userId,heroNames);
    
    const chunks = splitMessage(response);

    await interaction.editReply(chunks[0]);

    for (let i = 1; i < chunks.length; i++) {
      if(!channel){
        await interaction.followUp({ content: chunks[i], ephemeral: true });
      }else{
        await channel.send(chunks[i]);
      }
    }
  },
};
