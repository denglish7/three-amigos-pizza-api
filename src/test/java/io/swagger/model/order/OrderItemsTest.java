package io.swagger.model.order;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OrderItemsTest {

  private Topping topping1, topping2;
  private List<Topping> oneTopping, twoToppings;
  private Size size1, size2;
  private Crust crust1;
  private Pizza pizza1, pizza2;
  private OrderPizza orderPizza1, orderPizza2;
  private Special special1;
  private OrderSpecial orderSpecial1;
  private List<OrderPizza> pizzasInSpecial1;
  private OrderItems orderItems;

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
    special1 = new Special("Half off large", 0.5, 1, size1);
    pizzasInSpecial1 = new ArrayList<>();
    pizzasInSpecial1.add(orderPizza1);
    orderSpecial1 = new OrderSpecial(special1, pizzasInSpecial1);
    orderItems = new OrderItems();
  }


  @Test
  public void getPizzasNone() {
    assertTrue(orderItems.getPizzas().isEmpty());
  }

  @Test
  public void addPizza() {
    orderItems.addPizza(orderPizza1);
    assertEquals(orderPizza1, orderItems.getPizzas().get(orderPizza1.get_id()));
  }

  @Test
  public void getSpecialsNone() {
    assertTrue(orderItems.getSpecials().isEmpty());
  }

  @Test
  public void addSpecial() {
    orderItems.addSpecial(orderSpecial1);
    assertEquals(orderSpecial1, orderItems.getSpecials().get(orderSpecial1.getSpecialId()));
  }

  @Test
  public void removePizza() {
    orderItems.addPizza(orderPizza1);
    orderItems.removePizza(orderPizza1.get_id());
    assertTrue(orderItems.getPizzas().isEmpty());
  }

  @Test
  public void removeSpecialById() {
    orderItems.addSpecial(orderSpecial1);
    orderItems.removeSpecialById(orderSpecial1.getSpecialId());
  }

  @Test
  public void containsPizzaFalse() {
    assertFalse(orderItems.containsPizza(orderPizza1.get_id()));
  }

  @Test
  public void containsPizzaTrue() {
    orderItems.addPizza(orderPizza1);
    assertTrue(orderItems.containsPizza(orderPizza1.get_id()));
  }

  @Test
  public void containsSpecialFalse() {
    assertFalse(orderItems.containsSpecial(orderSpecial1.getSpecialId()));
  }

  @Test
  public void containsSpecialTrue() {
    orderItems.addSpecial(orderSpecial1);
    assertTrue(orderItems.containsSpecial(orderSpecial1.getSpecialId()));
  }

  @Test
  public void isEmptyTrue() {
    assertTrue(orderItems.isEmpty());
  }

  @Test
  public void isEmptyFalse() {
    orderItems.addPizza(orderPizza1);
    assertFalse(orderItems.isEmpty());
  }

  @Test
  public void getPriceNone() {
    Double basePrice = 0.0;
    assertEquals(basePrice, orderItems.getPrice(basePrice));
  }

  @Test
  public void getPrice() {
    Double basePrice = 0.0;
    orderItems.addPizza(orderPizza1);
    orderItems.addSpecial(orderSpecial1);
    Double expectedPrice = basePrice + orderPizza1.getPrice() + orderSpecial1.getPrice();
    assertEquals(expectedPrice, orderItems.getPrice(basePrice));
  }

  @Test
  public void getSpecialNames() {

  }

  @Test
  public void getPizzaNames() {

  }

  @Test
  public void toStringTest() {

  }
}
