package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "sauces")
public class Sauce implements Serializable {

  @Id
  private String name;

  private Double price;

  public Sauce(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  /**
   * Get name
   * @return name
   **/
  @ApiModelProperty(example = "Red", required = true)
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get Price
   * @return price
   **/
  @ApiModelProperty(example = "1.3", required = true)
  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
}
