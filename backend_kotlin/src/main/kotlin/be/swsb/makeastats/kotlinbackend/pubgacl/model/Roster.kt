package be.swsb.makeastats.kotlinbackend.pubgacl.model

data class Roster(
        val id: String,
        val attributes: RosterAttributes?,
        val relationships: RosterRelationships?
)

data class RosterAttributes(
        val sharedId: String,
        val stats: Any?,
        val won: String?
)

data class RosterRelationships(
        val participants: List<Participant?>?,
        val team: Any?
)

data class Stats(
        val rank: Int,
        val teamId: Int
)