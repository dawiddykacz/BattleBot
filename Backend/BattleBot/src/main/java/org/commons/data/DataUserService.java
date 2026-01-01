package org.commons.data;

import lombok.NonNull;
import org.commons.AllyCode;
import org.commons.NameId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataUserService {
    void registerUser(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    boolean userExists(@NonNull final NameId discordId) throws IllegalArgumentException;
    void addAllyCode(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    AllyCode getAllyCode(@NonNull final NameId discordId) throws IllegalArgumentException;
    void deleteAllyCode(@NonNull final NameId discordId,@NonNull final AllyCode allyCode) throws IllegalArgumentException;
    List<AllyCode> getAllyCodes(@NonNull final NameId discordId) throws IllegalArgumentException;
    void changeCurrentAllyCode(@NonNull final NameId discordId, @NonNull final AllyCode allyCode) throws IllegalArgumentException;
    void changeCurrentAllyCode(@NonNull final NameId discordId, final int index) throws IllegalArgumentException;
    List<AllyCode> getAllUsersByAllyCode();
}
