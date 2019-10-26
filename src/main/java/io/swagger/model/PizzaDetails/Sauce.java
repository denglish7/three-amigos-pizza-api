package io.swagger.model.PizzaDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;


@Document(collection = "Sauce")
public class Sauce {

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
