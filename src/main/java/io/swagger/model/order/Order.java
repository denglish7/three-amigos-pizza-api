package io.swagger.model.order;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Pizza;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
  @ApiModelProperty(hidden=true)
  private String _id;
  @ApiModelProperty(hidden=true)
  private List<Pizza> pizzas;
  @ApiModelProperty(hidden=true)
  private Customer customer;

//  @ApiModelProperty(hidden=true)
//  private OrderStatus status;


  public Order() {
  }

  public Order(List<Pizza> pizzas, Customer customer) {
    this.pizzas = pizzas;
    this.customer = customer;
  }

  /**
   * Get _id
   * @return _id
   */
  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  /**
   * Get pizzas
   * @return pizzas
   */
  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void addPizza(Pizza pizza) {
    this.pizzas.add(pizza);
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  /**
   * Get customer
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
