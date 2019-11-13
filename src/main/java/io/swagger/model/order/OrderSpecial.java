package io.swagger.model.order;

import io.swagger.model.Priceable;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;
import java.util.List;

public class OrderSpecial implements Priceable {

  private Special special;
  private List<Pizza> pizzasInSpecial;
  private Double price;

  public OrderSpecial(Special special, List<Pizza> pizzasInSpecial) {
    this.special = special;
    this.pizzasInSpecial = pizzasInSpecial;
    setPrice();
  }

  public String getSpecialId() {
    return special.get_id();
  }

  public String getSpecialName() {
    return special.getName();
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
