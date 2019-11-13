package io.swagger.api.order;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.customer.Customer;
import io.swagger.model.order.Order;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Menu;
import io.swagger.model.store.Store;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.SpecialRepository;
import io.swagger.repositories.StoreRepository;
import io.swagger.repositories.ToppingRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class OrderControllerTest {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private OrderController orderController;

  private Crust crust;
  private List<Topping> toppings;
  private List<String> toppingIds;
  private Size sizeLarge;
  private Pizza pizzaOnMenu, pizzaNotOnMenu;
  private List<Pizza> pizzasOnMenu;
  private Special special;
  private List<Special> specialsOnMenu;
  private Menu menu;
  private Store store;
  private Customer customer;


  @Before
  public void setUp() {
    makeCrust();
    makeToppings();
    makeSizes();
    makePizzas();
    makeSpecials();
    makeMenu();
    makeStore();
    makeCustomer();
  }

  @After
  public void tearDown() {
    orderRepository.deleteAll();
    pizzaRepository.deleteAll();
    crustRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    storeRepository.deleteAll();
    customerRepository.deleteAll();
    specialRepository.deleteAll();
  }

  @Test
  public void createOrderSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    assertEquals(store.get_id(), response.getBody().getStoreId());
  }

  @Test
  public void createOrderStoreIdNotFound() {
    String BAD_ORDER_ID = "8";
    String message = "storeId " + BAD_ORDER_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        BAD_ORDER_ID
    );
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

  @Test
  public void findByIdSuccess() {
    ResponseEntity<Order> createOrderResponse = orderController.createOrder(
        store.get_id()
    );
    ResponseEntity<Order> findOrderResponse = orderController
        .findById(createOrderResponse.getBody().get_id());
    assertTrue(findOrderResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void findByIdNotFound() {
    ResponseEntity<Order> createOrderResponse = orderController.createOrder(
        store.get_id()
    );
    String BAD_ID = createOrderResponse.getBody().get_id() + "1";
    ResponseEntity<Order> findOrderResponse = orderController.findById(BAD_ID);
    assertTrue(findOrderResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void setCustomerByIdSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        response.getBody().get_id(), customer.get_id()
    );
    assertEquals(customer.get_id(), setCustomerResponse.getBody().getCustomer().get_id());
  }

  @Test
  public void setCustomerByIdOrderNotFound() {
    String BAD_ORDER_ID = "5";
    String message = "orderId " + BAD_ORDER_ID + " not found.";
    orderController.createOrder(store.get_id());
    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        BAD_ORDER_ID, customer.get_id()
    );
    assertTrue(setCustomerResponse.getStatusCode().is4xxClientError());
    assertEquals(message, setCustomerResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void setCustomerByIdCustomerNotFound() {
    String BAD_CUSTOMER_ID = "5";
    String message = "customerId " + BAD_CUSTOMER_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        response.getBody().get_id(), BAD_CUSTOMER_ID
    );
    assertTrue(setCustomerResponse.getStatusCode().is4xxClientError());
    assertEquals(message, setCustomerResponse.getHeaders().getFirst("message"));
  }

  @Test //TODO: fix this bug.
  public void samePizzaTwice() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    response = orderController.addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    System.out.println("price 1: " + response.getBody().getPrice());
    response = orderController.addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    System.out.println("price 2: " + response.getBody().getPrice());
    response = orderController.removePizza(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    System.out.println("price 3: " + response.getBody().getPrice());
  }

  @Test
  public void addCustomPizzaSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    ResponseEntity<Order> addPizzaResponse = orderController
        .addCustomPizza(orderId, crust.get_id(), toppingIds, sizeLarge.get_id());
    System.out.println(addPizzaResponse.getHeaders().getFirst("message"));
    assertTrue(addPizzaResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void addCustomPizzaOrderNotFound() {
    String BAD_ORDER_ID = "5";
    String message = "orderId " + BAD_ORDER_ID + " not found.";
    orderController.createOrder(store.get_id());
    ResponseEntity<Order> addPizzaResponse = orderController.addCustomPizza(
        BAD_ORDER_ID, crust.get_id(), toppingIds, sizeLarge.get_id()
    );
    assertTrue(addPizzaResponse.getStatusCode().is4xxClientError());
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addCustomPizzaInvalidPizza() {
    String BAD_CRUST_ID = "42";
    String message = "crustId " + BAD_CRUST_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    ResponseEntity<Order> addPizzaResponse = orderController.addCustomPizza(
        orderId, BAD_CRUST_ID, toppingIds, sizeLarge.get_id()
    );
    assertTrue(addPizzaResponse.getStatusCode().is4xxClientError());
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addPizzaByIdSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    ResponseEntity<Order> addPizzaResponse = orderController
        .addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    assertTrue(addPizzaResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void addPizzaByIdOrderNotFound() {
    String BAD_ORDER_ID = "12";
    String message = "orderId " + BAD_ORDER_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    ResponseEntity<Order> addPizzaResponse = orderController
        .addPizzaById(BAD_ORDER_ID, pizzaOnMenu.get_id(), sizeLarge.get_id());
    assertTrue(addPizzaResponse.getStatusCode().is4xxClientError());
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addPizzaByIdPizzaNotOnMenu() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );

    Menu blankMenu = new Menu();
    store.setMenu(blankMenu);
    storeRepository.save(store);

    ResponseEntity<Order> addPizzaResponse = orderController.addPizzaById(
        response.getBody().get_id(),
        pizzaOnMenu.get_id(),
        sizeLarge.get_id()
    );

    String message = "pizzaId " + pizzaOnMenu.get_id()
        + " not found in menu associated with this order.";
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addPizzaByIdSizeNotFound() {
    String BAD_SIZE_ID = "42";
    String message = "sizeId " + BAD_SIZE_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    ResponseEntity<Order> addPizzaResponse = orderController
        .addPizzaById(orderId, pizzaOnMenu.get_id(), BAD_SIZE_ID);
    assertTrue(addPizzaResponse.getStatusCode().is4xxClientError());
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void removePizzaSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    orderController.addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    ResponseEntity<Order> removePizzaResponse = orderController
        .removePizza(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    assertTrue(removePizzaResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void removePizzaOrderNotFound() {
    String BAD_ORDER_ID = "42";
    String message = "orderId " + BAD_ORDER_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    orderController.addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    ResponseEntity<Order> removePizzaResponse = orderController
        .removePizza(BAD_ORDER_ID, pizzaOnMenu.get_id(), sizeLarge.get_id());
    assertTrue(removePizzaResponse.getStatusCode().is4xxClientError());
    assertEquals(message, removePizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void removePizzaPizzaNotFound() {
    Size sizeSmall = new Size("small", 8.0);
    sizeRepository.insert(sizeSmall);
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    orderController.addPizzaById(orderId, pizzaOnMenu.get_id(), sizeLarge.get_id());
    ResponseEntity<Order> removePizzaResponse = orderController
        .removePizza(orderId, pizzaOnMenu.get_id(), sizeSmall.get_id());
    assertTrue(removePizzaResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void addSpecialSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    List<String> pizzaIds = new ArrayList<>();
    pizzaIds.add(pizzaOnMenu.get_id());
    pizzaIds.add(pizzaOnMenu.get_id());
    ResponseEntity<Order> addSpecialResponse = orderController
        .addSpecial(orderId, special.get_id(), pizzaIds);
    assertTrue(addSpecialResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void addSpecialAlreadyAdded() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    List<String> pizzaIds = new ArrayList<>();
    pizzaIds.add(pizzaOnMenu.get_id());
    pizzaIds.add(pizzaOnMenu.get_id());
    orderController.addSpecial(orderId, special.get_id(), pizzaIds);
    ResponseEntity<Order> addSpecialAgainResponse = orderController
        .addSpecial(orderId, special.get_id(), pizzaIds);
    assertTrue(addSpecialAgainResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void addSpecialNotOnMenu() {
    Special newSpecial = new Special("Buy 3 Large, get 25% off", 0.75, 3, sizeLarge);
    specialRepository.insert(newSpecial);
    List<String> pizzaIds = new ArrayList<>();
    pizzaIds.add(pizzaOnMenu.get_id());
    pizzaIds.add(pizzaOnMenu.get_id());
    pizzaIds.add(pizzaOnMenu.get_id());
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    ResponseEntity<Order> addSpecialResponse = orderController
        .addSpecial(orderId, newSpecial.get_id(), pizzaIds);
    assertTrue(addSpecialResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void addSpecialWrongNumberPizzas() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    List<String> pizzaIds = new ArrayList<>();
    pizzaIds.add(pizzaOnMenu.get_id());
    orderController.addSpecial(orderId, special.get_id(), pizzaIds);
    ResponseEntity<Order> addSpecialAgainResponse = orderController
        .addSpecial(orderId, special.get_id(), pizzaIds);
    assertTrue(addSpecialAgainResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void addSpecialPizzaNotOnMenu() {
    ResponseEntity<Order> response = orderController.createOrder(
        store.get_id()
    );
    String orderId = response.getBody().get_id();
    List<String> pizzaIds = new ArrayList<>();
    pizzaIds.add(pizzaOnMenu.get_id());
    pizzaIds.add(pizzaNotOnMenu.get_id());
    orderController.addSpecial(orderId, special.get_id(), pizzaIds);
    ResponseEntity<Order> addSpecialResponse = orderController
        .addSpecial(orderId, special.get_id(), pizzaIds);
    assertTrue(addSpecialResponse.getStatusCode().is4xxClientError());
  }

  private void makeCrust() {
    crust = new Crust(4.50, false, "thin crust");
    crustRepository.insert(crust);
  }

  private void makeToppings() {
    Topping topping = new Topping("pepperoni", 2.0);
    Topping topping2 = new Topping("sausage", 2.0);
    toppingRepository.insert(topping);
    toppingRepository.insert(topping2);
    toppings = new ArrayList<>();
    toppings.add(topping);
    toppings.add(topping2);
    toppingIds = new ArrayList<>();
    for (Topping t : toppings) {
      toppingIds.add(t.get_id());
    }
  }

  private void makeSizes() {
    sizeLarge = new Size("Large", 10.0);
    sizeRepository.insert(sizeLarge);
  }

  private void makePizzas() {
    pizzaOnMenu = new Pizza(
        "Pepperoni",
        crust,
        toppings
    );
    pizzaNotOnMenu = new Pizza(
        "Cheese",
        crust,
        new ArrayList<>()
    );
    pizzaRepository.insert(pizzaOnMenu);
    pizzaRepository.insert(pizzaNotOnMenu);
    pizzasOnMenu = new ArrayList<>();
    pizzasOnMenu.add(pizzaOnMenu);
  }

  private void makeSpecials() {
    special = new Special("2 Larges half off", 0.5, 2, sizeLarge);
    specialRepository.insert(special);
    specialsOnMenu = new ArrayList<>();
    specialsOnMenu.add(special);
  }

  private void makeMenu() {
    menu = new Menu();
    menu.addPizzas(pizzasOnMenu);
    menu.addSpecials(specialsOnMenu);
  }

  private void makeCustomer() {
    customer = new Customer(
        "Daniel English",
        "395-328-3943",
        "1492 1st Ave"
    );
    customerRepository.insert(customer);
  }

  private void makeStore() {
    store = new Store("Seattle", "1111 1st Ave S", menu);
    storeRepository.insert(store);
  }
}