package be.swsb.makeastats.kotlinbackend.pubgacl.model

data class Asset(
        val type: String,
        val id: String,
        val attributes: AssetAttributes?
)

data class AssetAttributes(
        val URL: String,
        val createdAt: String,
        val description: String,
        val name: String
)