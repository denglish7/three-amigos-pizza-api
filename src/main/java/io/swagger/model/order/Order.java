package io.swagger.model.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Priceable;
import io.swagger.model.customer.Customer;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
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
  

  public Order(@NotNull String storeId) {
    this._id = new ObjectId().toString();
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
   *
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
   *
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

  public OrderItems getOrderItems() {
    return orderItems;
  }

  public void addPizza(OrderPizza pizza) {
    orderItems.addPizza(pizza);
    setPrice();
  }

  public boolean removePizza(String pizzaId) {
    boolean pizzaRemoved = orderItems.removePizza(pizzaId);
    setPrice();
    return pizzaRemoved;
  }

  public void addSpecial(OrderSpecial special) {
    orderItems.addSpecial(special);
    setPrice();
  }

  public boolean removeSpecialById(String specialId) {
    boolean specialRemoved = orderItems.removeSpecialById(specialId);
    setPrice();
    return specialRemoved;
  }

  public Boolean isEmpty() {
    return orderItems.isEmpty();
  }

  @Override
  public Double getPrice() {
    return price;
  }

  @Override
  public void setPrice() {
    this.price = orderItems.getPrice(BASE_PRICE);
  }

  @Override
  public String toString() {
    String string = "Order Id: " + _id + '\'' +
        "Order Items: " + orderItems.toString() + '\'' +
        "Order price " + price + '\'';
    if (customer != null) {
      string += "Customer: " + customer.getName();
    }
    return string;
  }
}
