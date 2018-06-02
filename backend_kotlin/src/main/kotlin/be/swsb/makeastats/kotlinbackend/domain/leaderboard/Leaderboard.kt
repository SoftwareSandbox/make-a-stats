package be.swsb.makeastats.kotlinbackend.domain.leaderboard

import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsId
import be.swsb.makeastats.kotlinbackend.util.hashids.Hashids
import java.util.*

/**
 * @param id: database id; do not expose
 * @param lid: exposed hashId (to use in url's)
 * @param name: name of the leaderboard
 * @param players: list of players that belong to this leaderboard
 */
data class Leaderboard internal constructor(val id: UUID,
                                            val lid: LeaderboardHashId,
                                            val name: String,
                                            val players: List<PlayerStatsId>?) {
    constructor(cmd: CreateLeaderBoardCmd) : this(UUID.randomUUID(), generateUniqueId(), cmd.name, null)

    companion object {
        fun generateUniqueId(): LeaderboardHashId {
            val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            return Hashids("GettingSnipedWhileLooting", 6, alphabet).encode(Random().nextInt(alphabet.length).toLong())
        }
    }
}

typealias LeaderboardHashId = String