package be.swsb.makeastats.kotlinbackend.domain

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class RepoConfig {

    @Bean
    fun jdbi(dataSource: DataSource): Jdbi {
        val jdbi = Jdbi.create(dataSource)
        jdbi.installPlugins()
        return jdbi
    }

    @Bean
    fun playerStatsRepo(jdbi: Jdbi): PlayerStatsRepo {
        return jdbi.onDemand()
    }

    @Bean
    fun leaderboardRepo(jdbi: Jdbi): LeaderboardRepo {
        return jdbi.onDemand()
    }
}