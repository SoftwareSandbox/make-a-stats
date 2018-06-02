package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.model.Leaderboard
import be.swsb.makeastats.kotlinbackend.model.LeaderboardHashId
import be.swsb.makeastats.kotlinbackend.model.PlayerStats
import be.swsb.makeastats.kotlinbackend.services.db.LeaderboardRepo
import be.swsb.makeastats.kotlinbackend.services.db.PlayerStatsRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class LeaderboardService(val leaderboardRepo: LeaderboardRepo,
                         val playerStatsRepo: PlayerStatsRepo) {

    private val leaderboards: MutableMap<LeaderboardHashId, Optional<Leaderboard>> = HashMap()

    fun handle(cmd: CreateLeaderBoardCmd): Leaderboard {
        val leaderboard = Leaderboard(cmd)
        val persistedLeaderboard = leaderboardRepo.insertAndFind(leaderboard)
        PlayerStats.fromPlayernames(cmd.playerNames).forEach(playerStatsRepo::insertIfNotExistsByName)
        return leaderboards.getOrPut(persistedLeaderboard.lid, { Optional.of(persistedLeaderboard) }).get()
    }

    fun getById(lid: LeaderboardHashId): Leaderboard? {
        return leaderboardRepo.findByLeaderboardId(lid)
    }
}