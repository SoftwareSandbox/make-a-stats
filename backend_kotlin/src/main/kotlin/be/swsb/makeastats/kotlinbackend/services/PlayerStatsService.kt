package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.PlayerName
import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayerStatsService {

    private val playerstats: MutableMap<PlayerName, PlayerStats?> = HashMap()

    fun getByName(playerName: String): PlayerStats? {
        return playerstats.get(playerName)
    }

}


