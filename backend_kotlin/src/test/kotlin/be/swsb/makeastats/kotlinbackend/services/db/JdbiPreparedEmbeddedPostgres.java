package be.swsb.makeastats.kotlinbackend.services.db;

import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class JdbiPreparedEmbeddedPostgres extends JdbiRule {

    private final PreparedDbRule embeddedPreparedDbRule;

    private JdbiPreparedEmbeddedPostgres(final String... migrationLocations) {
        String[] locations = migrationLocations != null && migrationLocations.length > 0
                                ? migrationLocations
                                : new String[]{"db/migration"};
        embeddedPreparedDbRule = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation(locations));
    }

    public static JdbiPreparedEmbeddedPostgres preparedJdbi(String... migrationLocations){
        return new JdbiPreparedEmbeddedPostgres(migrationLocations);
    }

    @Override
    protected Jdbi createJdbi() {
        return Jdbi.create(embeddedPreparedDbRule.getTestDatabase());
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return embeddedPreparedDbRule.apply(super.apply(base, description), description);
    }
}
