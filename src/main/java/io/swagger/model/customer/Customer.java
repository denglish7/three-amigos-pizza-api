package io.swagger.model.customer;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private String address;

  public Customer(@NotNull String name, @NotNull String phone,
      @NotNull String address) {
    this.name = name;
    this.phone = phone;
    this.address = address;
  }

  /**
   * Get _id
   *
   * @return _id
   */
  public String get_id() {
    return _id;
  }


  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(example = "Daniel")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get phone
   *
   * @return phone
   */
  @ApiModelProperty(example = "392-048-2753")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Get address
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
