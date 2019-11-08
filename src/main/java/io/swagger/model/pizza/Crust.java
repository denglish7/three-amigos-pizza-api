package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "crusts")
public class Crust {

  @ApiModelProperty(hidden = true)
  private String _id;

  @NotNull
  private Double price;

  @NotNull
  private Boolean isGlutenFree;

  private String name;

  public Crust(@NotNull Double price,
      @NotNull Boolean isGlutenFree, String name) {
    this.price = price;
    this.isGlutenFree = isGlutenFree;
    this.name = name;
  }

  public String get_id() {
    return _id;
  }

  /**
   * Get price
   *
   * @return price
   */
  @ApiModelProperty(example = "3.0", required = true)
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Get glutenFree
   *
   * @return glutenFree
   */
  @ApiModelProperty(example = "True", required = true)
  public Boolean getGlutenFree() {
    return isGlutenFree;
  }

  public void setGlutenFree(Boolean glutenFree) {
    isGlutenFree = glutenFree;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "wheat")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
