package be.swsb.makeastats.kotlinbackend

import com.opentable.db.postgres.embedded.FlywayPreparer
import com.opentable.db.postgres.embedded.PreparedDbProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class EmbeddedPostgresConfig {

    @Bean
    fun dataSource(): DataSource {
        return PreparedDbProvider
                .forPreparer(FlywayPreparer.forClasspathLocation("db/migration"))
                .createDataSource()
    }
}