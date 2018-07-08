package be.swsb.makeastats.kotlinbackend.domain.playerstats

import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*

interface PlayerStatsRepo {
    @SqlUpdate("insert into playerstats (id, player, totalKills, amountOfMatchesPlayed, killsPerMatch) " +
            "values (:playerstats.id, :playerstats.player, :playerstats.totalKills , :playerstats.amountOfMatchesPlayed , :playerstats.killsPerMatch)")
    fun insert(playerstats: PlayerStats)

    @SqlQuery("select id, player, totalKills, amountOfMatchesPlayed, killsPerMatch from playerstats")
    fun list(): List<PlayerStats>

    @SqlQuery("select id, player, totalKills, amountOfMatchesPlayed, killsPerMatch from playerstats where id=:id")
    fun findById(id: UUID): PlayerStats?

    //playername is unique per shard, but we're only keeping data from EU for now.
    @SqlQuery("select id, player, totalKills, amountOfMatchesPlayed, killsPerMatch from playerstats where player=:playername")
    fun findByName(playername: String): PlayerStats?

    fun insertAndFind(playerStats: PlayerStats): PlayerStats {
        insert(playerStats)
        return findById(playerStats.id)!!
    }

    fun insertIfNotExistsByName(playerStats: PlayerStats): PlayerStats {
        return this.findByName(playerStats.player) ?: this.insertAndFind(playerStats)
    }
}