package io.swagger.api.PizzaDetails;

import io.swagger.model.PizzaDetails.Pizza;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, String> {

  List<Pizza> findAllByName(String name);

  List<Pizza> findAll();
}
