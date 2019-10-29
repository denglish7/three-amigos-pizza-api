package io.swagger.model.OrderDetails;

import io.swagger.model.StoreDetails.Address;

public class DeliveryOrder {
  Address customerAddress;
  Integer customerPhone;

  public DeliveryOrder(Address customerAddress, Integer customerPhone) {
    this.customerAddress = customerAddress;
    this.customerPhone = customerPhone;
  }

  public Address getCustomerAddress() {
    return customerAddress;
  }

  public void setCustomerAddress(Address customerAddress) {
    this.customerAddress = customerAddress;
  }

  public Integer getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(Integer customerPhone) {
    this.customerPhone = customerPhone;
  }

  public Double calculateDeliveryCost() {
    return 1.0;
  }
}
