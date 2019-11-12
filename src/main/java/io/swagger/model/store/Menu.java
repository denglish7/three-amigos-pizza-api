package io.swagger.model.store;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Document(collection = "menus")
public class Menu {
  @ApiModelProperty(hidden = true)
  private String _id;

  private HashMap<String, Pizza> pizzas;
  private HashMap<String, Special> specials;

  public Menu() {
    this.pizzas = new HashMap<>();
    this.specials = new HashMap<>();
  }

  public String get_id() {
    return _id;
  }

  public List<Pizza> getPizzas() {
    return new ArrayList<>(pizzas.values());
  }

  public Pizza getPizza(String pizzaId) {
    return this.pizzas.get(pizzaId);
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

  public List<Special> getSpecials() {
    return new ArrayList<>(specials.values());
  }

  public void addSpecials(List<Special> specialsToAdd) {
    for (Special special : specialsToAdd) {
      this.specials.put(special.get_id(), special);
    }
  }

  public void removeSpecials(List<String> specialIdsToRemove) {
    for (String specialId : specialIdsToRemove) {
      this.specials.remove(specialId);
    }
  }
}