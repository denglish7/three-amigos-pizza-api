package io.swagger.model.classes.StoreDetails;

import io.swagger.model.classes.OrderDetails.Address;
import io.swagger.model.classes.OrderDetails.Order;

public class Store {
  Integer storeId;
  Address address;
  DeliveryDriver deliveryDriver;

  public Store(Integer storeId, Address address, DeliveryDriver deliveryDriver) {
    this.storeId = storeId;
    this.address = address;
    this.deliveryDriver = deliveryDriver;
  }

  public Integer getStoreId() {
    return storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public DeliveryDriver getDeliveryDriver() {
    return deliveryDriver;
  }

  public void setDeliveryDriver(DeliveryDriver deliveryDriver) {
    this.deliveryDriver = deliveryDriver;
  }

  public Boolean processOrder(Order order) {
    return true;
  }
}
