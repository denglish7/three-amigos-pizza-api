package io.swagger.model.classes.PizzaDetails;

public class Topping {
  String name;
  Integer toppingId;
  Integer quantity;
  Double price;
  Boolean isDairyFree;
  Boolean isVegetarian;

  public Topping(String name, Integer toppingId, Integer quantity, Double price, Boolean isDairyFree, Boolean isVegetarian) {
    this.name = name;
    this.toppingId = toppingId;
    this.quantity = quantity;
    this.price = price;
    this.isDairyFree = isDairyFree;
    this.isVegetarian = isVegetarian;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getToppingId() {
    return toppingId;
  }

  public void setToppingId(Integer toppingId) {
    this.toppingId = toppingId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
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
