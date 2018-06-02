package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.PlayerName
import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import be.swsb.makeastats.kotlinbackend.services.db.PlayerStatsRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayerStatsService(val playerStatsRepo: PlayerStatsRepo) {

    private val playerstats: MutableMap<PlayerName, PlayerStats?> = HashMap()

    fun getByName(playerName: String): PlayerStats? {
        return playerStatsRepo.findByName(playerName)
    }

}


