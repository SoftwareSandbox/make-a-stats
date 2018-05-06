package be.swsb.makeastats.kotlinbackend.model

import java.util.*

data class Leaderboard(val id:String, val players: List<PlayerStats>) {
    constructor(cmd: CreateLeaderBoardCmd) : this(UUID.randomUUID().toString(), PlayerStats.fromPlayernames(cmd.playerNames))
}