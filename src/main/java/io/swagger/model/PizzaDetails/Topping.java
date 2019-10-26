package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "toppings")
public class Topping implements Serializable {

  @Id
  private String name;

  private Double pricePerUnit;

  public Topping(String name, Double pricePerUnit) {
    this.name = name;
    this.pricePerUnit = pricePerUnit;
  }

  /**
   * Get name
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
