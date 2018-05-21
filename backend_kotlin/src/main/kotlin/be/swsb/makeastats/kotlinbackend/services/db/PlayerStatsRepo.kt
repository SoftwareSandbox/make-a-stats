package be.swsb.makeastats.kotlinbackend.services.db

import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlayerStatsRepo {
    @SqlUpdate("insert into playerstats (id, player, totalKills, amountOfMatchesPlayed, killsPerMatch) " +
            "values (:playerstats.id, :playerstats.player, :playerstats.totalKills , :playerstats.amountOfMatchesPlayed , :playerstats.killsPerMatch)")
    fun insert(playerstats: PlayerStats)

    @SqlQuery("select id, player, totalKills, amountOfMatchesPlayed, killsPerMatch from playerstats")
    fun list(): List<PlayerStats>

    @SqlQuery("select id, player, totalKills, amountOfMatchesPlayed, killsPerMatch from playerstats where id=:id")
    fun findById(id: UUID): PlayerStats

    fun insertAndFind(playerStats: PlayerStats): PlayerStats {
        insert(playerStats)
        return findById(playerStats.id)
    }
}