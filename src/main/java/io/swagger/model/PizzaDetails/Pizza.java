package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pizzas")
public class Pizza {
  @Id
  private ObjectId _id;
  private String name;
  private Size size;
  private Sauce sauce;
  private List<Topping> toppings;
  private Boolean isGlutenFree;

  public Pizza(String name, Size size, Sauce sauce,
      List<Topping> toppings, Boolean isGlutenFree) {
    this._id = new ObjectId();
    this.name = name;
    this.size = size;
    this.sauce = sauce;
    this.toppings = toppings;
    this.isGlutenFree = isGlutenFree;
  }

  /**
   * Get _id
   * @return _id string
   */
  @ApiModelProperty(example = "5349b4ddd2781d08c09890f3")
  public String get_id() {
    return _id.toString();
  }

  public void set_id(String newID) {
    this._id = new ObjectId(newID);
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
  @ApiModelProperty(example = "Large")
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
  @ApiModelProperty(example = "[topping1, topping2]")
  public List<Topping> getToppings() {
    return toppings;
  }

  public void setToppings(List<Topping> toppings) {
    this.toppings = toppings;
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
