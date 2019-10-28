package io.swagger.model.StoreDetails;

import io.swagger.model.OrderDetails.Order;

public class Store {
  Integer storeId;
  Address address;

  public Store(Integer storeId, Address address) {
    this.storeId = storeId;
    this.address = address;
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

  public Boolean processOrder(Order order) {
    return true;
  }
}
