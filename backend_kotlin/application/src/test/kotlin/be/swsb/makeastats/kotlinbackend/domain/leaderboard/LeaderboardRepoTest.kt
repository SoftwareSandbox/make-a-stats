package be.swsb.makeastats.kotlinbackend.domain.leaderboard

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder.Companion.aLeaderboard
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder.Companion.aLeaderboardWithoutPlayers
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
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

    private lateinit var leaderboardRepo: LeaderboardRepo
    private lateinit var playerStatsRepo: PlayerStatsRepo

    @Before
    fun setUp() {
        leaderboardRepo = db.jdbi.onDemand()
        playerStatsRepo = db.jdbi.onDemand()
    }

    @Test
    fun canSaveLeaderboard_WithoutPlayers() {
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "UvOiox", "ZF")
        val savedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)

        Assertions.assertThat(savedLeaderboard).isEqualTo(leaderboard)
    }

    @Test
    fun canAddPlayersToLeaderboard() {
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "UvOiox", "ZF")
        val savedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)

        val playerId1 = createAndPersistPlayerStats("womble").id
        val playerId2 = createAndPersistPlayerStats("cyanide").id

        leaderboardRepo.addPlayersToLeaderboard(savedLeaderboard.lid, listOf(playerId1, playerId2))
    }

    @Test
    fun canFindLeaderboardWithPlayers_LeaderboardWithGivenLeaderboardIdExists() {
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "UvOiox", "ZF")
        val savedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)

        val player1 = createAndPersistPlayerStats("womble")
        val player2 = createAndPersistPlayerStats("cyanide")

        leaderboardRepo.addPlayersToLeaderboard(savedLeaderboard.lid, listOf(player1.id, player2.id))
        val leaderboardWithPlayers = leaderboardRepo.findLeaderboardWithPlayersByLeaderboardId(savedLeaderboard.lid)

        Assertions.assertThat(leaderboardWithPlayers).containsOnly(LeaderboardRepo.LeaderboardPlayer(leaderboard.lid, player1.id), LeaderboardRepo.LeaderboardPlayer(leaderboard.lid, player2.id))
    }

    @Test
    fun cannotFindLeaderboardWithPlayers_LeaderboardWithGivenLeaderboardIdDoesNotExist() {
        val actual = leaderboardRepo.findLeaderboardWithPlayersByLeaderboardId("snarfsnarf")

        Assertions.assertThat(actual).isEmpty()
    }

    @Test
    fun canFindLeaderboardByLeaderboardId() {
        val lid = "UvOiox"
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), lid, "ZF")
        leaderboardRepo.insert(leaderboard)
        val savedLeaderboard = leaderboardRepo.findByLeaderboardId(lid)

        Assertions.assertThat(savedLeaderboard).isEqualTo(leaderboard)
    }

    @Test
    fun cannotFindLeaderboardByLeaderboardId_ReturnsNull() {
        val lid = "UvOiox"
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), lid, "ZF")
        leaderboardRepo.insert(leaderboard)
        val actual = leaderboardRepo.findByLeaderboardId("snarfsnarf")

        Assertions.assertThat(actual).isNull()
    }

    private fun createAndPersistPlayerStats(name: String): PlayerStats {
        val id = UUID.randomUUID()
        val player = PlayerStats(id, name, 12, 1, 12f)

        return playerStatsRepo.insertAndFind(player)
    }
}