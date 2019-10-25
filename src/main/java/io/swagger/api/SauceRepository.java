package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.PizzaDetails.Sauce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Api(value = "sauce")
public interface SauceRepository extends MongoRepository<Sauce, String> {

  Sauce findByName(String name);

}