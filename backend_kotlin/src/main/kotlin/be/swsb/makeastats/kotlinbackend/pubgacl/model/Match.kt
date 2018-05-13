package be.swsb.makeastats.kotlinbackend.pubgacl.model

data class Match(
        val type: String,
        val id: String,
        val attributes: MatchAttributes,
        val relationships: MatchRelationships,
        val links: MatchLinks
)

data class MatchAttributes(
        val createdAt: String,
        val duration: Int,
        val gameMode: String,
        val patchVersion: String,
        val sharedId: String,
        val stats: Any?,  // null
        val tags: Any?,  // null
        val tagId: String
)

data class MatchRelationships(
        val assets: Data<List<MatchAssetsData>>?,
        val rosters: Data<List<MatchRelationshipRosters>>?,
        val rounds: Data<List<Any>>?,
        val spectators: Data<List<Any>>?
)

data class MatchAssetsData(
        val type: String,
        val id: String
)

data class MatchRelationshipRosters(
        val type: String,
        val id: String
)

data class MatchLinks(
        val schema: String?,
        val self: String?
)