package io.swagger.model.menu;

import io.swagger.model.specials.Special;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.store.Store;

import java.util.Set;

public class Menu {
  Set <Store> storesUsingMenu;
  Set <Pizza> pizzas;
  Set <Special> specials;

  public Menu(Set <Store> storesUsingMenu, Set <Pizza> pizzas, Set <Special> specials) {
    this.storesUsingMenu = storesUsingMenu;
    this.pizzas = pizzas;
    this.specials = specials;
  }

  public Set <Store> getStoresUsingMenu() {
    return storesUsingMenu;
  }

  public void setStoresUsingMenu(Set <Store> storesUsingMenu) {
    this.storesUsingMenu = storesUsingMenu;
  }

  public Set <Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(Set <Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  public Set <Special> getSpecials() {
    return specials;
  }

  public void setSpecials(Set <Special> specials) {
    this.specials = specials;
  }
}
