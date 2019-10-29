/** A class we can use for custom mongo configuration */


package io.swagger.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


@Configuration
public class MongoConfig {

  @Autowired
  private Environment env;

  private MongoClient mongoclient;
  private MongoTemplate mongoTemplate;

  @Bean
  public MongoClient mongo() {
    if (this.mongoclient == null) {
      this.mongoclient = new MongoClient(env.getProperty("spring.data.mongodb.uri"));
    }
    return this.mongoclient;
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    if (this.mongoTemplate == null) {
      this.mongoTemplate = new MongoTemplate(mongo(), this.getDatabaseName());
    }
    return this.mongoTemplate;
  }

  @Bean
  protected String getDatabaseName(){
    return env.getProperty("spring.data.mongodb.database");
  }
}
//@Configuration
//public class MongoConfig extends AbstractMongoConfiguration {
//
//  private MongoClient mongoClient;
//
////  @Bean
////  public MongoClient mongoClient() {
////    MongoClientURI uri = new MongoClientURI("mongodb+srv://amigosUser:amigosPassword@cluster0-zj3zt.azure.mongodb.net/test?retryWrites=true&w=majority");
////    if (this.mongoClient == null) {
////      this.mongoClient = new MongoClient(uri);
////    }
////    return mongoClient;
////  }
//
//
//  @Override
//  public MongoClient mongoClient() {
//    MongoClientURI uri = new MongoClientURI("mongodb+srv://amigosUser:amigosPassword@cluster0-zj3zt.azure.mongodb.net/test?retryWrites=true&w=majority"
//        + " Copy");
//    if (this.mongoClient == null) {
//      this.mongoClient = new MongoClient(uri);
//    }
//    return this.mongoClient;
//  }
//
//  @Override
//  protected String getDatabaseName() {
//    return "test";
//  }
//}
