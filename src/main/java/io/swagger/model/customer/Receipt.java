package io.swagger.model.customer;


import java.util.List;

public class Receipt {
  private String customerName;
  private String orderId;
  private List <String> pizzas;
  private Double pricePaid;

  /**
   * Constructor for a Receipt using Order Details.
   * @param orderId order's id
   * @param pizzas names of the pizza's ordered
   * @param pricePaid price paid in dollars
   */
  public Receipt(String customerName, String orderId, List <String> pizzas, Double pricePaid) {
    this.customerName = customerName;
    this.orderId = orderId;
    this.pizzas = pizzas;
    this.pricePaid = pricePaid;
  }

  @Override
  public String toString() {
    return
        "Customer: " + customerName + '\'' +
        "Order Id: " + orderId + '\'' +
        "Pizzas: " + pizzas + '\'' +
        "Total: $" + pricePaid;
  }
}
