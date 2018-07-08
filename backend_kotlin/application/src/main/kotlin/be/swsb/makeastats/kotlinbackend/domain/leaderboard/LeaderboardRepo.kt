package be.swsb.makeastats.kotlinbackend.domain.leaderboard

import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsId
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*

interface LeaderboardRepo {
    @SqlUpdate("insert into leaderboard (id, lid, name) " +
            "values (:leaderboard.id, :leaderboard.lid, :leaderboard.name)")
    fun insert(leaderboard: Leaderboard)

    @SqlQuery("select id, lid, name from leaderboard")
    fun list(): List<Leaderboard>

    @SqlQuery("select id, lid, name from leaderboard where id=:id")
    fun findById(id: UUID): Leaderboard

    @SqlQuery("select id, lid, name from leaderboard where lid=:lid")
    fun findByLeaderboardId(lid: LeaderboardHashId): Leaderboard?

    fun insertAndFind(leaderboard: Leaderboard): Leaderboard {
        insert(leaderboard)
        return findById(leaderboard.id)
    }

    @SqlUpdate("insert into leaderboardplayers (lid, playerid) " +
            "values (:lid, :playerId)")
    fun insert(lid: LeaderboardHashId, playerId: UUID)

    fun addPlayersToLeaderboard(lid: LeaderboardHashId, playerIds: List<PlayerStatsId>) {
        playerIds.forEach { insert(lid, it) }
    }

    @SqlQuery("select lid, playerid from leaderboardplayers where lid=:lid")
    fun findLeaderboardWithPlayersByLeaderboardId(lid: LeaderboardHashId): List<LeaderboardPlayer>

    data class LeaderboardPlayer(val lid: LeaderboardHashId, val playerId: PlayerStatsId)
}