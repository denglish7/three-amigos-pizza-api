package io.swagger.model.PizzaDetails;

public class Size {
  String name;
  Integer diameter;
  Integer numberOfServings;
  Double price;

  public Size(String name, Integer diameter, Integer numberOfServings, Double price) {
    this.name = name;
    this.diameter = diameter;
    this.numberOfServings = numberOfServings;
    this.price = price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDiameter(Integer diameter) {
    this.diameter = diameter;
  }

  public void setNumberOfServings(Integer numberOfServings) {
    this.numberOfServings = numberOfServings;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Integer getDiameter() {
    return diameter;
  }

  public Integer getNumberOfServings() {
    return numberOfServings;
  }

  public Double getPrice() {
    return price;
  }
}
