package be.swsb.makeastats.kotlinbackend.services.db

import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.assertj.core.api.Assertions
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.testing.JdbiRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class PlayerStatsRepoTest {

    @Rule @JvmField
    val db: JdbiRule = JdbiPreparedEmbeddedPostgres.preparedJdbi("db/migration").withPlugins();

    var repo: PlayerStatsRepo? = null

    @Before
    fun setUp() {
        repo = db.jdbi.onDemand()
    }

    @Test
    fun canSavePlayerStats() {
        val playerStats = PlayerStats(UUID.randomUUID(), "shrood", 12, 1, 12f)
        val savedStats = repo!!.insertAndFind(playerStats)
        Assertions.assertThat(savedStats).isEqualTo(playerStats)
    }

    @Test
    fun findById_NoPlayerFound_ReturnsNull() {
        val id = UUID.randomUUID()
        val playerStats = PlayerStats(id, "shrood")
        repo!!.insert(playerStats)

        Assertions.assertThat(repo!!.findById(UUID.randomUUID())).isNull()
    }

    @Test
    fun findById_PlayerFound_ReturnsPlayerStats() {
        val id = UUID.randomUUID()
        val playerStats = PlayerStats(id, "shrood")
        repo!!.insert(playerStats)

        Assertions.assertThat(repo!!.findById(id)).isEqualTo(playerStats)
    }

    @Test
    fun findByName_NoPlayerFound_ReturnsNull() {
        val playerStats = PlayerStats(UUID.randomUUID(), "shrood")
        repo!!.insert(playerStats)

        Assertions.assertThat(repo!!.findByName("chad")).isNull()
    }

    @Test
    fun findByName_PlayerFound_ReturnsPlayerStats() {
        val playerStats = PlayerStats(UUID.randomUUID(), "shrood")
        repo!!.insert(playerStats)

        Assertions.assertThat(repo!!.findByName("shrood")).isEqualTo(playerStats)
    }

    @Test
    fun insertIfNotExists_PlayerAlreadyExists_NoPlayerIsInserted() {
        repo!!.insert(PlayerStats(UUID.randomUUID(),"shroud"))

        repo!!.insertIfNotExistsByName(PlayerStats(UUID.randomUUID(),"shroud"))
        Assertions.assertThat(repo!!.list().map(PlayerStats::player)).containsExactlyInAnyOrder("shroud")
    }

}