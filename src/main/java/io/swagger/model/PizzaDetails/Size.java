package io.swagger.model.PizzaDetails;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class Size {
  @Id
  private String name;
  @NotNull
  private Double basePrice;
  private Integer diameter;
  private Integer numberOfServings;
  private Integer numberOfSlices;

  public Size(String name, Double basePrice, Integer diameter, Integer numberOfServings,
      Integer numberOfSlices) {
    this.name = name;
    this.basePrice = basePrice;
    this.diameter = diameter;
    this.numberOfServings = numberOfServings;
    this.numberOfSlices = numberOfSlices;
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

  /**
   * Get diameter in inches
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
