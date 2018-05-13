package be.swsb.makeastats.kotlinbackend.pubgacl.model

data class Participant(
        val type: String,
        val id: String,
        val attributes: ParticipantAttributues
)

data class ParticipantAttributues(
        val actor: String,
        val shardId: String,
        val stats: ParticipantStats
)

data class ParticipantStats(
        val DBNOs: Int,
        val assists: Int,
        val boosts: Int,
        val damageDealt: Float,
        val deathType: String,
        val headshotKills: Int,
        val heals: Int,
        val killPlace: Int,
        val killPointsDelta: Int,
        val killStreaks: Int,
        val kills: Int,
        val lastKillPoints: Int,
        val lastWinPoints: Int,
        val longestKill: Float,
        val mostDamage: Int,
        val name: String,
        val playerId: String,
        val revives: Int,
        val rideDistance: Float,
        val roadKills: Int,
        val teamKills: Int,
        val timeSurvived: Float,
        val vehicleDestroyers: Int,
        val walkDistance: Float,
        val weaponsAcquired: Int,
        val winPlace: Int,
        val winPointsDelta: Int
)
