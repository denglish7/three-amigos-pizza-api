package io.swagger.api.PizzaDetails;

import io.swagger.model.PizzaDetails.Size;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SizeRepository extends MongoRepository<Size, String> {
  Size findByName(String name);

  List<Size> findAll();
}
