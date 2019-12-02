package io.swagger.model.customer;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "receipts")
public class Receipt {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String timeOfPurchase;
  private String storeName;
  private String customerName;
  private String orderId;
  private List <String> pizzas;
  private List <String> specialApplied;
  private String paymentMethod;
  private Double pricePaid;

  /**
   * Constructor for a Receipt using Order Details.
   * timeOfPurchase time that the order was processed
   * @param storeName name of the store that fulfilled order
   * @param customerName customer for the other
   * @param orderId the order's id
   * @param pizzas names of the pizza's ordered
   * @param pricePaid price paid in dollars
   */
  public Receipt(String storeName, String customerName, String orderId, List <String> pizzas, List <String> specialApplied, String paymentMethod, Double pricePaid) {
    this.timeOfPurchase = LocalDateTime.now().toLocalDate().toString();
    this.storeName = storeName;
    this.customerName = customerName;
    this.orderId = orderId;
    this.pizzas = pizzas;
    this.specialApplied = specialApplied;
    this.paymentMethod = paymentMethod;
    this.pricePaid = pricePaid;
  }

  private String get_id() {
    return this._id;
  }

  @Override
  public String toString() {
    String receiptId = this.get_id();
    return
        "Receipt #: " + receiptId + '\'' +
        "Time of Purchase: " + timeOfPurchase + '\'' +
        "Store: " + storeName + '\'' +
        "Customer: " + customerName + '\'' +
        "Order Id: " + orderId + '\'' +
        "Pizzas: " + pizzas + '\'' +
        "Promotion Applied: " + specialApplied + '\'' +
        "Payment Method: Card Ending In " + paymentMethod + '\'' +
        "Total: $" + pricePaid;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getTimeOfPurchase() {
    return timeOfPurchase;
  }

  public void setTimeOfPurchase(String timeOfPurchase) {
    this.timeOfPurchase = timeOfPurchase;
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public List<String> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List<String> pizzas) {
    this.pizzas = pizzas;
  }

  public List<String> getSpecialApplied() {
    return specialApplied;
  }

  public void setSpecialApplied(List<String> specialApplied) {
    this.specialApplied = specialApplied;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Double getPricePaid() {
    return pricePaid;
  }

  public void setPricePaid(Double pricePaid) {
    this.pricePaid = pricePaid;
  }
}
