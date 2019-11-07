package io.swagger.model.order;

import io.swagger.model.specials.Special;
import io.swagger.model.pizza.Pizza;

import java.util.List;


public class Cart {
  List <Pizza> pizzas;
  List <Special> specials;

  public Cart(List <Pizza> pizzas, List <Special> specials) {
    this.pizzas = pizzas;
    this.specials = specials;
  }

  public List <Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List <Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  public List <Special> getSpecials() {
    return specials;
  }

  public void setSpecials(List <Special> specials) {
    this.specials = specials;
  }

  /**
   * Calculates the price of the pizzas and specials in the cart.
   */
  public Double calculatePrice() {
    return 1.0;
  }
}
