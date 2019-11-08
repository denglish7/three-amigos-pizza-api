package io.swagger.repositories;

import io.swagger.model.pizza.Size;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SizeRepository extends MongoRepository<Size, String> {

}
