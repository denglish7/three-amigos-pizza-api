package io.swagger.model.pizza;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ToppingTest {

  private Topping topping;

  @Before
  public void setUp() throws Exception {
    topping = new Topping(
      "mushrooms",
        .10
    );
  }

  @Test
  public void setName() {
    topping.setName("peppers");
    assertEquals("peppers", topping.getName());
  }

  @Test
  public void setPricePerUnit() {
    topping.setPricePerUnit(.05);
    assertEquals((Double) .05, topping.getPricePerUnit());
  }
}
