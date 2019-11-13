package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails {
  private Map<String, Pizza> pizzas;
  private Special special;
  private Map<String, Pizza> specialPizzas;

  public OrderDetails() {
    this.pizzas = new HashMap<>();
  }

  public List<Pizza> getPizzas() {
    return new ArrayList<>(pizzas.values());
  }

  public Pizza getPizzaById(String pizzaId) {
    return pizzas.get(pizzaId);
  }

  public void addPizza(Pizza pizza) {
    pizzas.put(pizza.get_id(), pizza);
  }

  public Pizza removePizzaById(String pizzaId) {
    return pizzas.remove(pizzaId);
  }

  public Boolean isEmpty() {
    return (pizzas.size() == 0 && specialPizzas.size() == 0);
  }
}
