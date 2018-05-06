package be.swsb.makeastats.kotlinbackend.services

import be.swsb.makeastats.kotlinbackend.model.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.model.Leaderboard
import org.springframework.stereotype.Service
import java.util.*

typealias LeaderboardId = String

@Service
class LeaderboardService {

    private val leaderboards: MutableMap<LeaderboardId, Optional<Leaderboard>> = HashMap()

    fun handle(cmd: CreateLeaderBoardCmd): Leaderboard {
        val leaderboard = Leaderboard(cmd)
        return leaderboards.getOrPut(leaderboard.id, { Optional.of(leaderboard) }).get()
    }

    fun getById(id: LeaderboardId): Optional<Leaderboard> {
        return leaderboards.getOrDefault(id, Optional.empty())
    }
}