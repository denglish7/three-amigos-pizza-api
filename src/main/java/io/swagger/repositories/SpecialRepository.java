package io.swagger.repositories;

import io.swagger.model.specials.Special;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecialRepository extends MongoRepository<Special, String> {

}
