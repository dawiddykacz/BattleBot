package org.commons.user;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.NameId;
import org.commons.data.DataUserService;

import java.util.List;

class UserCommonService implements UserService {
    private final DataUserService dataUserService;

    public UserCommonService(@NonNull final DataUserService dataUserService) {
        this.dataUserService = dataUserService;
    }

    @Override
    public void registerUser(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        if(this.dataUserService.userExists(discordId)) throw new IllegalArgumentException("User already exists");
        this.dataUserService.registerUser(discordId, allyCode);
    }

    @Override
    public void addAllyCode(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        if(!this.dataUserService.userExists(discordId)) throw new IllegalArgumentException("User is not exists");
        this.dataUserService.addAllyCode(discordId, allyCode);
    }

    @Override
    public AllyCode getAllyCode(@NonNull NameId discordId) throws IllegalArgumentException {
        if(!this.dataUserService.userExists(discordId)) throw new IllegalArgumentException("User is not exists");
        return this.dataUserService.getAllyCode(discordId);
    }

    @Override
    public List<AllyCode> getAllyCodes(@NonNull NameId discordId) throws IllegalArgumentException {
        if(!this.dataUserService.userExists(discordId)) throw new IllegalArgumentException("User is not exists");
        return this.dataUserService.getAllyCodes(discordId);
    }

    @Override
    public void deleteAllyCode(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        if(!this.dataUserService.userExists(discordId)) throw new IllegalArgumentException("User is not exists");

        this.dataUserService.deleteAllyCode(discordId, allyCode);
    }

    @Override
    public void changeCurrentAllyCode(@NonNull NameId discordId, int index) throws IllegalArgumentException {
        this.dataUserService.changeCurrentAllyCode(discordId, index);
    }

    @Override
    public void changeCurrentAllyCode(@NonNull NameId discordId, @NonNull AllyCode allyCode) throws IllegalArgumentException {
        this.dataUserService.changeCurrentAllyCode(discordId, allyCode);
    }

    @Override
    public List<AllyCode> getAllUsersByAllyCode() {
        return this.dataUserService.getAllUsersByAllyCode();
    }
}
