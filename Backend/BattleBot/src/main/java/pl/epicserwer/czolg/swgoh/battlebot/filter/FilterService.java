package pl.epicserwer.czolg.swgoh.battlebot.filter;

import lombok.NonNull;
import org.commons.queue.RequestStatus;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.FilterHeroesByStatsResponseDto;

public interface FilterService {
    String addFilterRequest(final String discordId, @NonNull final String[] heroNameArray);
    RequestStatus getStatus(@NonNull final String requestId);
    FilterHeroesByStatsResponseDto getResult(@NonNull final String requestId);
}
