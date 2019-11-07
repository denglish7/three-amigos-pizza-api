package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "sauces")
public class Sauce {

  @Id
  private String name;
  @NotNull
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
