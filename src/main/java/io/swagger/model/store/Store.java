package io.swagger.model.store;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import io.swagger.model.customer.CreditCard;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "stores")
public class Store {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private String address;
  private List<String> currentOrderIds;
  private List<String> completedOrderIds;
  private Menu menu;

  public Store(@NotNull String name, @NotNull String address, Menu menu) {
    this.name = name;
    this.address = address;
    this.menu = menu;
    this.currentOrderIds = new ArrayList <>();
    this.completedOrderIds = new ArrayList <>();
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
    return customerCard.getCardNumber().matches("[0-9]+")
        && customerCard.getCardNumber().length() == 16
        && customerCard.getCvv().matches("[0-9]+")
        && customerCard.getCvv().length() == 3
        && validateExpiration(customerCard);
  }

  private Boolean validateExpiration(CreditCard customerCard) {
    if ((customerCard.getExpirationYear()) > LocalDate.now().getYear()) {
      return true;
    } else if ((customerCard.getExpirationYear()) < LocalDate.now().getYear()) {
      return false;
    } else {
      return customerCard.getExpirationMonth() >= LocalDate.now().getMonthValue();
    }
  }

  /**
   * Moves an orderId into the list of current orders.
   * @param orderId an orderId provided by the customer at checkout
   */
  public void processOrder(String orderId) {
    this.currentOrderIds.add(orderId);
  }

  public List <String> getCurrentOrderIds() {
    return this.currentOrderIds;
  }

  /**
   * Moves an orderId from current orders into the list of completed orderIds.
   * @param orderId orderId being completed
   */
  public void completeOrder(String orderId){
    for(int i = 0; i < currentOrderIds.size(); i++)
      if (currentOrderIds.get(i) == orderId) {
        currentOrderIds.remove(i);
        completedOrderIds.add(orderId);
      }
  }

  public List <String> getCompletedOrders() {
    return this.completedOrderIds;
  }
}
