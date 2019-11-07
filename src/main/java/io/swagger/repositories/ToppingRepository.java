package io.swagger.repositories;

import io.swagger.model.pizza.Topping;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToppingRepository extends MongoRepository<Topping, String> {
  Topping findByName(String name);

  List<Topping> findAll();
}
