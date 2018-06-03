package be.swsb.makeastats.testcdc

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.Leaderboard
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardHashId
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
import be.swsb.makeastats.kotlinbackend.services.LeaderboardService
import com.nhaarman.mockito_kotlin.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.*
import javax.sql.DataSource

@Configuration
open class HttpBaseConfig {

    @Bean
    @Primary
    open fun leaderboardService(): LeaderboardService {
        return LeaderboardServiceStub(leaderboardRepo(), playerStatsRepo())
    }

    @Bean
    @Primary
    open fun playerStatsRepo(): PlayerStatsRepo {
        return mock()
    }

    @Bean
    @Primary
    open fun leaderboardRepo(): LeaderboardRepo {
        return mock()
    }

    @Bean
    @Primary
    open fun datasource(): DataSource {
        return mock()
    }
}

class LeaderboardServiceStub(override val leaderboardRepo: LeaderboardRepo,
                             override val playerStatsRepo: PlayerStatsRepo)
    : LeaderboardService(leaderboardRepo, playerStatsRepo) {

    private val leaderboards: MutableMap<LeaderboardHashId, Leaderboard> = HashMap()

    override fun handle(cmd: CreateLeaderBoardCmd): Leaderboard? {
        return Leaderboard(CreateLeaderBoardCmd("shroudSquad", setOf("shrood","wadu")))
    }

    override fun getById(lid: LeaderboardHashId): Leaderboard? {
        return Leaderboard(CreateLeaderBoardCmd("shroudSquad", setOf("shrood","wadu")))
    }
}
