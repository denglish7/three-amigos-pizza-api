package io.swagger.model.customer;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.store.Address;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

  @ApiModelProperty(hidden=true)
  private String _id;
  private String name;
  private String phone;
  private Address address;

  public Customer(String _id, String name, String phone,
      Address address) {
    this._id = _id;
    this.name = name;
    this.phone = phone;
    this.address = address;
  }

  /**
   * Get _id
   * @return _id
   */
  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  /**
   * Get name
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
   * Get Address
   * @return Address
   */
  @ApiModelProperty
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
