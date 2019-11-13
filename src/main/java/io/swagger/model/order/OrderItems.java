package io.swagger.model.order;

import io.swagger.model.pizza.Pizza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItems {

  private Map<String, List<Pizza>> pizzas;
  private Map<String, OrderSpecial> specials;

  public OrderItems() {
    this.pizzas = new HashMap<>();
    this.specials = new HashMap<>();
  }

  public void addPizza(Pizza pizza) {
    List<Pizza> pizzasToPut = new ArrayList<>();
    if (pizzas.containsKey(pizza.get_id())) {
      pizzasToPut = pizzas.get(pizza.get_id());
    }
    pizzasToPut.add(pizza);
    pizzas.put(pizza.get_id(), pizzasToPut);
  }

  public boolean removePizza(String pizzaId, String sizeId) {
    if (!pizzas.containsKey(pizzaId)) {
      return false;
    }
    List<Pizza> pizzasWithId = pizzas.get(pizzaId);
    for (Pizza pizza : pizzasWithId) {
      if (pizza.getSize().get_id().equals(sizeId)) {
        pizzasWithId.remove(pizza);
        pizzas.put(pizzaId, pizzasWithId);
        return true;
      }
    }
    return false;
  }

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
    for (List<Pizza> pizzas : pizzas.values()) {
      for (Pizza pizza : pizzas) {
        price += pizza.getPrice();
      }
    }
    for (OrderSpecial special : specials.values()) {
      price += special.getPrice();
    }
    return price;
  }
}
