package io.swagger.model.customer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
  private Customer customer;

  @Before
  public void setUp() throws Exception {
    customer = new Customer(
        "Daniel",
        "396-385-2853",
        "1953 1st Ave N"
    );
  }

  @Test
  public void setName() {
    customer.setName("Jonah");
    assertEquals("Jonah", customer.getName());
  }

  @Test
  public void setPhone() {
    customer.setName("395-482-4725");
    assertEquals("395-482-4725", customer.getName());
  }

  @Test
  public void setAddress() {
    customer.setName("2934 2nd Ave S");
    assertEquals("2934 2nd Ave S", customer.getName());
  }
}
