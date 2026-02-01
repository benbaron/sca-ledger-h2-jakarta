package org.nonprofitbookkeeping.app;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main
{
    private static final Logger LOGGER =
        LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        // Run schema migrations (H2 file DB at ./data/sca-ledger)
        Flyway flyway = Flyway.configure()
            .dataSource("jdbc:h2:file:./data/sca-ledger;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH", "sa", "")
            .locations("classpath:db/migration")
            .load();

        flyway.migrate();

        try (SeContainer container = SeContainerInitializer.newInstance().initialize())
        {
            LOGGER.info("Bootstrapped CDI container: {}", container);
            LOGGER.info("Database migrated and ready.");
        }
    }
}
