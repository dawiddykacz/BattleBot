package org.commons.data.sql;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.NameId;
import org.commons.data.DataUserService;

import java.util.ArrayList;
import java.util.List;

public class SQLDataUserService implements DataUserService {
    private final DiscordUserRepository discordUserRepository;
    private final AllyCodeRepository allyCodeRepository;

    public SQLDataUserService(DiscordUserRepository discordUserRepository, AllyCodeRepository allyCodeRepository) {
        this.discordUserRepository = discordUserRepository;
        this.allyCodeRepository = allyCodeRepository;
    }

    @Transactional
    public void registerUser(@NonNull final NameId discordId, @NonNull final AllyCode allyCode){
        final AllyCodeEntity allyCodeEntity = new AllyCodeEntity();
        allyCodeEntity.setAllyCode(allyCode.toString());

        final DiscordUserEntity discordUser = new DiscordUserEntity();
        discordUser.setDiscordId(discordId.toString());

        allyCodeEntity.setDiscordUser(discordUser);

        discordUser.setAllyCode(allyCodeEntity);
        discordUser.getAllyCodes().add(allyCodeEntity);

        this.discordUserRepository.save(discordUser);
    }

    @Override
    public boolean userExists(@NonNull NameId discordId) {
        return this.discordUserRepository.existsByDiscordId(discordId.toString());
    }

    @Override
    @Transactional
    public void addAllyCode(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        AllyCodeEntity newAllyCode = new AllyCodeEntity();
        newAllyCode.setAllyCode(allyCode.toString());
        newAllyCode.setDiscordUser(user);
        if(user.getAllyCodes().contains(newAllyCode)) throw new IllegalArgumentException("User already has ally code: " + allyCode);

        user.getAllyCodes().add(newAllyCode);

        discordUserRepository.save(user);
    }

    @Override
    @Transactional
    public AllyCode getAllyCode(@NonNull NameId discordId) {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        if(user.getAllyCode() == null) throw new IllegalArgumentException("User has no ally code");
        return new AllyCode(user.getAllyCode().getAllyCode());
    }

    @Override
    @Transactional
    public void deleteAllyCode(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        final AllyCodeEntity allyCodeEntity = new AllyCodeEntity();
        allyCodeEntity.setAllyCode(allyCode.toString());

        if(user.getAllyCode().equals(allyCodeEntity))
            throw new IllegalArgumentException("Cannot delete current ally code.");
        if(!user.getAllyCodes().contains(allyCodeEntity))
            throw new IllegalArgumentException("User does not have ally code: " + allyCode);

        user.getAllyCodes().remove(allyCodeEntity);
    }

    @Override
    @Transactional
    public List<AllyCode> getAllyCodes(@NonNull NameId discordId) throws IllegalArgumentException {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        List<AllyCode> allyCodes = new ArrayList<>();
        for (AllyCodeEntity allyCode : user.getAllyCodes()) {
            allyCodes.add(new AllyCode(allyCode.getAllyCode()));
        }
        return allyCodes;
    }

    @Transactional
    @Override
    public void changeCurrentAllyCode(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        AllyCodeEntity allyCodeEntity = null;
        for (AllyCodeEntity code : user.getAllyCodes()) {
            if(code.getAllyCode().equals(allyCode.toString())) {
                allyCodeEntity = code;
                continue;
            }
        }

        if(allyCodeEntity == null) throw new IllegalArgumentException("Ally code "+allyCode+" not found.");

        user.setAllyCode(allyCodeEntity);

        this.discordUserRepository.save(user);
    }

    @Transactional
    @Override
    public void changeCurrentAllyCode(@NonNull NameId discordId, int index) throws IllegalArgumentException {
        DiscordUserEntity user = discordUserRepository.findByDiscordId(discordId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found with discordId: " + discordId));

        if(user.getAllyCodes().size() <= index) throw new IllegalArgumentException("Ally code's index "+index+" not found.");
        AllyCodeEntity allyCodeEntity = user.getAllyCodes().get(index);
        user.setAllyCode(allyCodeEntity);

        this.discordUserRepository.save(user);

    }

    @Override
    public List<AllyCode> getAllUsersByAllyCode() {
        List<AllyCode> allyCodes = new ArrayList<>();

        for (DiscordUserEntity discordUserEntity : this.discordUserRepository.findAll()) {
            allyCodes.add(new AllyCode(discordUserEntity.getAllyCode().getAllyCode()));
        }

        return allyCodes;
    }
}
