package io.swagger.model.menu;

import io.swagger.model.pizza.Pizza;
import java.util.List;

public class Menu {

  private List<Pizza> pizzas;

  public Menu() {
  }

  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  public void addPizzas(List<Pizza> pizzas) {
    this.pizzas.addAll(pizzas);
  }
}
