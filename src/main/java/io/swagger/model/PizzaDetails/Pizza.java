package io.swagger.model.PizzaDetails;

public class Pizza {
  String name;
  Integer pizzaID;
  Size size;
  Sauce sauce;
  Topping topping;
  Boolean isGlutenFree;
  Boolean isDairyFree;
  Boolean isVegetarian;

  public Pizza(String name, Integer pizzaID, Size size, Sauce sauce, Topping topping, Boolean isGlutenFree, Boolean isDairyFree, Boolean isVegetarian) {
    this.name = name;
    this.pizzaID = pizzaID;
    this.size = size;
    this.sauce = sauce;
    this.topping = topping;
    this.isGlutenFree = isGlutenFree;
    this.isDairyFree = isDairyFree;
    this.isVegetarian = isVegetarian;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPizzaID() {
    return pizzaID;
  }

  public void setPizzaID(Integer pizzaID) {
    this.pizzaID = pizzaID;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Sauce getSauce() {
    return sauce;
  }

  public void setSauce(Sauce sauce) {
    this.sauce = sauce;
  }

  public Topping getTopping() {
    return topping;
  }

  public void setTopping(Topping topping) {
    this.topping = topping;
  }

  public Boolean getGlutenFree() {
    return isGlutenFree;
  }

  public void setGlutenFree(Boolean glutenFree) {
    isGlutenFree = glutenFree;
  }

  public Boolean getDairyFree() {
    return isDairyFree;
  }

  public void setDairyFree(Boolean dairyFree) {
    isDairyFree = dairyFree;
  }

  public Boolean getVegetarian() {
    return isVegetarian;
  }

  public void setVegetarian(Boolean vegetarian) {
    isVegetarian = vegetarian;
  }
}
