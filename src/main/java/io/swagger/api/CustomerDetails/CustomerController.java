package io.swagger.api.CustomerDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.CustomerDetails.Customer;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Customers.")
@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Customers in the system.", response = Customer.class, responseContainer = "List", tags = {"developers",})
  public List getAllCustomers() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a customer", tags={ "admins", })
  public Customer createCustomer(@ApiParam("Customer information for a new customer") @Valid @RequestBody Customer customer) {
    return repository.save(customer);
  }
}
