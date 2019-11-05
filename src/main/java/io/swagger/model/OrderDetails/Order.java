package io.swagger.model.OrderDetails;

import io.swagger.model.StoreDetails.Address;
import java.time.LocalDateTime;

public class Order {
  Integer storeId;
  Integer orderId;
  String customerId;
  String customerName;
  Cart cart;
  LocalDateTime timeCreated;
  LocalDateTime timeCompleted;
  OrderStatus status;

  public Order(Integer storeId, Integer orderId, String customerId, String customerName, LocalDateTime timeCreated, LocalDateTime timeCompleted, OrderStatus status) {
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

  public LocalDateTime getTimeCreated() {
    return timeCreated;
  }

  public void setTimeCreated(LocalDateTime timeCreated) {
    this.timeCreated = timeCreated;
  }

  public LocalDateTime getTimeCompleted() {
    return timeCompleted;
  }

  public void setTimeCompleted(LocalDateTime timeCompleted) {
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