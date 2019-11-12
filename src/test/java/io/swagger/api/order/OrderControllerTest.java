package io.swagger.api.order;

import static org.junit.Assert.assertEquals;

import io.swagger.model.customer.Customer;
import io.swagger.model.store.Menu;
import io.swagger.model.order.Order;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Topping;
import io.swagger.model.store.Store;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.MenuRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
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
  private StoreRepository storeRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private OrderController orderController;

  private Crust crust;
  private Topping topping;
  private Topping topping2;
  private List<String> toppingIds;
  private Pizza pizza;
  private Store store;
  private Order order;
  private Customer customer;
  private List<String> pizzaIdsToAdd;
  private List<Pizza> pizzasToAdd;
  private Menu menu;

  @Before
  public void setUp() {
    pizzaRepository.deleteAll();
    crust = new Crust(4.50, false, "thin crust");
    crustRepository.insert(crust);
    topping = new Topping("pepperoni", .10);
    toppingRepository.insert(topping);
    topping2 = new Topping("sausage", .10);
    toppingRepository.insert(topping2);
    toppingIds = new ArrayList<>();
    toppingIds.add(topping.get_id());
    toppingIds.add(topping2.get_id());
    List<Topping> toppings = new ArrayList<>();
    toppings.add(topping);
    toppings.add(topping2);
    pizza = new Pizza(
        "Pepperoni",
        crust,
        toppings
    );
    pizzaRepository.insert(pizza);

    store = new Store("Seattle", "1111 1st Ave S", null);
    customer = new Customer(
        "Daniel English",
        "395-328-3943",
        "1492 1st Ave"
    );
    customerRepository.insert(customer);

    pizzaIdsToAdd = new ArrayList<>();
    pizzaIdsToAdd.add(pizza.get_id());
    pizzasToAdd = new ArrayList<>();
    pizzasToAdd.add(pizza);
    menu = new Menu();
    menu.addPizzas(pizzasToAdd);
    menuRepository.insert(menu);
    store.setMenu(menu);
    storeRepository.insert(store);

    order = new Order(store.get_id());
  }

  @After
  public void tearDown() {
    orderRepository.deleteAll();
  }

  @Test
  public void createOrderSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );
    assertEquals(order.getStoreId(), response.getBody().getStoreId());
  }

  @Test
  public void createOrderStoreIdNotFound() {
    ResponseEntity<Order> response = orderController.createOrder(
        "8"
    );

    String message = "storeId 8 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

  @Test
  public void addToOrderSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );

    ResponseEntity<Order> addToOrderResponse = orderController.addToOrder(
        response.getBody().get_id(),
        pizzaIdsToAdd,
        customer.get_id()
    );

    assertEquals(pizzasToAdd.get(0).get_id(), addToOrderResponse.getBody().getOrderDetails().getPizzas().get(0).get_id());
  }

  @Test
  public void addToOrderOrderIdNotFound() {
    ResponseEntity<Order> addToOrderResponse = orderController.addToOrder(
        "5",
        pizzaIdsToAdd,
        customer.get_id()
    );
    String message = "orderId 5 not found.";
    assertEquals(message, addToOrderResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addToOrderCustomerIdNotFound() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );

    ResponseEntity<Order> addToOrderResponse = orderController.addToOrder(
        response.getBody().get_id(),
        pizzaIdsToAdd,
        "5"
    );
    String message = "customerId 5 not found.";
    assertEquals(message, addToOrderResponse.getHeaders().getFirst("message"));
  }
  @Test
  public void addToOrderStoreIdNotFound() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );
    storeRepository.deleteAll();
    ResponseEntity<Order> addToOrderResponse = orderController.addToOrder(
        response.getBody().get_id(),
        pizzaIdsToAdd,
        customer.get_id()
    );
    String message = "storeId " + order.getStoreId() + " not found.";
    assertEquals(message, addToOrderResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addToOrderPizzaNotOnMenu() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );

    Menu blankMenu = new Menu();
    store.setMenu(blankMenu);
    storeRepository.save(store);

    ResponseEntity<Order> addToOrderResponse = orderController.addToOrder(
        response.getBody().get_id(),
        pizzaIdsToAdd,
        customer.get_id()
    );

    String message = "pizzaId " + pizza.get_id() + " is not on the menu of the store associated with this order!";
    assertEquals(message, addToOrderResponse.getHeaders().getFirst("message"));
  }


}
