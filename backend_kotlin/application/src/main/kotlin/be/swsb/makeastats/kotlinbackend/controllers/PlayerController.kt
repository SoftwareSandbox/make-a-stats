package be.swsb.makeastats.kotlinbackend.controllers

import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import be.swsb.makeastats.kotlinbackend.services.PlayerStatsService
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("player", produces = [APPLICATION_JSON_UTF8_VALUE])
class PlayerController(val playerStatsStatsService: PlayerStatsService) {

    @GetMapping("{name}")
    fun getByName(@PathVariable("name") playerName: String): ResponseEntity<PlayerStats> {
        return playerStatsStatsService.getByName(playerName)
                ?.let { ResponseEntity.accepted().body(it) }
                ?: ResponseEntity.notFound().build()
    }
}