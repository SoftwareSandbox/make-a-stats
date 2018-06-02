package be.swsb.makeastats.kotlinbackend.test

import com.opentable.db.postgres.embedded.FlywayPreparer
import com.opentable.db.postgres.junit.EmbeddedPostgresRules
import com.opentable.db.postgres.junit.PreparedDbRule
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.testing.JdbiRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class JdbiPreparedEmbeddedPostgresKotlin private constructor(vararg migrationLocations: String) : JdbiRule() {

    private val embeddedPreparedDbRule: PreparedDbRule

    init {
        val locations = migrationLocations.allOrElse(arrayOf("db/migration"))
        embeddedPreparedDbRule = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation(*locations))
    }

    override fun createJdbi(): Jdbi {
        return Jdbi.create(embeddedPreparedDbRule.testDatabase)
    }

    override fun apply(base: Statement, description: Description?): Statement {
        return embeddedPreparedDbRule.apply(super.apply(base, description), description)
    }

    companion object {
        fun preparedJdbi(vararg migrationLocations: String): JdbiPreparedEmbeddedPostgresKotlin {
            return JdbiPreparedEmbeddedPostgresKotlin(*migrationLocations)
        }
    }
}

fun <T> Array<out T>.allOrElse(defaultArray: Array<T>): Array<out T> {
    return if (size > 0) this else defaultArray
}