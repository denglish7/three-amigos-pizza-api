/** A class we can use for custom mongo configuration */


package io.swagger.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Autowired
  private Environment env;

  private MongoClient mongoclient;
  private MongoTemplate mongoTemplate;

  @Bean
  public MongoClient mongoClient() {

    if (this.mongoclient == null) {
      MongoClientURI uri = new MongoClientURI(this.getURIName());
      this.mongoclient = new MongoClient(uri);
    }

    return this.mongoclient;
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    if (this.mongoTemplate == null) {
      this.mongoTemplate = new MongoTemplate(this.mongoClient(), this.getDatabaseName());
    }
    return this.mongoTemplate;
  }

  @Bean
  protected String getDatabaseName() {
    return env.getProperty("spring.data.mongodb.database");
  }

  private String getURIName() {
    return env.getProperty("spring.data.mongodb.uri");
  }
}

