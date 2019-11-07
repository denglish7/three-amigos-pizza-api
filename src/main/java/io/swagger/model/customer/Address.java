package io.swagger.model.customer;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {

  @NotNull
  private String streetNumber;
  @NotNull
  private String streetName;
  @NotNull
  private String cityName;
  @NotNull
  private String stateAcronym;
  @NotNull
  private String zipCode;
  @NotNull
  private String countryName;

  public Address(@NotNull String streetNumber,
      @NotNull String streetName, @NotNull String cityName,
      @NotNull String stateAcronym, @NotNull String zipCode,
      @NotNull String countryName) {
    this.streetNumber = streetNumber;
    this.streetName = streetName;
    this.cityName = cityName;
    this.stateAcronym = stateAcronym;
    this.zipCode = zipCode;
    this.countryName = countryName;
  }

  /**
   * Get streetNumber
   *
   * @return streetNumber
   */
  @ApiModelProperty(example = "123")
  public String getStreetNumber() {
    return this.streetNumber;
  }

  /**
   * Get streetName
   *
   * @return streetName
   */
  @ApiModelProperty(example = "Broadway")
  public String getStreetName() {
    return this.streetName;
  }

  /**
   * Get cityName
   *
   * @return cityName
   */
  @ApiModelProperty(example = "Seattle")
  public String getCityName() {
    return this.cityName;
  }

  /**
   * Get stateAcronym
   *
   * @return stateAcronym
   */
  @ApiModelProperty(example = "WA")
  public String getStateAcronym() {
    return this.stateAcronym;
  }

  /**
   * Get zipCode
   *
   * @return zipCode
   */
  @ApiModelProperty(example = "98122")
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Get countryName
   *
   * @return countryName
   */
  @ApiModelProperty(example = "USA")
  public String getCountryName() {
    return this.countryName;
  }

  @Override
  public String toString() {
    return this.getStreetNumber()
        + " " + this.getStreetName()
        + " " + this.getCityName()
        + " " + this.getStateAcronym()
        + " " + this.getZipCode()
        + " " + this.getCountryName();
  }

  /**
   * Compares the address against another to check for equality.
   *
   * @param obj another address
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Address)) {
      return false;
    }
    Address address = (Address) obj;
    return getStreetNumber().equals(address.getStreetNumber())
        && getStreetName().equals(address.getStreetName())
        && getCityName().equals(address.getCityName())
        && getStateAcronym().equals(address.getStateAcronym())
        && getZipCode().equals(address.getZipCode())
        && getCountryName().equals(address.getCountryName());
  }

  /**
   * Get the int representation of this address.
   *
   * @return int representation of this address
   */
  @Override
  public int hashCode() {
    return Objects
        .hash(getStreetNumber(), getStreetName(), getCityName(), getStateAcronym(), getZipCode(),
            getCountryName());
  }
}

