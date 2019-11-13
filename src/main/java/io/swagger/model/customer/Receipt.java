package io.swagger.model.customer;


import java.util.List;

public class Receipt {
  private String storeName;
  private String customerName;
  private String orderId;
  private List <String> pizzas;
  private String specialApplied;
  private Double pricePaid;

  /**
   * Constructor for a Receipt using Order Details.
   * @param storeName name of the store that fulfilled order
   * @param customerName customer for the other
   * @param orderId the order's id
   * @param pizzas names of the pizza's ordered
   * @param pricePaid price paid in dollars
   */
  public Receipt(String storeName, String customerName, String orderId, List <String> pizzas, String specialApplied, Double pricePaid) {
    this.storeName = storeName;
    this.customerName = customerName;
    this.orderId = orderId;
    this.pizzas = pizzas;
    this.specialApplied = specialApplied;
    this.pricePaid = pricePaid;
  }

  @Override
  public String toString() {
    return
        "Store: " + storeName + '\'' +
        "Customer: " + customerName + '\'' +
        "Order Id: " + orderId + '\'' +
        "Pizzas: " + pizzas + '\'' +
        "Promotion Applied: " + specialApplied + '\'' +
        "Total: $" + pricePaid;
  }
}
