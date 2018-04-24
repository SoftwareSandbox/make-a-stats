package com.softwaresandbox.makeastats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/backend/")
public class MakeAStatsController {

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/player/{name}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getPlayer(@PathVariable("name") String playerName) {
        return ok().body(playerService.getPlayerStats(playerName));
    }

}
