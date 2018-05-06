package be.swsb.makeastats.kotlinbackend.controllers

import be.swsb.makeastats.kotlinbackend.model.Leaderboard
import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("leaderboard")
class LeaderboardController {

    @GetMapping("{id}")
    fun leaderboard(@PathVariable(value = "id", required = true) id: String): Leaderboard {
        val playerStats = listOf(PlayerStats("gianni",1,1,1f))
        return Leaderboard(playerStats)
    }
}