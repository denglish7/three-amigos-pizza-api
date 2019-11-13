package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItems {

  private Map<String, Pizza> pizzas;
  private Map<String, OrderSpecial> specials;

  public OrderItems() {
    this.pizzas = new HashMap<>();
    this.specials = new HashMap<>();
  }

  public List <String> getPizzaNames() {
    List <String> pizzaNames = new ArrayList <>();
    for (String key: this.pizzas.keySet()) {
      String pizzaName = pizzas.get(key).getName();
      pizzaNames.add(pizzaName);
    }
    return pizzaNames;
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

  //Can we get the specialId to use this in StoreController.processOrder()?
  public OrderSpecial getSpecialById(String specialId) {return specials.get(specialId);}

  public List<String> getSpecialNames() {
    List <String> specialNames = new ArrayList <>();
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
    for (Pizza pizza : pizzas.values()) {
      price += pizza.getPrice();
    }
    for (OrderSpecial special : specials.values()) {
      price += special.getPrice();
    }
    return price;
  }
}
