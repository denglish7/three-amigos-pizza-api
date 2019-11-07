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
  @DBRef
  private List<Pizza> pizzas;
  @DBRef
  private Customer customer;
  private String storeId;

//  @ApiModelProperty(hidden=true)
//  private OrderStatus status;


  public Order(List<Pizza> pizzas, Customer customer, String storeId) {
    this.pizzas = pizzas;
    this.customer = customer;
    this.storeId = storeId;
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
   * Get pizzas
   *
   * @return pizzas
   */
  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
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

}
