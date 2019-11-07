package io.swagger.repositories;

import io.swagger.model.customer.Customer;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
  List<Customer> findAll();
}
