package io.swagger.model.CustomerDetails;

import io.swagger.model.OrderDetails.Order;
import io.swagger.model.StoreDetails.Address;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Customer {

  private String _id;
  @DBRef
  private String name;
  private Integer phone;
  private Address address;
  private Order currentOrder;
  private List<Order> pastOrders;

  public Customer(String _id, String name, Integer phone,
      Address address, Order currentOrder,
      List<Order> pastOrders) {
    this._id = _id;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.currentOrder = currentOrder;
    this.pastOrders = pastOrders;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Order getCurrentOrder() {
    return currentOrder;
  }

  public void setCurrentOrder(Order currentOrder) {
    this.currentOrder = currentOrder;
  }

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
