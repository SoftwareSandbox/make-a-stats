package com.softwaresandbox.makeastats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

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
