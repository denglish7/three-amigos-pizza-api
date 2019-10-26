package io.swagger.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

//  @Override
//  public Mongo mongo() throws Exception {
//      MongoClientURI uri = new MongoClientURI("mongodb+srv://amigosUser:<password>@cluster0-zj3zt.azure.mongodb.net/test?retryWrites=true&w=majority");
//    return new MongoClient(uri);
//  }

  @Override
  public MongoClient mongoClient() {
    MongoClientURI uri = new MongoClientURI("mongodb+srv://amigosUser:<password>@cluster0-zj3zt.azure.mongodb.net/test?retryWrites=true&w=majority");
    return new MongoClient(uri);
  }

  @Override
  protected String getDatabaseName() {
    return "threeamigosdb";
  }

//  MongoClientURI uri = new MongoClientURI(
//      "mongodb+srv://amigosUser:<password>@cluster0-zj3zt.azure.mongodb.net/test?retryWrites=true&w=majority");
//
//  MongoClient mongoClient = new MongoClient(uri);
//  MongoDatabase database = mongoClient.getDatabase("test");

}
