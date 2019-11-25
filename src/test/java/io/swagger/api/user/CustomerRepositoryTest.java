package io.swagger.api.user;

import io.swagger.model.customer.Customer;
import io.swagger.repositories.CustomerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class CustomerRepositoryTest {
  @Autowired
  private CustomerRepository customerRepository;

  @Before
  public void setUp() {
    customerRepository.deleteAll();
  }

  @After
  public void tearDown() {
    customerRepository.deleteAll();
  }

  @Test
  public void addCustomerToRepositoryTest() throws Exception {
    assertEquals(0, customerRepository.count());
    Customer customer = new Customer(
        "Daniel English",
        "345-382-5321",
        "1423 1st Ave Seattle, WA 98352"
    );
    customerRepository.insert(customer);
    assertEquals(1, customerRepository.count());
  }
}
