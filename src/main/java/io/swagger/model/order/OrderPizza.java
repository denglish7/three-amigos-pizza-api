package io.swagger.model.order;

import io.swagger.model.Priceable;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;

public class OrderPizza extends Pizza implements Priceable {

  private Size size;
  private Double price = BASE_PRICE;

  public OrderPizza() {
  }

  public OrderPizza(Pizza pizza, Size size) {
    super(pizza.getName(), pizza.getCrust(), pizza.getToppings());
    this.size = size;
    setPrice();
  }

  public Size getSize() {
    return size;
  }

  /**
   * Get price
   *
   * @return price
   */
  @Override
  public Double getPrice() {
    return price;
  }

  @Override
  public void setPrice() {
    Double price = super.getBasePrice();
    price *= size.getPriceMultiplier();
    this.price = price;
  }

  @Override
  public String toString() {
    return "Pizza name: " + super.getName() + '\'' +
        "Size: " + size.getName() + '\'' +
        "Price: " + price.toString();
  }
}
