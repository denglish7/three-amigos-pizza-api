package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pizzas")
public class Pizza {

  @ApiModelProperty(hidden = true)
  private String _id;
  private String name;
  @DBRef @NotNull
  private Crust crust;
  @DBRef @NotNull
  private List<Topping> toppings;

  public Pizza() {
  }

  public Pizza(String name, @NotNull Crust crust,
               @NotNull List<Topping> toppings) {
    this._id = new ObjectId().toString();
    this.name = name;
    this.crust = crust;
    this.toppings = toppings;
  }

  public String get_id() {
    return _id;
  }

  protected void set_id(String _id) {
    this._id = _id;
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

  public Double getBasePrice() {
    Double basePrice = crust.getPrice();
    for (Topping topping : toppings) {
      basePrice += topping.getPricePerUnit();
    }
    return basePrice;
  }
}