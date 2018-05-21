package be.swsb.makeastats.kotlinbackend.model

import java.util.*

data class PlayerStats(val id: PlayerStatsId,
                       val player: String,
                       val totalKills: Int? = 0,
                       val amountOfMatchesPlayed: Int? = 0,
                       val killsPerMatch: Float? = 0f) {
    companion object {
        fun fromPlayernames(names: Iterable<String>): List<PlayerStats> =
                names.map { name -> PlayerStats(UUID.randomUUID(), name) }
    }
}

typealias PlayerName = String
typealias PlayerStatsId = UUID