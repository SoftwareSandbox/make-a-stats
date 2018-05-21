package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import be.swsb.makeastats.kotlinbackend.services.db.JdbiPreparedEmbeddedPostgres
import be.swsb.makeastats.kotlinbackend.services.db.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.services.db.PlayerStatsRepo
import org.assertj.core.api.Assertions
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.testing.JdbiRule
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class LeaderboardServiceTest {

    @Rule @JvmField
    val db: JdbiRule = JdbiPreparedEmbeddedPostgres.preparedJdbi().withPlugins()

    private var leaderboardRepo: LeaderboardRepo? = null
    private var playerStatsRepo: PlayerStatsRepo? = null
    private var leaderboardService: LeaderboardService? = null

    @Before
    fun setUp() {
        leaderboardRepo = db.jdbi.onDemand()
        playerStatsRepo = db.jdbi.onDemand()
        leaderboardService = LeaderboardService(leaderboardRepo!!, playerStatsRepo!!)
    }

    @Test
    fun handleLeaderboardCreation_CreatesLeaderboard_CreatesPlayers_StartsAsyncPubgCalls() {
        val leaderboard = leaderboardService!!.handle(CreateLeaderBoardCmd("ZF", setOf("womble", "cyanide")))

        val foundLeaderboard = leaderboardRepo!!.findByLeaderboardId(leaderboard.lid)

        Assertions.assertThat(foundLeaderboard?.lid).isEqualTo(leaderboard.lid)
        Assertions.assertThat(foundLeaderboard?.name).isEqualTo("ZF")
        Assertions.assertThat(playerStatsRepo!!.list().map(PlayerStats::player)).containsOnly("womble","cyanide")
    }

    @Test
    @Ignore
    fun handleLeaderboardCreation_PlayersAlreadyExist_DoesNotRecreatePlayers() {
        //todo: write test
    }
}