package io.swagger.model.pizza;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrustTest {
  private Crust crust;

  @Before
  public void setUp() throws Exception {
    crust = new Crust(
        2.00,
        false,
        "regular"
    );
  }

  @Test
  public void setName() {
    crust.setName("wheat");
    assertEquals("wheat", crust.getName());
  }

  @Test
  public void setPrice() {
    crust.setPrice(2.50);
    assertEquals((Double) 2.5, crust.getPrice());
  }

  @Test
  public void setGlutenFree() {
    crust.setGlutenFree(true);
    assertEquals(true, crust.getGlutenFree());
  }

}
