package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.model.Leaderboard
import be.swsb.makeastats.kotlinbackend.model.LeaderboardId
import org.springframework.stereotype.Service
import java.util.*

@Service
class LeaderboardService {

    private val leaderboards: MutableMap<LeaderboardId, Optional<Leaderboard>> = HashMap()

    fun handle(cmd: CreateLeaderBoardCmd): Leaderboard {
        val leaderboard = Leaderboard(cmd)
        return leaderboards.getOrPut(leaderboard.lid, { Optional.of(leaderboard) }).get()
    }

    fun getById(lid: LeaderboardId): Optional<Leaderboard> {
        return leaderboards.getOrDefault(lid, Optional.empty())
    }
}