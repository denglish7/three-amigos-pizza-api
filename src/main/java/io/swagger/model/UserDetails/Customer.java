package io.swagger.model.UserDetails;

public class Customer extends User{
  Integer customerId;
  String customerName;
  Integer customerPhone;


  public Customer(String userID, String password, Integer customerId, String customerName, Integer customerPhone) {
    super(userID, password);
    customerId = this.customerId;
    customerName = this.customerName;
    customerPhone = this.customerPhone;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Integer getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(Integer customerPhone) {
    this.customerPhone = customerPhone;
  }

//  public Order placeOrder() {
//    return new Order();
//  }
}
