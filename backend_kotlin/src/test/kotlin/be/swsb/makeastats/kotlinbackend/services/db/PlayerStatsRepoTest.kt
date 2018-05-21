package be.swsb.makeastats.kotlinbackend.services.db

import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import org.assertj.core.api.Assertions
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.testing.JdbiRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        val playerStats = PlayerStats(1, "shrood", 12, 1, 12f)
        val savedStats = repo?.insertAndFind(playerStats)
        Assertions.assertThat(savedStats).isEqualTo(playerStats)
    }
}