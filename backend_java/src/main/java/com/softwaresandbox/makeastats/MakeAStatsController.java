package com.softwaresandbox.makeastats;

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

    @GetMapping(value = "/player/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getPlayer(@PathVariable("id") String playerId) {
        return ok().body("Retrieving stats for player not implemented yet. Made request for player=" + playerId);
    }

}
