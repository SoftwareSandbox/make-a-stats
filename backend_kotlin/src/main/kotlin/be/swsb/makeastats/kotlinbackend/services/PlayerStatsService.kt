package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.springframework.stereotype.Service
import java.util.*

typealias PlayerName = String

@Service
class PlayerStatsService {

    private val playerstats: MutableMap<PlayerName, Optional<PlayerStats>> = HashMap()

    fun getByName(playerName: String): Optional<PlayerStats> {
        return playerstats.getOrDefault(playerName, Optional.empty())
    }

}


