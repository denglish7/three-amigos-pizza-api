package io.swagger.model.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Priceable;
import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Pizza;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order implements Priceable {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private LocalDateTime timeCreated = LocalDateTime.now();
  @NotNull
  private String storeId;
  @NotNull
  private OrderItems orderItems;
  @DBRef
  private Customer customer;
  private Double price = BASE_PRICE;
  private String creditCard;

  public Order() {
  }

  public Order(@NotNull String storeId) {
    this.storeId = storeId;
    this.orderItems = new OrderItems();
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
   * Get customer
   *
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public Double getPrice() {
    return price;
  }

  @Override
  public void setPrice() {
    this.price = orderItems.getPrice(BASE_PRICE);
  }

  public OrderItems getOrderItems() {
    return orderItems;
  }

  public void addPizza(Pizza pizza) {
    orderItems.addPizza(pizza);
    setPrice();
  }

  public Pizza removePizzaById(String pizzaId) {
    Pizza removedPizza = orderItems.removePizzaById(pizzaId);
    setPrice();
    return removedPizza;
  }

  public void addSpecial(OrderSpecial special) {
    orderItems.addSpecial(special);
    setPrice();
  }

  public OrderSpecial removeSpecialById(String specialId) {
    OrderSpecial removedSpecial = orderItems.removeSpecialById(specialId);
    setPrice();
    return removedSpecial;
  }

  public void addCreditCard(String cardNum) {
    this.creditCard = cardNum;
  }

  public String getCreditCard() {
    return this.creditCard;
  }

  public Boolean isEmpty() {
    return orderItems.isEmpty();
  }
}
