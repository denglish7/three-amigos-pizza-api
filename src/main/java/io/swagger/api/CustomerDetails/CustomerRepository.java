package io.swagger.api.CustomerDetails;


import io.swagger.model.CustomerDetails.Customer;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
  Customer findByName(String name);

  List<Customer> findAll();
}
