package io.swagger.api.customer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.customer.Customer;
import io.swagger.repositories.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class CustomerControllerTest {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CustomerController customerController;

  @Before
  public void setUp() {
    customerRepository.deleteAll();
  }

  @After
  public void tearDown() {
    customerRepository.deleteAll();
  }

  @Test
  public void getAllCustomersNone() {
    assertTrue(customerController.getAllCustomers().getBody().isEmpty());
  }

  @Test
  public void getAllCustomersOne() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    customerController.createCustomer(newCustomer);
    assertTrue(customerController.getAllCustomers().getBody().size() == 1);
  }

  @Test
  public void createCustomer() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity response = customerController.createCustomer(newCustomer);
    assertEquals(newCustomer, response.getBody());
  }

  @Test
  public void findById() {
    String NAME = "Daniel English";
    String PHONE = "395-284-2463";
    String ADDRESS = "9284 1st Ave Seattle, WA 98352";
    Customer newCustomer = new Customer(NAME, PHONE, ADDRESS);
    ResponseEntity<Customer> saveResponse = customerController.createCustomer(newCustomer);
    String id = saveResponse.getBody().get_id();
    ResponseEntity<Customer> response = customerController.findById(id);
    assertEquals(NAME, response.getBody().getName());
    assertEquals(PHONE, response.getBody().getPhone());
    assertEquals(ADDRESS, response.getBody().getAddress());
  }

  @Test
  public void findByIdCustomerNotFound() {
    String id = "3958304";
    ResponseEntity<Customer> response = customerController.findById(id);
    String message = "customerId 3958304 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

}
