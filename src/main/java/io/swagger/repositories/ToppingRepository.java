package io.swagger.repositories;

import io.swagger.model.pizza.Topping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToppingRepository extends MongoRepository<Topping, String> {

}
