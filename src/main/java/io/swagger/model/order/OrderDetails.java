package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails {
  private Map<String, Pizza> pizzas;

  public OrderDetails() {
    this.pizzas = new HashMap<>();
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
