package io.swagger.model.customer;


import java.util.List;

public class Receipt {
  private String orderId;
  private List <String> pizzas;
  private Double pricePaid;

  public Receipt(String orderId, List <String> pizzas, Double pricePaid) {
    this.orderId = orderId;
    this.pizzas = pizzas;
    this.pricePaid = pricePaid;
  }

  @Override
  public String toString() {
    return "Receipt{" +
        "orderId='" + orderId + '\'' +
        ", pizzas=" + pizzas +
        ", pricePaid=" + pricePaid +
        '}';
  }
}
