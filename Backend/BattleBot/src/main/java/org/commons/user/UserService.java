package org.commons.user;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.NameId;

import java.util.List;

public interface UserService {
    void registerUser(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    void addAllyCode(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    AllyCode getAllyCode(@NonNull final NameId discordId) throws IllegalArgumentException;
    void deleteAllyCode(@NonNull final NameId discordId,@NonNull final AllyCode allyCode) throws IllegalArgumentException;
    List<AllyCode> getAllyCodes(@NonNull final NameId discordId) throws IllegalArgumentException;
    void changeCurrentAllyCode(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    void changeCurrentAllyCode(@NonNull final NameId discordId, final int index) throws IllegalArgumentException;
    List<AllyCode> getAllUsersByAllyCode();
}
