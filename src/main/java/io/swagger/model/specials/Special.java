package io.swagger.model.specials;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.pizza.Size;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
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
    this._id = new ObjectId().toString();
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

  /**
   * Get requiredNumberPizzas
   *
   * @return requiredNumberPizzas
   */
  @ApiModelProperty(allowableValues = ">= 1", example = "2", required = true)
  public Integer getRequiredNumberPizzas() {
    return requiredNumberPizzas;
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
}