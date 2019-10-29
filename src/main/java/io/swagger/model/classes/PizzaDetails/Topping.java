package io.swagger.model.classes.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

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

  @ApiModelProperty(example = "Mushrooms", required = true)
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ApiModelProperty(example = "5", required = true)
  @NotNull
  public Integer getToppingId() {
    return toppingId;
  }

  public void setToppingId(Integer toppingId) {
    this.toppingId = toppingId;
  }

  @ApiModelProperty(example = "1", required = true)
  @NotNull
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @ApiModelProperty(example = "1.50", required = true)
  @NotNull
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @ApiModelProperty(example = "true", required = true)
  @NotNull
  public Boolean getDairyFree() {
    return isDairyFree;
  }

  public void setDairyFree(Boolean dairyFree) {
    isDairyFree = dairyFree;
  }

  @ApiModelProperty(example = "true", required = true)
  @NotNull
  public Boolean getVegetarian() {
    return isVegetarian;
  }

  public void setVegetarian(Boolean vegetarian) {
    isVegetarian = vegetarian;
  }


}
