package io.swagger.repositories;

import io.swagger.model.pizza.Crust;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrustRepository extends MongoRepository<Crust, String> {

}