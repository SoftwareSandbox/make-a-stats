package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.domain.leaderboard.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.Leaderboard
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardHashId
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStats
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.domain.playerstats.PlayerStatsRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class LeaderboardService(val leaderboardRepo: LeaderboardRepo,
                         val playerStatsRepo: PlayerStatsRepo) {

    fun handle(cmd: CreateLeaderBoardCmd): Leaderboard? {
        val leaderboard = Leaderboard(cmd)
        val persistedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)
        PlayerStats.fromPlayernames(cmd.playerNames).forEach(playerStatsRepo::insertIfNotExistsByName)
        return persistedLeaderboard
    }

    fun getById(lid: LeaderboardHashId): Leaderboard? {
        return leaderboardRepo.findByLeaderboardId(lid)
    }
}