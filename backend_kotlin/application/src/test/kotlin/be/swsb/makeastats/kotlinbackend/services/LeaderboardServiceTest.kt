package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
import be.swsb.makeastats.kotlinbackend.test.JdbiPreparedEmbeddedPostgresKotlin
import org.assertj.core.api.Assertions
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.testing.JdbiRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class LeaderboardServiceTest {

    @Rule @JvmField
    val db: JdbiRule = JdbiPreparedEmbeddedPostgresKotlin.preparedJdbi().withPlugins()

    private lateinit var leaderboardRepo: LeaderboardRepo
    private lateinit var playerStatsRepo: PlayerStatsRepo
    private lateinit var leaderboardService: LeaderboardService

    @Before
    fun setUp() {
        leaderboardRepo = db.jdbi.onDemand()
        playerStatsRepo = db.jdbi.onDemand()
        leaderboardService = LeaderboardService(leaderboardRepo, playerStatsRepo)
    }

    @Test
    fun handleLeaderboardCreation_CreatesPlayers_CreatesLeaderboard_StartsAsyncPubgCalls() {
        val leaderboard = leaderboardService.handle(CreateLeaderBoardCmd("ZF", setOf("womble", "cyanide")))!!

        val foundLeaderboard = leaderboardRepo.findByLeaderboardId(leaderboard.lid)

        Assertions.assertThat(playerStatsRepo.list().map(PlayerStats::player)).containsOnly("womble","cyanide")
        Assertions.assertThat(foundLeaderboard?.lid).isEqualTo(leaderboard.lid)
        Assertions.assertThat(foundLeaderboard?.name).isEqualTo("ZF")
        //TODO verify pubg calls are made
    }

    @Test
    fun handleLeaderboardCreation_PlayersAlreadyExist_DoesNotRecreatePlayers() {
        val leaderboard = leaderboardService.handle(CreateLeaderBoardCmd("ZF", setOf("womble", "cyanide")))!!
        val leaderboard2 = leaderboardService.handle(CreateLeaderBoardCmd("ZFShroud", setOf("womble", "cyanide", "shroud", "chad")))!!

        val foundLeaderboard = leaderboardRepo.findByLeaderboardId(leaderboard.lid)
        val foundLeaderboard2 = leaderboardRepo.findByLeaderboardId(leaderboard2.lid)
        Assertions.assertThat(foundLeaderboard?.name).isEqualTo("ZF")
        Assertions.assertThat(foundLeaderboard2?.name).isEqualTo("ZFShroud")
        Assertions.assertThat(playerStatsRepo.list().map(PlayerStats::player)).containsExactlyInAnyOrder("womble","cyanide","shroud","chad")
    }

    @Test
    fun findLeaderboardWithPlayers_LeaderboardFound_PlayersFound_ReturnsLeaderboardWithPlayers() {
        val leaderboard = LeaderboardTestBuilder.aLeaderboardWithoutPlayers(UUID.randomUUID(), "UvOiox", "ZF")
        val savedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)

        val player1 = createAndPersistPlayerStats("womble")
        val player2 = createAndPersistPlayerStats("cyanide")

        leaderboardRepo.addPlayersToLeaderboard(savedLeaderboard.lid, listOf(player1.id, player2.id))
        val leaderboardWithPlayers = leaderboardRepo.findLeaderboardWithPlayersByLeaderboardId(savedLeaderboard.lid)

        val actual = leaderboardService.findLeaderboardWithPlayers(savedLeaderboard.lid)!!

        Assertions.assertThat(actual.lid).isEqualTo(savedLeaderboard.lid)
        Assertions.assertThat(actual.id).isEqualTo(savedLeaderboard.id)
        Assertions.assertThat(actual.name).isEqualTo(savedLeaderboard.name)
        Assertions.assertThat(actual.players).isEqualTo(listOf(player1, player2))
    }

    @Test
    fun findLeaderboardWithPlayers_NoLeaderboardFound_ReturnsNull() {
        val actual = leaderboardService.findLeaderboardWithPlayers("snarf")

        Assertions.assertThat(actual).isNull()
    }

    private fun createAndPersistPlayerStats(name: String): PlayerStats {
        val id = UUID.randomUUID()
        val player = PlayerStats(id, name, 12, 1, 12f)

        return playerStatsRepo.insertAndFind(player)
    }
}