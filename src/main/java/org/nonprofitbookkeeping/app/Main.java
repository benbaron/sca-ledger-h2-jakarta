package org.nonprofitbookkeeping.app;

import org.flywaydb.core.Flyway;
import org.nonprofitbookkeeping.service.CoaFundIo;
import org.nonprofitbookkeeping.service.FundBalanceService;
import org.nonprofitbookkeeping.service.JournalLine;
import org.nonprofitbookkeeping.service.PostingService;
import org.nonprofitbookkeeping.service.PostingService.SplitInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Minimal CLI bootstrap.
 *
 * For now, this only:
 * - runs Flyway migrations
 * - boots CDI
 * - demonstrates how to call services (commented examples)
 *
 * Next steps (typical):
 * - add picocli and real CLI commands
 * - or switch Main to launch JavaFX UI
 */
public class Main
{
    private static final Logger LOGGER =
        LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception
    {
        Flyway flyway = Flyway.configure()
            .dataSource("jdbc:h2:file:./data/sca-ledger;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH", "sa", "")
            .locations("classpath:db/migration")
            .load();

        flyway.migrate();

        try (SeContainer container = SeContainerInitializer.newInstance().initialize())
        {
            LOGGER.info("Database migrated and CDI bootstrapped.");

            // Grab services from CDI (works in SE)
            PostingService posting = container.select(PostingService.class).get();
            FundBalanceService fundBalances = container.select(FundBalanceService.class).get();
            CoaFundIo io = container.select(CoaFundIo.class).get();

            LOGGER.info("Services ready: {}, {}, {}", posting, fundBalances, io);

            // Example usage (requires that you have already inserted some Accounts and Funds):
            //
            // Txn txn = posting.post(
            //     LocalDate.now(),
            //     null,
            //     "Example posted transaction",
            //     null,
            //     List.of(
            //         new SplitInput( /* accountId */ 1L, /* fundId */ 1L, new BigDecimal("10.00"), null, null, false, null),
            //         new SplitInput( /* accountId */ 2L, /* fundId */ 1L, new BigDecimal("-10.00"), null, null, false, null)
            //     )
            // );
            //
            // List<JournalLine> journal = posting.journalForTxn(txn.getId());
            // LOGGER.info("Journal lines: {}", journal.size());
            //
            // LOGGER.info("Fund balances as of today: {}", fundBalances.balancesAsOf(LocalDate.now()).size());
        }
    }
}
