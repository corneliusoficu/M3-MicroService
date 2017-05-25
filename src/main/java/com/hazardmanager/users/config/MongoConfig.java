package com.hazardmanager.users.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ComponentScan(basePackages = "com.hazardmanager.users")
public class MongoConfig {

    @Autowired
    private Environment environment;

    @Bean
    public MongoDbFactory mongoDbFactory(){
        MongoClient mongoClient = new MongoClient(
                environment.getProperty("spring.data.mongodb.host"),
                Integer.valueOf(environment.getProperty("spring.data.mongodb.port"))
        );
        return new SimpleMongoDbFactory(mongoClient,environment.getProperty("spring.data.mongodb.database"));

    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }

}

