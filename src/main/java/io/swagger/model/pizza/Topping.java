package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "toppings")
public class Topping {

  @ApiModelProperty(hidden = true)
  private String _id;

  @NotNull
  private String name;

  @NotNull
  private Double pricePerUnit;

  public Topping(String name, Double pricePerUnit) {
    this._id = new ObjectId().toString();
    this.name = name;
    this.pricePerUnit = pricePerUnit;
  }

  public String get_id() {
    return _id;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "mushrooms", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get price
   *
   * @return price
   */
  @ApiModelProperty(example = "1.0", required = true)
  public Double getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(Double pricePerUnit) {
    this.pricePerUnit = pricePerUnit;
  }
}
