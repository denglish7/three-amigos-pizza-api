package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pizzas")
public class Pizza {

  @ApiModelProperty(hidden = true)
  private String _id;
  private String name;
  @DBRef
  private Size size;
  @DBRef
  private Crust crust;
  @DBRef
  private List<Topping> toppings;

  public Pizza(String name, Size size, Crust crust,
      List<Topping> toppings) {
    this.name = name;
    this.size = size;
    this.crust = crust;
    this.toppings = toppings;
  }

  public String get_id() {
    return _id;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "Cheese")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get size
   *
   * @return size
   */
  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  /**
   * Get crust
   *
   * @return crust
   */
  public Crust getCrust() {
    return crust;
  }

  public void setCrust(Crust crust) {
    this.crust = crust;
  }

  /**
   * Get topping
   *
   * @return topping
   */
  public List<Topping> getToppings() {
    return toppings;
  }

  public void setToppings(List<Topping> toppings) {
    this.toppings = toppings;
  }
}