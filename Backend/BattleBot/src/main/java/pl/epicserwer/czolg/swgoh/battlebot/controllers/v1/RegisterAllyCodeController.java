package pl.epicserwer.czolg.swgoh.battlebot.controllers.v1;

import org.commons.queue.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.responses.RequestResultResponse;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.responses.RequestStatusResponse;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.responses.UUIDResponse;
import pl.epicserwer.czolg.swgoh.battlebot.controllers.v1.dto.RegisterAllyCodeDto;
import pl.epicserwer.czolg.swgoh.battlebot.register.AppRegisterService;
import pl.epicserwer.czolg.swgoh.battlebot.register.RegisterResult;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterAllyCodeController {
    private final AppRegisterService appRegisterService;

    @Autowired
    public RegisterAllyCodeController(AppRegisterService appRegisterService) {
        this.appRegisterService = appRegisterService;
    }

    @PostMapping
    public ResponseEntity<UUIDResponse> addAllyCode(@RequestBody RegisterAllyCodeDto registerAllyCodeDto) {
        final String uuid = this.appRegisterService.addRegisterRequest(
                registerAllyCodeDto.getDiscordId(),registerAllyCodeDto.getAllyCode());
        return ResponseEntity.ok(new UUIDResponse(uuid));
    }

    @GetMapping
    public ResponseEntity<RequestStatusResponse> getStatus(@RequestParam String uuid){
        final RequestStatus requestStatus = this.appRegisterService.getStatus(uuid);
        return ResponseEntity.ok(new RequestStatusResponse(requestStatus.name().toLowerCase()));
    }
    @GetMapping("/result")
    public ResponseEntity<RequestResultResponse> getResult(@RequestParam String uuid){
        final RegisterResult registerResult = this.appRegisterService.getResult(uuid);
        return ResponseEntity.ok(new RequestResultResponse(registerResult.getMessage()));
    }


}
