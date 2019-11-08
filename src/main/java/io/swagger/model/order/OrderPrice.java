package io.swagger.model.order;

import java.time.LocalDateTime;

public class OrderPrice {

  private String orderId;
  private Double orderPrice;
  private LocalDateTime localDateTime;

  public OrderPrice(String orderId, Double orderPrice) {
    this.orderId = orderId;
    this.orderPrice = orderPrice;
    this.localDateTime = LocalDateTime.now();
  }
}
