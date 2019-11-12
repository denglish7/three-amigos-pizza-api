package io.swagger.api.order;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.customer.Customer;
import io.swagger.model.pizza.Size;
import io.swagger.model.store.Menu;
import io.swagger.model.order.Order;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Topping;
import io.swagger.model.store.Store;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
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
  private Size size;

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
    size = new Size("Large", 10.0);
    sizeRepository.insert(size);
    pizzaIdsToAdd = new ArrayList<>();
    pizzaIdsToAdd.add(pizza.get_id());
    pizzasToAdd = new ArrayList<>();
    pizzasToAdd.add(pizza);
    menu = new Menu();
    menu.addPizzas(pizzasToAdd);
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
    String BAD_ID = "8";
    String message = "storeId " + BAD_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        BAD_ID
    );
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

  @Test
  public void findByIdSuccess() {
    ResponseEntity<Order> createOrderResponse = orderController.createOrder(
        order.getStoreId()
    );
    ResponseEntity<Order> findOrderResponse = orderController
        .findById(createOrderResponse.getBody().get_id());
    assertTrue(findOrderResponse.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void findByIdNotFound() {
    ResponseEntity<Order> createOrderResponse = orderController.createOrder(
        order.getStoreId()
    );
    String BAD_ID = createOrderResponse.getBody().get_id() + "1";
    ResponseEntity<Order> findOrderResponse = orderController.findById(BAD_ID);
    assertTrue(findOrderResponse.getStatusCode().is4xxClientError());
  }

  @Test
  public void setCustomerByIdSuccess() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );

    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        response.getBody().get_id(), customer.get_id()
    );
    assertEquals(customer.get_id(), setCustomerResponse.getBody().getCustomer().get_id());
  }

  @Test
  public void setCustomerByIdOrderNotFound() {
    String BAD_ID = "5";
    String message = "orderId " + BAD_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );
    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        BAD_ID, customer.get_id()
    );
    assertTrue(setCustomerResponse.getStatusCode().is4xxClientError());
    assertEquals(message, setCustomerResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void setCustomerByIdCustomerNotFound() {
    String BAD_ID = "5";
    String message = "customerId " + BAD_ID + " not found.";
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );
    ResponseEntity<Order> setCustomerResponse = orderController.setCustomerById(
        response.getBody().get_id(), BAD_ID
    );
    assertTrue(setCustomerResponse.getStatusCode().is4xxClientError());
    assertEquals(message, setCustomerResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addCustomPizzaSuccess() {
  }

  @Test
  public void addCustomPizzaOrderNotFound() {
  }

  @Test
  public void addCustomPizzaInvalidPizza() {
  }

  @Test
  public void addPizzaByIdSuccess() {
  }

  @Test
  public void addPizzaByIdOrderNotFound() {
  }

  @Test
  public void addPizzaByIdMenuNotFound() {
  }

  @Test
  public void addPizzaByIdPizzaNotOnMenu() {
    ResponseEntity<Order> response = orderController.createOrder(
        order.getStoreId()
    );

    Menu blankMenu = new Menu();
    store.setMenu(blankMenu);
    storeRepository.save(store);

    ResponseEntity<Order> addPizzaResponse = orderController.addPizzaById(
        response.getBody().get_id(),
        pizza.get_id(),
        size.get_id()
    );

    String message = "pizzaId " + pizza.get_id()
        + " not found in menu associated with this order.";
    assertEquals(message, addPizzaResponse.getHeaders().getFirst("message"));
  }

  @Test
  public void addPizzaByIdSizeNotFound() {
  }

  @Test
  public void removePizzaByIdSuccess() {
  }

  @Test
  public void removePizzaByIdOrderNotFound() {
  }

  @Test
  public void removePizzaByIdPizzaNotOnOrder() {
  }
}