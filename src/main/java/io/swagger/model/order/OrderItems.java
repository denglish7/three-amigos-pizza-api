package io.swagger.model.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItems {

  private Map<String, OrderPizza> pizzas;
  private Map<String, OrderSpecial> specials;

  OrderItems() {
    this.pizzas = new HashMap<>();
    this.specials = new HashMap<>();
  }

  public Map<String, OrderPizza> getPizzas() {
    return pizzas;
  }

  public Map<String, OrderSpecial> getSpecials() {
    return specials;
  }

  public List<String> getPizzaNames() {
    List<String> pizzaNames = new ArrayList<>();
    for (OrderPizza pizza : pizzas.values()) {
      pizzaNames.add(pizza.getName());
    }
    return pizzaNames;
  }


  public void addPizza(OrderPizza pizza) {
    pizzas.put(pizza.get_id(), pizza);
  }

  public boolean removePizza(String pizzaId) {
    if (containsPizza(pizzaId)) {
      pizzas.remove(pizzaId);
      return true;
    }
    return false;
  }

  public void addSpecial(OrderSpecial special) {
    specials.put(special.getSpecialId(), special);
  }

  public boolean removeSpecialById(String specialId) {
    if (containsSpecial(specialId)) {
      specials.remove(specialId);
      return true;
    }
    return false;
  }

  public boolean containsPizza(String pizzaId) { return pizzas.containsKey(pizzaId); }

  public boolean containsSpecial(String specialId) { return specials.containsKey(specialId); }


  public List<String> getSpecialNames() {
    List<String> specialNames = new ArrayList<>();
    for (String key : this.specials.keySet()) {
      String specialName = specials.get(key).getSpecialName();
      specialNames.add(specialName);
    }
    return specialNames;
  }

  public Boolean isEmpty() {
    return (pizzas.size() == 0 && specials.size() == 0);
  }

  public Double getPrice(Double basePrice) {
    Double price = basePrice;
    for (OrderPizza pizza : pizzas.values()) {
      if (pizza != null) {
        price += pizza.getPrice();
      }
    }
    for (OrderSpecial special : specials.values()) {
      if (special != null) {
        price += special.getPrice();
      }
    }
    return price;
  }

  @Override
  public String toString() {
    return "Pizzas: " + pizzas.values().toString() + '\'' +
        "Specials: " + specials.values().toString();
  }
}
