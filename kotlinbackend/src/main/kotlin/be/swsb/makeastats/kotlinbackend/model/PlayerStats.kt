package be.swsb.makeastats.kotlinbackend.model

data class PlayerStats(val player:String, val totalKills:Int, val amountOfMatchesPlayed:Int, val killsPerMatch:Float)