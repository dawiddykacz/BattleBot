package pl.epicserwer.czolg.swgoh.battlebot.drt;

import org.commons.AllyCode;
import org.commons.stats.GamePlayer;
import org.commons.stats.Guild;
import org.commons.stats.GuildMember;
import org.commons.user.UserService;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtEntity;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtRepository;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtUserEntity;
import pl.epicserwer.czolg.swgoh.battlebot.drt.sql.DrtUserRepository;
import pl.epicserwer.czolg.swgoh.battlebot.stats.comlink.parser.StatsParserService;

import java.time.LocalDate;
import java.util.HashMap;

class DrtCommonService implements DrtService {
    private static class DrtUser {
        public final AllyCode allyCode;
        public boolean checked = false;
        public int drt = 0;

        public DrtUser(AllyCode allyCode) {
            this.allyCode = allyCode;
        }
    }

    private final UserService userService;
    private final DrtRepository drtRepository;
    private final DrtUserRepository drtUserRepository;
    private final StatsParserService statsParserService;

    public DrtCommonService(UserService userService, DrtRepository drtRepository,
                            DrtUserRepository drtUserRepository,
                            StatsParserService statsParserService) {
        this.userService = userService;
        this.drtRepository = drtRepository;
        this.drtUserRepository = drtUserRepository;
        this.statsParserService = statsParserService;
    }

    public void a() {
        HashMap<AllyCode, DrtUser> drtUserHashMap = new HashMap<>();

        for (AllyCode allyCode : this.userService.getAllUsersByAllyCode()) {
            drtUserHashMap.put(allyCode, new DrtUser(allyCode));
        }

        for (DrtUser value : drtUserHashMap.values()) {
            if (value.checked) continue;

            final GamePlayer gamePlayer = this.statsParserService.getPlayerFromAllyCode(value.allyCode.toString());
            Guild guild = this.statsParserService.getGuild(gamePlayer.getGuildID().toString());
            for (GuildMember guildMember : guild.getGuildMembers()) {
                final GamePlayer memberData = this.statsParserService.getPlayerFromComlinkId(
                        guildMember.playerId().toString());

                DrtUserEntity drtUserEntity = getDrtUserEntity(memberData.getAllyCode());

                if (drtUserEntity == null) {
                    drtUserEntity = addUserEntity(memberData);
                }

                if (!memberData.getGuildID().toString().equals(drtUserEntity.getGuild())
                        || !memberData.getPlayerName().toString().equals(drtUserEntity.getName())) {
                    drtUserEntity = updateUserEntity(memberData);
                }

                DrtEntity drtEntity = getLastDrtEntity(drtUserEntity);

                if(drtEntity == null) drtEntity = addDrt(drtUserEntity,guildMember);

                if(!LocalDate.now().equals(drtEntity.getDate())) {
                    addDrt(drtUserEntity,guildMember);
                }else if(drtEntity.getDrt() < guildMember.drt().getAmountAsInt()){
                    updateDrt(drtUserEntity,guildMember);
                }
            }
        }
    }

    private DrtUserEntity getDrtUserEntity(AllyCode allyCode) {
        return drtUserRepository.findById(allyCode.toString()).orElse(null);
    }

    private DrtEntity getLastDrtEntity(DrtUserEntity userEntity) {
        return drtRepository.findFirstByUserOrderByDateDesc(userEntity).orElse(null);
    }

    private DrtUserEntity addUserEntity(GamePlayer gamePlayer) {
        DrtUserEntity drtUserEntity = new DrtUserEntity();

        drtUserEntity.setName(gamePlayer.getPlayerName().toString());
        drtUserEntity.setAllyCode(gamePlayer.getAllyCode().toString());
        drtUserEntity.setGuild(gamePlayer.getGuildID().toString());

        drtUserRepository.save(drtUserEntity);
        return drtUserEntity;
    }

    private DrtUserEntity updateUserEntity(GamePlayer gamePlayer) {
        DrtUserEntity drtUserEntity = getDrtUserEntity(gamePlayer.getAllyCode());

        drtUserEntity.setAllyCode(gamePlayer.getAllyCode().toString());
        drtUserEntity.setGuild(gamePlayer.getGuildID().toString());

        drtUserRepository.save(drtUserEntity);
        return drtUserEntity;
    }

    public DrtEntity addDrt(DrtUserEntity userEntity,GuildMember guildMember) {
        DrtEntity drtEntity = new DrtEntity();

        drtEntity.setUser(userEntity);
        drtEntity.setDrt(guildMember.drt().getAmountAsInt());
        drtEntity.setDate(LocalDate.now());

        drtRepository.save(drtEntity);
        return drtEntity;
    }

    public DrtEntity updateDrt(DrtUserEntity userEntity,GuildMember guildMember) {
        DrtEntity drtEntity = getLastDrtEntity(userEntity);

        drtEntity.setDrt(guildMember.drt().getAmountAsInt());

        drtRepository.save(drtEntity);
        return drtEntity;
    }
}
