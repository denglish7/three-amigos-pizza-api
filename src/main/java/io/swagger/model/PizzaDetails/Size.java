package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

public class Size {
  @Id
  private String name;
  private Integer diameter;
  private Integer numberOfServings;
  private Integer numberOfSlices;
  private Double basePrice;

  public Size(String name, Integer diameter, Integer numberOfServings,
      Integer numberOfSlices, Double basePrice) {
    this.name = name;
    this.diameter = diameter;
    this.numberOfServings = numberOfServings;
    this.numberOfSlices = numberOfSlices;
    this.basePrice = basePrice;
  }

  /**
   * Get name
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
   * Get diameter in inches
   * @return diameter in inches
   */
  @ApiModelProperty(example = "18", required = true)
  public Integer getDiameter() {
    return diameter;
  }

  public void setDiameter(Integer diameter) {
    this.diameter = diameter;
  }

  /**
   * Get number of servings
   * @return number of servings
   */
  @ApiModelProperty(example = "4", required = true)
  public Integer getNumberOfServings() {
    return numberOfServings;
  }

  public void setNumberOfServings(Integer numberOfServings) {
    this.numberOfServings = numberOfServings;
  }

  /**
   * Get number of slices
   * @return number of slices
   */
  @ApiModelProperty(example = "8")
  public Integer getNumberOfSlices() {
    return numberOfSlices;
  }

  public void setNumberOfSlices(Integer numberOfSlices) {
    this.numberOfSlices = numberOfSlices;
  }

  /**
   * Get base price
   * @return base price
   */
  @ApiModelProperty(example = "12.0", required = true)
  public Double getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(Double basePrice) {
    this.basePrice = basePrice;
  }
}
