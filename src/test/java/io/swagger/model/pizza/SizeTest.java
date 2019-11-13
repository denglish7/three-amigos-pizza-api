package io.swagger.model.pizza;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SizeTest {

  private Size size;

  @Before
  public void setUp() throws Exception {
    size = new Size(
        "Large",
        5.00,
        14,
        4,
        8
    );
  }

  @Test
  public void setName() {
    size.setName("small");
    assertEquals("small", size.getName());
  }

  @Test
  public void setBasePrice() {
    size.setPriceMultiplier(7.50);
    assertEquals((Double) 7.50, size.getPriceMultiplier());
  }

  @Test
  public void setDiameter() {
    size.setDiameter(16);
    assertEquals((Integer) 16, size.getDiameter());
  }

  @Test
  public void setNumberOfServings() {
    size.setNumberOfServings(10);
    assertEquals((Integer) 10, size.getNumberOfServings());
  }

  @Test
  public void setNumberOfSlices() {
    size.setNumberOfSlices(12);
    assertEquals((Integer) 12, size.getNumberOfSlices());
  }
}
