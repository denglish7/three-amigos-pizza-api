package io.swagger.model;

/**
 * Interface for all Objects that have a price.
 */
public interface Priceable {

  Double BASE_PRICE = 0.0;

  /**
   * Sets the price of a priceable object.
   */
  void setPrice();

  /**
   * Get price
   * @return price
   */
  Double getPrice();

}
