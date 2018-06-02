package be.swsb.makeastats.kotlinbackend.model

import java.util.*

class LeaderboardTestBuilder private constructor() {
    companion object {
        fun aLeaderboard(id:UUID, lid: LeaderboardHashId, name: String): Leaderboard{
            return Leaderboard(id, lid, name, null)
        }

        fun aLeaderboard(): LeaderboardTestBuilder {
            return LeaderboardTestBuilder()
        }
    }

    private var id: UUID? = null
    private var lid: LeaderboardHashId? = null
    private var name: String? = null
    private var players: List<PlayerStatsId>? = null

    fun build(): Leaderboard {
        return Leaderboard(id!!, lid!!, name!!, players)
    }

    fun withId(id: UUID): LeaderboardTestBuilder {
        this.id = id
        return this
    }

    fun withLid(lid: LeaderboardHashId): LeaderboardTestBuilder {
        this.lid = lid
        return this
    }

    fun withName(name: String): LeaderboardTestBuilder {
        this.name = name
        return this
    }

    fun withPlayers(players: List<PlayerStatsId>): LeaderboardTestBuilder {
        this.players = players
        return this
    }
}