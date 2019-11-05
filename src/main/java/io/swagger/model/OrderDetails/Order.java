package io.swagger.model.OrderDetails;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.StoreDetails.Address;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
  Integer storeId;
  Integer orderId;
  String customerId;
  String customerName;
  Cart cart;
  LocalDate timeCreated;
  LocalDate timeCompleted;
  OrderStatus status;

  public Order(Integer storeId, Integer orderId, String customerId, String customerName, LocalDate timeCreated, LocalDate timeCompleted, OrderStatus status) {
    this.storeId = storeId;
    this.orderId = orderId;
    this.customerId = customerId;
    this.customerName = customerName;
    this.timeCreated = timeCreated;
    this.timeCompleted = timeCompleted;
    this.status = status;
  }

  public Integer getStoreId() {
    return storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  @ApiModelProperty(required=true, dataType = "org.joda.time.LocalDate")
  public LocalDate getTimeCreated() {
    return timeCreated;
  }

  public void setTimeCreated(LocalDate timeCreated) {
    this.timeCreated = timeCreated;
  }

  @ApiModelProperty(required=true, dataType = "org.joda.time.LocalDate")
  public LocalDate getTimeCompleted() {
    return timeCompleted;
  }

  public void setTimeCompleted(LocalDate timeCompleted) {
    this.timeCompleted = timeCompleted;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Double calculatePrice() {
    return this.cart.calculatePrice();
  }

  public DeliveryOrder placeOrderForDelivery(Address customerAddress, Integer customerPhone) {
    return new DeliveryOrder(customerAddress, customerPhone);
  }

  public PickupOrder placeOrderForPickup(Integer customerPhone) {
    return new PickupOrder(customerPhone);
  }
}
