package io.swagger.api.PizzaDetails;

import io.swagger.model.PizzaDetails.Sauce;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SauceRepository extends MongoRepository<Sauce, String> {

  Sauce findByName(String name);

  List<Sauce> findAll();

}