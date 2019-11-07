package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pizzas")
public class Pizza {

  @ApiModelProperty(hidden=true)
  private String _id;
  private String name;
  @DBRef
  private Size size;
  @DBRef
  private Sauce sauce;
  @DBRef
  private List<Topping> toppings;
  private Boolean isGlutenFree;
  @ApiModelProperty(hidden=true)
  private Double totalPrice;

public Pizza(String _id, String name, Size size, Sauce sauce, List<Topping> toppings, Boolean isGlutenFree) {
    this._id = _id;
    this.name = name;
    this.size = size;
    this.sauce = sauce;
    this.toppings = toppings;
    this.isGlutenFree = isGlutenFree;
    this.totalPrice = this.calculatePrice();
  }

  /**
   * Get _id
   * @return _id
   */
  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  /**
   * Get totalPrice
   * @return totalPrice
   */
  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  /**
   * Get name
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
   * @return Size object
   */
  @ApiModelProperty
  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
    this.setTotalPrice(this.calculatePrice());
  }

  /**
   * Get sauce
   * @return Sauce object
   */
  @ApiModelProperty
  public Sauce getSauce() {
    return sauce;
  }

  public void setSauce(Sauce sauce) {
    this.sauce = sauce;
    this.setTotalPrice(this.calculatePrice());
  }

  /**
   * Get toppings
   * @return list of toppings
   */
  @ApiModelProperty
  public List<Topping> getToppings() {
    return toppings;
  }

  public void setToppings(List<Topping> toppings) {
    this.toppings = toppings;
    this.setTotalPrice(this.calculatePrice());
  }

  public void addTopping(Topping topping) {
    this.toppings.add(topping);
    this.setTotalPrice(this.calculatePrice());
  }

  public void removeTopping(Topping topping) {
    this.toppings.remove(topping);
    this.setTotalPrice(this.calculatePrice());
  }

  /**
   * Get isGlutenFree
   * @return isGlutenFree
   */
  @ApiModelProperty
  public Boolean getGlutenFree() {
    return isGlutenFree;
  }

  public void setGlutenFree(Boolean glutenFree) {
    isGlutenFree = glutenFree;
  }

  /**
   * Helper method for calculating price of pizza.
   * @return Double price, or null if size not set.
   */
  private Double calculatePrice() {
    if (this.size == null) {
      return null;
    }
    Double price = size.getBasePrice();
    if (this.sauce != null) {
      price += this.sauce.getPrice();
    }
    if (this.toppings != null) {
      for (Topping t : this.toppings) {
        price += t.getPricePerUnit();
      }
    }
    return price;
  }
}
