package io.swagger.model.OrderDetails;

public class PickupOrder {
  Integer customerPhone;

  public PickupOrder(Integer customerPhone) {
    this.customerPhone = customerPhone;
  }

  public Integer getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(Integer customerPhone) {
    this.customerPhone = customerPhone;
  }
}
