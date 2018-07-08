package be.swsb.makeastats.kotlinbackend.domain.leaderboard

import java.util.*

class LeaderboardTestBuilder private constructor() {
    companion object {
        fun aLeaderboardWithoutPlayers(id:UUID, lid: LeaderboardHashId, name: String): Leaderboard {
            return Leaderboard(id, lid, name)
        }

        fun aLeaderboard(): LeaderboardTestBuilder {
            return LeaderboardTestBuilder()
        }
    }

    private var id: UUID? = null
    private var lid: LeaderboardHashId? = null
    private var name: String? = null

    fun build(): Leaderboard {
        return Leaderboard(id!!, lid!!, name!!)
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
}