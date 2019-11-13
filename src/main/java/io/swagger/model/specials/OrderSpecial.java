package io.swagger.model.specials;

import io.swagger.model.Priceable;
import io.swagger.model.pizza.Pizza;
import java.util.List;

public class OrderSpecial implements Priceable {

  private Special special;
  private List<Pizza> pizzasInSpecial;
  private Double price;

  public OrderSpecial(Special special, List<Pizza> pizzasInSpecial) {
    this.special = special;
    this.pizzasInSpecial = pizzasInSpecial;
  }

  public Special getSpecial() {
    return special;
  }

  public void setSpecial(Special special) {
    this.special = special;
  }

  @Override
  public void setPrice() {
    Double price = BASE_PRICE;
    for (Pizza pizza : pizzasInSpecial) {
      price += pizza.getPrice();
    }
    this.price = price * special.getSpecialPriceRatio();
  }

  @Override
  public Double getPrice() {
    return price;
  }
}
