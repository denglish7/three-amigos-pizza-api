package io.swagger.model.specials;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.pizza.Size;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "specials")
public class Special {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private Double specialPriceRatio;
  @NotNull
  private Integer requiredNumberPizzas;
  @NotNull
  @DBRef
  private Size requiredSizeOfPizzas;

  public Special() {
  }

  public Special(@NotNull String name,
      @NotNull Double specialPriceRatio,
      @NotNull Integer requiredNumberPizzas,
      @NotNull Size requiredSizeOfPizzas) {
    this.name = name;
    this.specialPriceRatio = specialPriceRatio;
    this.requiredNumberPizzas = requiredNumberPizzas;
    this.requiredSizeOfPizzas = requiredSizeOfPizzas;
  }

  /**
   * Get id
   *
   * @return id
   */
  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "Two for one", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get specialPriceRatio
   *
   * @return specialPriceRatio
   */
  @ApiModelProperty(allowableValues = "0 to 1", example = "0.8", required = true)
  public Double getSpecialPriceRatio() {
    return specialPriceRatio;
  }

  public void setSpecialPriceRatio(Double specialPriceRatio) {
    this.specialPriceRatio = specialPriceRatio;
  }

  /**
   * Get requiredNumberPizzas
   *
   * @return requiredNumberPizzas
   */
  @ApiModelProperty(allowableValues = ">= 1", example = "2", required = true)
  public Integer getRequiredNumberPizzas() {
    return requiredNumberPizzas;
  }

  public void setRequiredNumberPizzas(Integer requiredNumberPizzas) {
    this.requiredNumberPizzas = requiredNumberPizzas;
  }

  /**
   * Get requiredSizeOfPizzas
   *
   * @return requiredSizeOfPizzas
   */
  @ApiModelProperty(required = true)
  public Size getRequiredSizeOfPizzas() {
    return requiredSizeOfPizzas;
  }

  public void setRequiredSizeOfPizzas(Size requiredSizeOfPizzas) {
    this.requiredSizeOfPizzas = requiredSizeOfPizzas;
  }
}