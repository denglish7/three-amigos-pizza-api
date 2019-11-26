package io.swagger.model.order;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Menu;
import io.swagger.model.store.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

  private Topping topping1, topping2;
  private List<Topping> oneTopping, twoToppings;
  private Size size1, size2;
  private Crust crust1;
  private Pizza pizza1, pizza2;
  private OrderPizza orderPizza1, orderPizza2, orderPizza3, orderPizza4;
  private Special special1;
  private OrderSpecial orderSpecial1;
  private List<OrderPizza> pizzasInSpecial1;
  private Customer customer;
  private Store store1;
  private Order order;

  @Before
  public void setUp() {
    topping1 = new Topping("mushrooms", 2.1);
    topping2 = new Topping("pep", 3.0);
    oneTopping = new ArrayList<>();
    oneTopping.add(topping1);
    twoToppings = new ArrayList<>();
    twoToppings.add(topping1);
    twoToppings.add(topping2);
    crust1 = new Crust(3.0, false, "classic");
    pizza1 = new Pizza("its a Pizza", crust1, twoToppings);
    pizza2 = new Pizza("another Pizza", crust1, oneTopping);
    size1 = new Size("Large", 2.0);
    size2 = new Size("Medium", 1.5);
    orderPizza1 = new OrderPizza(pizza1, size1);
    orderPizza2 = new OrderPizza(pizza2, size2);
    orderPizza3 = new OrderPizza(pizza1, size2);
    orderPizza4 = new OrderPizza(pizza2, size1);
    special1 = new Special("Half off large", 0.5, 1, size1);
    pizzasInSpecial1 = new ArrayList<>();
    pizzasInSpecial1.add(orderPizza1);
    orderSpecial1 = new OrderSpecial(special1, pizzasInSpecial1);
    customer = new Customer("King kong", "206-555-5555", "123 Broadway");
    store1 = new Store("Pagliacci", "123 Broadway", new Menu());
    order = new Order(store1.get_id());
  }

  @After
  public void tearDown() {

  }

  @Test
  public void get_id() {
    Order aorder = new Order(store1.get_id());
    System.out.println(aorder.toString());
    assertNotNull(order.get_id());
  }

  @Test
  public void setTimeCreated() {
    LocalDateTime now = LocalDateTime.now();
    order.setTimeCreated(now);
    assertEquals(now, order.getTimeCreated());
  }

  @Test
  public void getStoreId() {
    String newId = "a new id";
    order.setStoreId(newId);
    assertEquals(newId, order.getStoreId());
  }

  @Test
  public void setStoreId() {
    String storeId = new ObjectId().toString();
    order.setStoreId(storeId);
    assertEquals(storeId, order.getStoreId());
  }

  @Test
  public void getCustomer() {
    assertNull(order.getCustomer());
  }

  @Test
  public void setCustomer() {
    order.setCustomer(customer);
    assertEquals(customer, order.getCustomer());
  }

  @Test
  public void getOrderItems() {
    order.addPizza(orderPizza1);
    order.addPizza(orderPizza2);
    order.addPizza(orderPizza3);
    order.addPizza(orderPizza4);
    assertEquals(4, order.getOrderItems().getPizzas().size());
    assertEquals(orderPizza1, order.getOrderItems().getPizzas().get(orderPizza1.get_id()));
  }

  @Test
  public void removePizza() {
    order.addPizza(orderPizza1);
    order.removePizza(orderPizza1.get_id());
    assertTrue(order.isEmpty());
  }

  @Test
  public void addSpecial() {
    order.addSpecial(orderSpecial1);
    assertEquals(1, order.getOrderItems().getSpecials().size());
  }

  @Test
  public void removeSpecialById() {
    order.addSpecial(orderSpecial1);
    assertEquals(1, order.getOrderItems().getSpecials().size());
    order.removeSpecialById(orderSpecial1.getSpecialId());
    assertEquals(0, order.getOrderItems().getSpecials().size());
    order.removeSpecialById(orderSpecial1.getSpecialId());
    assertEquals(0, order.getOrderItems().getSpecials().size());
  }

  @Test
  public void getCreditCard() {
    order.setCustomer(customer);
    assertNull(order.getCreditCard());
  }

  @Test
  public void isNotEmpty() {
    order.addPizza(orderPizza1);
    assertFalse(order.isEmpty());
  }

  @Test
  public void getPriceNone() {
    Double expectedPrice = 0.0;
    assertEquals(expectedPrice, order.getPrice());
  }

  @Test
  public void getPriceNone2() {
    Double expectedPrice = 0.0;
    assertEquals(expectedPrice, order.getPrice());
  }

  @Test
  public void getPrice1() {
    Double expectedPrice = orderPizza1.getPrice();
    order.addPizza(orderPizza1);
    assertEquals(expectedPrice, order.getPrice());
  }

  @Test
  public void getPrice2() {
    Double expectedPrice =
        orderPizza1.getPrice() + orderPizza2.getPrice() + orderPizza3.getPrice() + orderPizza4
            .getPrice() + orderSpecial1.getPrice();
    order.addPizza(orderPizza1);
    order.addPizza(orderPizza2);
    order.addPizza(orderPizza3);
    order.addPizza(orderPizza4);
    order.addSpecial(orderSpecial1);
    assertEquals(expectedPrice, order.getPrice(), 0.0001);
  }

  @Test
  public void getPrice3() {
    Double expectedPrice = orderPizza3.getPrice() + orderPizza4.getPrice();
    order.addPizza(orderPizza1);
    order.addPizza(orderPizza2);
    order.removePizza(orderPizza2.get_id());
    order.addPizza(orderPizza3);
    order.addPizza(orderPizza4);
    order.removePizza(orderPizza1.get_id());
    order.addSpecial(orderSpecial1);
    order.removeSpecialById(orderSpecial1.getSpecialId());
    assertEquals(expectedPrice, order.getPrice(), 0.0001);
  }

  @Test
  public void testToString() {
    order.setCustomer(customer);
    assertTrue(order.toString().contains(new StringBuffer("Customer: " + customer.getName())));
  }
}
