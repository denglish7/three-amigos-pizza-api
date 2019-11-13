package io.swagger.model.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Pizza;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

  private static final Double ORDER_BASE_PRICE = 0.0;

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private LocalDateTime timeCreated = LocalDateTime.now();
  @NotNull
  private String storeId;
  @NotNull
  private Double price = ORDER_BASE_PRICE;
  @NotNull
  private OrderDetails orderDetails;
  @DBRef
  private Customer customer;
  private String creditCard;

  public Order() {
  }

  public Order(@NotNull String storeId) {
    this.storeId = storeId;
    this.orderDetails = new OrderDetails();
  }

  /**
   * Get _id
   *
   * @return _id
   */
  public String get_id() {
    return _id;
  }

  /**
   * Get timeCreated
   * @return timeCreated
   */
  public LocalDateTime getTimeCreated() {
    return timeCreated;
  }

  public void setTimeCreated(LocalDateTime timeCreated) {
    this.timeCreated = timeCreated;
  }

  /**
   * Get storeId
   * @return storeId
   */
  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  /**
   * Get price
   * @return price
   */
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Get pizzas
   *
   * @return pizzas
   */
  public List<Pizza> getPizzas () {
    return orderDetails.getPizzas();
  }

  /**
   * Get customer
   *
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  public Pizza getPizzaInOrder(String pizzaId) {
    return orderDetails.getPizzaById(pizzaId);
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void addPizza(Pizza pizza) {
    orderDetails.addPizza(pizza);
    setPrice(getPrice() + pizza.getPrice());
  }

  public Pizza removePizzaById(String pizzaId) {
    Pizza removedPizza = orderDetails.removePizzaById(pizzaId);
    if (removedPizza != null) {
      setPrice(getPrice() - removedPizza.getPrice());
    }
    return removedPizza;
  }

  public void addCreditCard(String cardNum) {
    this.creditCard = cardNum;
  }

  public String getCreditCard() {
    return this.creditCard;
  }

  public Boolean isEmpty() {
    return orderDetails.isEmpty();
  }
}
