package ibee.webapp.todo_app.core.service.cleanup;

import ibee.webapp.todo_app.core.repository.AddressRepository;
import ibee.webapp.todo_app.core.repository.CompanyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataGarbageCollectionService {

    private static final Logger log = LoggerFactory.getLogger(DataGarbageCollectionService.class);

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public DataGarbageCollectionService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

// How this Cron Expression works: 0 0 2 1 */4 ?
// Spring Boot cron expressions use 6 fields: second minute hour day-of-month month day-of-week

// 0 = 0th second

// 0 = 0th minute

// 2 = 2 AM

// 1 = The 1st day of the month

// */4 = Every 4 months

// ? = We don't care what day of the week it happens to be.

    // /**
    //  * Runs automatically every 4 months.
    //  * Cron expression: "0 0 2 1 */4 ?"
    //  * Meaning: At 02:00 AM, on the 1st day of the month, every 4th month.
    //  * (e.g., January 1st, May 1st, September 1st)
    //  */
    @Scheduled(cron = "0 0 2 1 */4 ?")
    @Transactional
    public void cleanUpZombieRecords() {
        log.info("Starting Database Garbage Collection...");

        // 1. Delete orphaned Companies FIRST (because they hold foreign keys to Addresses)
        int deletedCompanies = companyRepository.deleteOrphanedCompanies();
        log.info("Garbage Collection: Deleted {} orphaned companies.", deletedCompanies);

        // 2. Delete orphaned Addresses SECOND
        int deletedAddresses = addressRepository.deleteOrphanedAddresses();
        log.info("Garbage Collection: Deleted {} orphaned addresses.", deletedAddresses);

        log.info("Database Garbage Collection completed.");
    }
}