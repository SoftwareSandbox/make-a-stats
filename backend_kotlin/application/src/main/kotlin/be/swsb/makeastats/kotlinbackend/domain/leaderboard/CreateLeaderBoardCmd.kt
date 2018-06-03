package be.swsb.makeastats.kotlinbackend.domain.leaderboard

data class CreateLeaderBoardCmd(val name:String = "",
                                val playerNames:Set<String> = emptySet())
