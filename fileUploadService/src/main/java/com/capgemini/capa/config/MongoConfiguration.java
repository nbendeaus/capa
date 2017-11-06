package com.capgemini.capa.config;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

/**
 *
 */
public class MongoConfiguration extends AbstractMongoConfiguration {

    private final Mongo mongo;

    private final MongoProperties properties;

    public MongoConfiguration(MongoProperties properties, Mongo mongo) {
        this.properties = properties;
        this.mongo = mongo;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return properties.getDatabase();
    }

    @Override
    public Mongo mongo() throws Exception {
        return mongo;
    }

}
