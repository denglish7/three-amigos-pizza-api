package io.swagger.repositories;

import io.swagger.model.pizza.Pizza;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PizzaRepository extends MongoRepository<Pizza, String> {

}
