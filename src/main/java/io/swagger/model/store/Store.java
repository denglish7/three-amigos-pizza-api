package io.swagger.model.store;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

import io.swagger.model.customer.CreditCard;
import io.swagger.model.order.Order;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Document(collection = "stores")
public class Store {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private String address;
  private HashMap <String, Order> currentOrders;
  private HashMap <String, Order> completedOrders;
  private Menu menu;

  public Store(@NotNull String name, @NotNull String address,
      Menu menu) {
    this.name = name;
    this.address = address;
    this.menu = menu;
    this.currentOrders = new HashMap <>();
    this.completedOrders = new HashMap <>();
  }

  public String get_id() {
    return _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public String getAddress() {
    return address;
  }

  /**
   * Update the Store's Address.
   * @param address a new address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Validates a customer's card based on length, expiration, and data type.
   * @param customerCard customer's credit card
   * @return true if valid, false otherwise
   */
  public Boolean validateCard(CreditCard customerCard) {
    if (customerCard.getCardNumber().matches("[0-9]+")
        && customerCard.getCardNumber().length() == 16
        && customerCard.getExpirationMonth() >= LocalDate.now().getMonthValue()
        && customerCard.getExpirationYear() >= LocalDate.now().getYear()
        && customerCard.getCvv().matches("[0-9]+")
        && customerCard.getCvv().length() == 3) {
    return true;
    } else {
      return false;
    }
  }

  /**
   * Moves an order into the list of current orders.
   * @param order an order provided by the customer at checkout
   */
  public void processOrder(Order order) {
    this.currentOrders.put(order.get_id(), order);
  }

  public List <String> getCurrentOrders() {
    List <String> currentOrderIds = new ArrayList <>();
    for (String key : this.currentOrders.keySet()) {
      String currentOrderId = currentOrders.get(key).get_id();
      currentOrderIds.add(currentOrderId);
    }
    return currentOrderIds;
  }

  /**
   * Moves an order from current orders into the list of completed orders.
   * @param orderId orderId being completed
   */
  public void completeOrder(String orderId) {
    Order completedOrder = this.currentOrders.get(orderId);
    this.completedOrders.put(completedOrder.get_id(), completedOrder);
  }

  public List <String> getCompletedOrders() {
    List <String> completedOrderIds = new ArrayList <>();
    for (String key : this.completedOrders.keySet()) {
      String completedOrderId = completedOrders.get(key).get_id();
      completedOrderIds.add(completedOrderId);
    }
    return completedOrderIds;
  }
}
