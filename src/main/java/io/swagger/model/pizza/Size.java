package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sizes")
public class Size {

  @ApiModelProperty(hidden = true)
  private String _id;

  @NotNull
  private String name;

  @NotNull
  private Double priceMultiplier;

  private Integer diameter;
  private Integer numberOfServings;
  private Integer numberOfSlices;

  public Size() {
  }

  public Size(@NotNull String name, @NotNull Double priceMultiplier) {
    this.name = name;
    this.priceMultiplier = priceMultiplier;
  }

  public Size(@NotNull String name, @NotNull Double priceMultiplier, Integer diameter,
      Integer numberOfServings, Integer numberOfSlices) {
    this.name = name;
    this.priceMultiplier = priceMultiplier;
    this.diameter = diameter;
    this.numberOfServings = numberOfServings;
    this.numberOfSlices = numberOfSlices;
  }

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
  @ApiModelProperty(example = "Large", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get base price
   *
   * @return base price
   */
  @ApiModelProperty(example = "12.0", required = true)
  public Double getPriceMultiplier() {
    return priceMultiplier;
  }

  public void setPriceMultiplier(Double priceMultiplier) {
    this.priceMultiplier = priceMultiplier;
  }

  /**
   * Get diameter in inches
   *
   * @return diameter in inches
   */
  @ApiModelProperty(example = "18")
  public Integer getDiameter() {
    return diameter;
  }

  public void setDiameter(Integer diameter) {
    this.diameter = diameter;
  }

  /**
   * Get number of servings
   *
   * @return number of servings
   */
  @ApiModelProperty(example = "4")
  public Integer getNumberOfServings() {
    return numberOfServings;
  }

  public void setNumberOfServings(Integer numberOfServings) {
    this.numberOfServings = numberOfServings;
  }

  /**
   * Get number of slices
   *
   * @return number of slices
   */
  @ApiModelProperty(example = "8")
  public Integer getNumberOfSlices() {
    return numberOfSlices;
  }

  public void setNumberOfSlices(Integer numberOfSlices) {
    this.numberOfSlices = numberOfSlices;
  }
}
