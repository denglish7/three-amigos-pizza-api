package io.swagger.model.classes.PizzaDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class Sauce {
  @JsonProperty("name")
  private String name;

  @JsonProperty("price")
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
  @NotNull

  @Valid
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
  @NotNull

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
