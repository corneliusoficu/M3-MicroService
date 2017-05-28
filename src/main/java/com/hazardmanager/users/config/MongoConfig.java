package com.hazardmanager.users.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
@ComponentScan(basePackages = "com.hazardmanager.users")
public class MongoConfig {

    @Bean
    public MongoDbFactory mongoDbFactory(){
        try {
            String property = System.getProperty("spring.data.mongodb.uri");
            return new SimpleMongoDbFactory(new MongoClientURI(System.getProperty("spring.data.mongodb.uri")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }

}

