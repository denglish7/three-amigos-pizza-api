package io.swagger.model.classes.PizzaDetails;

public class Sauce {
  String name;
  Integer quantity;
  Double price;


  public Sauce(String name, Integer quantity, Double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Double getPrice() {
    return price;
  }
}
