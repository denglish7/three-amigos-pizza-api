package io.swagger.model.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Pizza;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String storeId;
  @NotNull
  private OrderDetails orderDetails;
  @DBRef
  private Customer customer;

//  @ApiModelProperty(hidden=true)
//  private OrderStatus status;

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

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  /**
   * Get orderDetails
   * @return orderDetails
   */
  public OrderDetails getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
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

  public void addPizzas(List<Pizza> pizzas) {this.orderDetails.addPizzas(pizzas);}

}
