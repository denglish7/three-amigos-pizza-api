package io.swagger.model.store;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import io.swagger.model.customer.Receipt;
import io.swagger.model.order.Order;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "stores")
public class Store {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private String location;

  private HashMap <String, Order> currentOrders;

  private HashMap <String, Order> completedOrders;

  private Menu menu;

  public Store(@NotNull String name, @NotNull String location,
      Menu menu) {
    this.name = name;
    this.location = location;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public Boolean validateCard(String customerCard) {
    if (customerCard.length() == 16) {
      return true;
    }
    return false;
  }

  public void processOrder(Order order) {
    this.currentOrders.put(order.get_id(), order);
  }

//  public void completeOrder(String OrderId) {
//    Order completedOrder = this.currentOrders.get(OrderId);
//    Receipt customerReciept = new Receipt(
//        completedOrder.get_id(),
//        completedOrder.getOrderDetails().getPizzas(),
//        completedOrder.getPrice()
//        );
//
//    completedOrder.getCustomer().receipts.add(customerReciept);
//    this.completedOrders.put(completedOrder.get_id(), completedOrder);
//  }


}
