package io.swagger.api.CustomerDetails;

import io.swagger.model.CustomerDetails.Customer;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
  List<Customer> findAll();
}
