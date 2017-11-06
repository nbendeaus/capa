package com.capgemini.capa.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Component;

/**
 * Component containing operations to execute on application startup.
 * <p>
 * This component initialises the configured database with seed data.
 * <p>
 * <em>This component only exists for purposes of this reference application and would not
 * ordinarily be used in a Production application.</em>
 */
@Component
public class ApplicationStartupService implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(ApplicationStartupService.class);

    /**
     * Lower-level interface to the database, used e.g. to create collections and indexes.
     */
    private final MongoOperations mongoOperations;

    public ApplicationStartupService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("onApplicationEvent(event={})", contextRefreshedEvent);
        createDatabase();
        seedDatabase();
    }

    /**
     * Create the various database collections and indexes for this application.
     */
    private void createDatabase() {
        logger.debug("createDatabase()");
        try {
            mongoOperations.createCollection("file");
            mongoOperations.indexOps("file")
                .ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder()
                    .named("title")
                    .onField("title")
                    .build());
        }
        catch (Exception e) {
            logger.debug("Exception creating database, assuming database already exists");
            // This is most likely because the collection already exists, so ignore the error and
            // carry on
        }
    }

    /**
     * Populate the database with seed data, in particular some demo accounts are created so that
     * application logins and role-based authorisations will work.
     */
    private void seedDatabase() {
        logger.debug("seedDatabase()");
//        if (accountRepository.findByUsername("mark") == null) {
//            Account markAdminAccount = new Account();
//            markAdminAccount.setUsername("mark");
//            markAdminAccount.setPassword(passwordEncoder.encode("bimble"));
//            markAdminAccount.setRoles(adminRoles);
//            accountRepository.save(markAdminAccount);
//        }
    }
}
