package be.swsb.makeastats.kotlinbackend.model

data class CreateLeaderBoardCmd(val name:String = "",
                                val playerNames:Set<String> = emptySet())
