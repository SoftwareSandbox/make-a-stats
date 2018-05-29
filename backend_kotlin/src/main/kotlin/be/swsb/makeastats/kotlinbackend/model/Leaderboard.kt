package be.swsb.makeastats.kotlinbackend.model

import be.swsb.makeastats.kotlinbackend.util.hashids.Hashids
import java.util.*

/**
 * @param id: database id; do not expose
 * @param lid: exposed hashId (to use in url's)
 * @param name: name of the leaderboard
 * @param players: list of players that belong to this leaderboard
 */
data class Leaderboard(val id: UUID,
                       val lid: LeaderboardId,
                       val name: String,
                       val players: List<PlayerStatsId>?) {
    constructor(cmd: CreateLeaderBoardCmd) : this(UUID.randomUUID(), generateUniqueId(), cmd.name)
    //TODO look up if one can extend a class with a constructor, because this constructor should only be used by tests
    constructor(id:UUID, lid: LeaderboardId, name: String) : this(UUID.randomUUID(), lid, name, null)

    companion object {
        fun generateUniqueId(): LeaderboardId {
            val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            return Hashids("GettingSnipedWhileLooting", 6, alphabet).encode(Random().nextInt(alphabet.length).toLong())
        }
    }
}

typealias LeaderboardId = String