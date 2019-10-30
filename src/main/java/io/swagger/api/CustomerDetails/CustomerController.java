package io.swagger.api.CustomerDetails;

import io.swagger.annotations.ApiOperation;
import io.swagger.model.CustomerDetails.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CustomerController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Customer.class, responseContainer = "List", tags = {"developers",})
  public List<Customer> getAllCustomers() {
    return mongoTemplate.findAll(Customer.class);
  }




}
