package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;
import java.util.HashMap;
import java.util.Map;

public class OrderItems {

  private Map<String, Pizza> pizzas;
  private Map<String, OrderSpecial> specials;

  public OrderItems() {
    this.pizzas = new HashMap<>();
    this.specials = new HashMap<>();
  }

  public void addPizza(Pizza pizza) {
    pizzas.put(pizza.get_id(), pizza);
  }

  public Pizza removePizzaById(String pizzaId) {
    return pizzas.remove(pizzaId);
  }

  public Pizza getPizzaById(String pizzaId) {return pizzas.get(pizzaId);}

  public void addSpecial(OrderSpecial special) {
    specials.put(special.getSpecialId(), special);
  }

  public OrderSpecial removeSpecialById(String specialId) {
    return specials.remove(specialId);
  }

  public OrderSpecial getSpecialById(String specialId) {return specials.get(specialId);}

  public Boolean isEmpty() {
    return (pizzas.size() == 0 && specials.size() == 0);
  }

  public Double getPrice(Double basePrice) {
    Double price = basePrice;
    for (Pizza pizza : pizzas.values()) {
      price += pizza.getPrice();
    }
    for (OrderSpecial special : specials.values()) {
      price += special.getPrice();
    }
    return price;
  }
}
