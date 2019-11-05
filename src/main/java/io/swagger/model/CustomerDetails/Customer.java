package io.swagger.model.CustomerDetails;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.OrderDetails.Order;
import io.swagger.model.StoreDetails.Address;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Customer {

  @ApiModelProperty(hidden=true)
  private String _id;
  private String name;
  private String phone;
  private Address address;
  private Order currentOrder;
  private List<Order> pastOrders;

  public Customer(String _id, String name, String phone,
      Address address) {
    this._id = _id;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.currentOrder = null;
    this.pastOrders = null;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  @ApiModelProperty(example = "Daniel")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ApiModelProperty(example = "392-048-2753")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @ApiModelProperty
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @ApiModelProperty
  public Order getCurrentOrder() {
    return currentOrder;
  }

  public void setCurrentOrder(Order currentOrder) {
    this.currentOrder = currentOrder;
  }

  @ApiModelProperty
  public List<Order> getPastOrders() {
    return pastOrders;
  }

  public void setPastOrders(List<Order> pastOrders) {
    this.pastOrders = pastOrders;
  }

  public void addCurrentOrderPastOrders(Order order) {
    this.pastOrders.add(order);
    this.currentOrder = null;
  }
}
