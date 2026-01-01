package pl.epicserwer.czolg.swgoh.battlebot.controllers.v1;

import org.commons.queue.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.responses.RequestStatusResponse;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.responses.UUIDResponse;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.FilterHeroesByStatsDto;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.FilterHeroesByStatsResponseDto;
import pl.epicserwer.czolg.swgoh.battlebot.filter.FilterService;

@RestController
@RequestMapping("/api/v1/filter/hero")
public class FilterController {
    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @PostMapping
    public ResponseEntity<UUIDResponse> addAllyCode(@RequestBody FilterHeroesByStatsDto filterHeroesByStatsDto) {
        final String uuid = this.filterService.addFilterRequest(filterHeroesByStatsDto.getDiscordId(),
                filterHeroesByStatsDto.getHeroNames());
        return ResponseEntity.ok(new UUIDResponse(uuid));
    }

    @GetMapping
    public ResponseEntity<RequestStatusResponse> getStatus(@RequestParam String uuid){
        final RequestStatus requestStatus = this.filterService.getStatus(uuid);
        return ResponseEntity.ok(new RequestStatusResponse(requestStatus.name().toLowerCase()));
    }
    @GetMapping("/result")
    public ResponseEntity<FilterHeroesByStatsResponseDto> getResult(@RequestParam String uuid){
        return ResponseEntity.ok(this.filterService.getResult(uuid));
    }
}
