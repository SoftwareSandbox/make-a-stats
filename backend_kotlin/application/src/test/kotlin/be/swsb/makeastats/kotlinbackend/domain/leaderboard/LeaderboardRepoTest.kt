package be.swsb.makeastats.kotlinbackend.domain.leaderboard

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder.Companion.aLeaderboard
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder.Companion.aLeaderboardWithoutPlayers
import be.swsb.makeastats.kotlinbackend.test.JdbiPreparedEmbeddedPostgresKotlin
import org.assertj.core.api.Assertions
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.testing.JdbiRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class LeaderboardRepoTest {

    @Rule @JvmField
    val db: JdbiRule = JdbiPreparedEmbeddedPostgresKotlin.preparedJdbi("db/migration").withPlugins();

    private lateinit var repo: LeaderboardRepo

    @Before
    fun setUp() {
        repo = db.jdbi.onDemand()
    }

    @Test
    fun canSaveLeaderboard_WithoutPlayers() {
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "UvOiox", "ZF")
        val savedLeaderboard = repo.insertAndFind(leaderboard)
        Assertions.assertThat(savedLeaderboard).isEqualTo(leaderboard)
    }

    @Test
    fun canFindLeaderboardByLeaderboardId() {
        val lid = "UvOiox"
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), lid, "ZF")
        repo.insert(leaderboard)
        val savedLeaderboard = repo.findByLeaderboardId(lid)
        Assertions.assertThat(savedLeaderboard).isEqualTo(leaderboard)
    }

    @Test
    fun cannotFindLeaderboardByLeaderboardId_ReturnsNull() {
        val lid = "UvOiox"
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), lid, "ZF")
        repo.insert(leaderboard)
        val actual = repo.findByLeaderboardId("snarfsnarf")
        Assertions.assertThat(actual).isNull()
    }
}