package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Document(collection = "pizzas")
public class Pizza {
  @Id
  private String name;

  @DBRef
  private Size size;
  @DBRef
  private Sauce sauce;
  @DBRef
  private List<Topping> toppings;
  private Boolean isGlutenFree;

//  public Pizza(String name, @RequestBody Size size, @RequestBody Sauce sauce,
//      @RequestBody List<Topping> toppings, Boolean isGlutenFree) {
//    this.name = name;
//
//    this.size = size;
//    this.sauce = sauce;
//    this.toppings = toppings;
//    this.isGlutenFree = isGlutenFree;
//    this.totalPrice = this.calculatePrice();
//  }

  private Double calculatePrice() {
    Double price = size.getBasePrice();
    price += this.sauce.getPrice();
    for (Topping t : this.toppings) {
      price += t.getPricePerUnit();
    }
    return price;
  }

  public Double getTotalPrice() {
    return this.calculatePrice();
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
  @ApiModelProperty(example = "")
  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  /**
   * Get sauce
   * @return Sauce object
   */
  @ApiModelProperty(example = "")
  public Sauce getSauce() {
    return sauce;
  }

  public void setSauce(Sauce sauce) {
    this.sauce = sauce;
  }

  /**
   * Get toppings
   * @return list of toppings
   */
  @ApiModelProperty(example = "")
  public List<Topping> getToppings() {
    return toppings;
  }

  public void setToppings(List<Topping> toppings) {
    this.toppings = toppings;
  }

  public void addTopping(Topping topping) {
    this.toppings.add(topping);
  }

  public void removeTopping(Topping topping) {
    this.toppings.remove(topping);
  }

  /**
   * Get isGlutenFree
   * @return isGlutenFree
   */
  @ApiModelProperty(example = "true")
  public Boolean getGlutenFree() {
    return isGlutenFree;
  }

  public void setGlutenFree(Boolean glutenFree) {
    isGlutenFree = glutenFree;
  }
}
