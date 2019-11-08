package io.swagger.model.specials;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.pizza.Pizza;
import java.util.List;
import org.bson.types.ObjectId;

public class Special {

  @ApiModelProperty(hidden = true)
  private String _id;
  private List<Pizza> pizzas;
  private Double price;

  public Special(List<Pizza> pizzas, Double price) {
    this._id = new ObjectId().toString();
    this.pizzas = pizzas;
    this.price = price;
  }

  public String get_id() {
    return _id;
  }

  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
