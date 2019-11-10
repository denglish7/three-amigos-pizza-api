package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetails {

  private HashMap<String, Pizza> pizzas;

  public OrderDetails() {
  }

  public List<Pizza> getPizzas() {
    return new ArrayList<>(pizzas.values());
  }

  public void addPizzas(List<Pizza> pizzasToAdd) {
    for (Pizza pizza : pizzasToAdd) {
      this.pizzas.put(pizza.get_id(), pizza);
    }
  }

  public void removePizzas(List<String> pizzaIdsToRemove) {
    for (String pizzaId : pizzaIdsToRemove) {
      this.pizzas.remove(pizzaId);
    }
  }
}