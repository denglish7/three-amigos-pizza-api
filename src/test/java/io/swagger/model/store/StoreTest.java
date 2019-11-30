package io.swagger.model.store;

import io.swagger.model.customer.CreditCard;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StoreTest {

  private Store store;

  @Before
  public void setUp() throws Exception {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    Menu MENU = new Menu();
    store = new Store(STORENAME, ADDRESS, MENU);
  }


  @Test
  public void getName() {
    String STORENAME = "UptownGurl";
    assertEquals(STORENAME, store.getName());
  }

  @Test
  public void setName() {
    String NEWSTORENAME = "DownTownBoi";
    store.setName(NEWSTORENAME);
    assertEquals(NEWSTORENAME, store.getName());
  }

  @Test
  public void getMenuWithSpecial() {
    List <Special> exampleSpecials = new ArrayList <>();
    Special special = new Special();
    exampleSpecials.add(special);
    store.getMenu().addSpecials(exampleSpecials);
    assertTrue(store.getMenu().getSpecials().size() > 0);
  }

  @Test
  public void setMenuWithPizza() {
    List<Pizza> examplePizzas = new ArrayList <>();
    Pizza pizza = new Pizza();
    examplePizzas.add(pizza);
    store.getMenu().addPizzas(examplePizzas);
    assertTrue(store.getMenu().getPizzas().size() > 0);
  }

  @Test
  public void getAddress() {
    assertEquals("123 Jerry St.", store.getAddress());
  }

  @Test
  public void setAddress() {
    String NEWADDRESS = "456 Sherry Ave.";
    store.setAddress(NEWADDRESS);
    assertEquals("456 Sherry Ave.", store.getAddress());
  }

  @Test
  public void validateValidCard() {
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 2029;
    String CVV = "888";
    CreditCard creditCard = new CreditCard(CARDNUM, EXPMONTH, EXPYEAR, CVV);
    assertEquals(true, store.validateCard(creditCard));
  }

  @Test
  public void validateCardExpiredMonth() {
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 10;
    Integer EXPYEAR = 2019;
    String CVV = "888";
    CreditCard creditCard = new CreditCard(CARDNUM, EXPMONTH, EXPYEAR, CVV);
    assertEquals(false, store.validateCard(creditCard));
  }

  @Test
  public void validateCardExpiredYear() {
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 2018;
    String CVV = "888";
    CreditCard creditCard = new CreditCard(CARDNUM, EXPMONTH, EXPYEAR, CVV);
    assertEquals(false, store.validateCard(creditCard));
  }

  @Test
  public void validateCardInvalidCvv() {
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 2019;
    String CVV = "88";
    CreditCard creditCard = new CreditCard(CARDNUM, EXPMONTH, EXPYEAR, CVV);
    assertEquals(false, store.validateCard(creditCard));
  }

  @Test
  public void getCurrentOrders() {
    assertTrue(store.getCurrentOrderIds().isEmpty());
  }

  @Test
  public void completeOrderDoesNotExist() {
    String fakeOrderId = "srgdv";
    store.completeOrder(fakeOrderId);
    assertEquals(0, store.getCompletedOrders().size(), 0);
  }

  @Test
  public void processOrder() {
    String fakeOrderId = "srgdv";
    store.processOrder(fakeOrderId);
    assertEquals(1, store.getCurrentOrderIds().size(), 0);
  }

  @Test
  public void completeOrderSuccess() {
    String fakeOrderId = "srgdv";
    store.processOrder(fakeOrderId);
    store.completeOrder(fakeOrderId);
    assertEquals(1, store.getCompletedOrders().size(), 0);
  }

}