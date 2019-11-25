package io.swagger.api.store;

import io.swagger.api.user.CustomerController;
import io.swagger.api.order.ReceiptController;
import io.swagger.api.order.OrderController;
import io.swagger.api.pizza.CrustController;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.pizza.SizeController;
import io.swagger.api.pizza.ToppingController;
import io.swagger.model.customer.Customer;
import io.swagger.model.customer.Receipt;
import io.swagger.model.order.Order;
import io.swagger.model.order.OrderPizza;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Menu;
import io.swagger.model.store.Store;
import io.swagger.repositories.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class StoreControllerTest {
  private Crust crust;
  private Topping topping;
  private Topping topping2;
  private List<String> toppingIds;
  private Pizza pizza;

  @Autowired
  private CrustController crustController;
  @Autowired
  private PizzaController pizzaController;
  @Autowired
  private ToppingController toppingController;
  @Autowired
  private SpecialController specialController;
  @Autowired
  private SizeController sizeController;
  @Autowired
  private StoreController storeController;
  @Autowired
  private StoreRepository storeRepository;
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
  private CustomerRepository customerRepository;
  @Autowired
  private CustomerController customerController;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private OrderController orderController;
  @Autowired
  private ReceiptRepository receiptRepository;
  @Autowired
  private ReceiptController receiptController;

  @Before
  public void setUp() throws Exception {
    receiptRepository.deleteAll();
    storeRepository.deleteAll();
    orderRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    specialRepository.deleteAll();
    crustRepository.deleteAll();
    pizzaRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    receiptRepository.deleteAll();
    storeRepository.deleteAll();
    orderRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    specialRepository.deleteAll();
    crustRepository.deleteAll();
    pizzaRepository.deleteAll();
  }

  @Test
  public void getAllStoresNone() {
    assertTrue(pizzaController.getAllPizzas().getBody().isEmpty());
  }

  @Test
  public void getAllStoresOne() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    storeController.createStore(
        STORENAME,
        ADDRESS,
        null
    );
    TestCase.assertEquals(1, storeController.getAllStores().getBody().size());
  }
  @Test
  public void createStoreFailure() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    Store comparisonStore = new Store(STORENAME, ADDRESS, null);
    ResponseEntity <Store> newStore = storeController.createStore(STORENAME, ADDRESS, null);
    Menu newMenu = new Menu();
    assertEquals(newMenu.getPizzas().size(), newStore.getBody().getMenu().getPizzas().size(), 0);
    assertEquals(newMenu.getSpecials().size(), newStore.getBody().getMenu().getSpecials().size(), 0);
  }

  @Test
  public void createStoreNewMenu() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    Store comparisonStore = new Store(STORENAME, ADDRESS, null);
    ResponseEntity <Store> newStore = storeController.createStore(STORENAME, ADDRESS, null);
    Menu newMenu = new Menu();
    assertEquals(newMenu.getPizzas().size(), newStore.getBody().getMenu().getPizzas().size(), 0);
    assertEquals(newMenu.getSpecials().size(), newStore.getBody().getMenu().getSpecials().size(), 0);
  }

  @Test
  public void createStoreExistingMenuWithSpecials() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> storeOne = storeController.createStore(STORENAME, ADDRESS, null);

    String SPECIALNAME = "2Large41";
    Double PRICERATIO = 0.5;
    Integer NUMPIZZAS = 2;
    String NAME = "large";
    Double PRICE = 15.5;
    Size newSize = new Size(NAME, PRICE);
    ResponseEntity<Size> saveSize = sizeController.saveSize(newSize);
    String sizeId = saveSize.getBody().get_id();
    ResponseEntity <Special> newSpecial = specialController.createSpecial(
        SPECIALNAME,
        PRICERATIO,
        NUMPIZZAS,
        sizeId);
    String newSpecialId = newSpecial.getBody().get_id();
    List<String> storeOneSpecialIds = new ArrayList <>();
    storeOneSpecialIds.add(newSpecialId);

    String storeOneId = storeOne.getBody().get_id();
    ResponseEntity <Menu> storeOneMenuSpecialsUpdated = storeController.addToStoreMenu(storeOneId, null, storeOneSpecialIds);
    ResponseEntity <Store> storeTwo = storeController.createStore(STORENAME, ADDRESS, storeOneId);
    assertEquals(storeOneMenuSpecialsUpdated.getBody().getSpecials().get(0).get_id(), storeTwo.getBody().getMenu().getSpecials().get(0).get_id());
  }

  @Test
  public void removePizzasFromStoreMenu() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> storeOne = storeController.createStore(STORENAME, ADDRESS, null);

    String TOPPINGNAME = "mushroom";
    Double TOPPINGPRICE = 1.5;
    Topping newTopping = new Topping(TOPPINGNAME, TOPPINGPRICE);
    ResponseEntity<Topping> saveTopping = toppingController.saveTopping(newTopping);
    String toppingsId = saveTopping.getBody().get_id();
    List<Topping> newToppings = new ArrayList <>();
    newToppings.add(newTopping);
    List<String> newToppingIds = new ArrayList <>();
    newToppingIds.add(toppingsId);

    String CRUSTNAME = "VEGGIEDOUGH";
    Double CRUSTPRICE = 1.5;
    Boolean GLUTENFREE = true;
    Crust newCrust = new Crust(CRUSTPRICE, GLUTENFREE, CRUSTNAME);
    ResponseEntity<Crust> saveCrust = crustController.saveCrust(newCrust);
    String crustId = saveCrust.getBody().get_id();

    String PIZZANAME = "shroomzaa";
    ResponseEntity <Pizza> newPizza = pizzaController.createPizza(PIZZANAME, crustId, newToppingIds);
    String newPizzaId = newPizza.getBody().get_id();
    String storeOneId = storeOne.getBody().get_id();
    List<String> storeOnePizzaIds = new ArrayList <>();
    storeOnePizzaIds.add(newPizzaId);
    ResponseEntity <Menu> addPizzaToStore = storeController.addToStoreMenu(storeOneId, storeOnePizzaIds, null);
    List<Pizza> currentPizzas = storeController.getMenu(storeOneId).getBody().getPizzas();
    ResponseEntity <Menu> removePizzaFromStore = storeController.removeFromStoreMenu(storeOneId, storeOnePizzaIds, null);
    assertFalse(addPizzaToStore.getBody().getPizzas().size() == removePizzaFromStore.getBody().getPizzas().size());
  }

  @Test
  public void getStoreAddress() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    ResponseEntity <String> getStoreAddr = storeController.getLocation(store.getBody().get_id());
    assertTrue(getStoreAddr.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void updateStoreAddress() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    String NEWADDRESS = "456 Sherry Ave.";
    ResponseEntity <Store> updatedStore = storeController.changeLocation(storeId, NEWADDRESS);
    String currentAddress = updatedStore.getBody().getAddress();
    assertEquals(NEWADDRESS, currentAddress );
  }

  @Test
  public void updateInvalidStoreAddress() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = "ajbsdf";
    String NEWADDRESS = "456 Sherry Ave.";
    ResponseEntity <Store> updatedStore = storeController.changeLocation(storeId, NEWADDRESS);
    assertTrue(updatedStore .getStatusCode().is4xxClientError());
  }
  @Test
  public void processNewOrderSuccess() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity<Customer> customer = customerController.createCustomer(newCustomer);
    String customerId = customer.getBody().get_id();
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 19;
    String CVV = "888";
    ResponseEntity<Customer> addedCustomerCard = customerController.addPaymentDetails(
        customerId,
        CARDNUM,
        EXPMONTH,
        EXPYEAR,
        CVV
    );

    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity <Order> order = orderController.createOrder(storeId);
    String orderId = order.getBody().get_id();
    ResponseEntity <Order> orderWithCustomer = orderController.setCustomerById(orderId, customerId);
    crust = new Crust(4.50, false, "thin crust");
    ResponseEntity<Crust> newCrust = crustController.saveCrust(crust);
    String crustId = newCrust.getBody().get_id();
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
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String NAME = "large";
    Double PRICE = 15.5;
    Size size = new Size(NAME, PRICE);
    ResponseEntity<Size> newSize = sizeController.saveSize(size);
    String sizeId = newSize.getBody().get_id();
    ResponseEntity <OrderPizza> pizzaForOrder = orderController.addCustomPizza(
        orderId,
        pizza.getName(),
        crustId,
        toppingIds,
        sizeId
    );
    String pizzaForOrderId = pizzaForOrder.getBody().get_id();
    ResponseEntity <OrderPizza> orderWithPizza = orderController.addPizzaById(
        orderId,
        pizzaForOrderId,
        sizeId
    );

    ResponseEntity <Receipt> orderReceipt = storeController.processNewOrder(
        storeId,
        orderId
    );
    assertTrue(receiptRepository.count() > 0);

  }

  @Test
  public void completeOrderSuccess() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity<Customer> customer = customerController.createCustomer(newCustomer);
    String customerId = customer.getBody().get_id();
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 19;
    String CVV = "888";
    ResponseEntity<Customer> addedCustomerCard = customerController.addPaymentDetails(
        customerId,
        CARDNUM,
        EXPMONTH,
        EXPYEAR,
        CVV
    );

    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity <Order> order = orderController.createOrder(storeId);
    String orderId = order.getBody().get_id();
    ResponseEntity <Order> orderWithCustomer = orderController.setCustomerById(orderId, customerId);
    crust = new Crust(4.50, false, "thin crust");
    ResponseEntity<Crust> newCrust = crustController.saveCrust(crust);
    String crustId = newCrust.getBody().get_id();
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
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String NAME = "large";
    Double PRICE = 15.5;
    Size size = new Size(NAME, PRICE);
    ResponseEntity<Size> newSize = sizeController.saveSize(size);
    String sizeId = newSize.getBody().get_id();
    ResponseEntity <OrderPizza> pizzaForOrder = orderController.addCustomPizza(
        orderId,
        pizza.getName(),
        crustId,
        toppingIds,
        sizeId
    );
    String pizzaForOrderId = pizzaForOrder.getBody().get_id();
    ResponseEntity <OrderPizza> orderWithPizza = orderController.addPizzaById(
        orderId,
        pizzaForOrderId,
        sizeId
    );

    ResponseEntity <Receipt> orderReceipt = storeController.processNewOrder(
        storeId,
        orderId
    );
    ResponseEntity <Store> storeCompletedOrder = storeController.completeOrder(
        storeId,
        orderId
    );
    assertTrue(storeCompletedOrder.getStatusCode().is2xxSuccessful());
  }

  @Test
  public void completeOrderInvalidStore() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity<Customer> customer = customerController.createCustomer(newCustomer);
    String customerId = customer.getBody().get_id();
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 19;
    String CVV = "888";
    ResponseEntity<Customer> addedCustomerCard = customerController.addPaymentDetails(
        customerId,
        CARDNUM,
        EXPMONTH,
        EXPYEAR,
        CVV
    );

    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity <Order> order = orderController.createOrder(storeId);
    String orderId = order.getBody().get_id();
    ResponseEntity <Order> orderWithCustomer = orderController.setCustomerById(orderId, customerId);
    crust = new Crust(4.50, false, "thin crust");
    ResponseEntity<Crust> newCrust = crustController.saveCrust(crust);
    String crustId = newCrust.getBody().get_id();
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
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String NAME = "large";
    Double PRICE = 15.5;
    Size size = new Size(NAME, PRICE);
    ResponseEntity<Size> newSize = sizeController.saveSize(size);
    String sizeId = newSize.getBody().get_id();
    ResponseEntity <OrderPizza> pizzaForOrder = orderController.addCustomPizza(
        orderId,
        pizza.getName(),
        crustId,
        toppingIds,
        sizeId
    );
    String pizzaForOrderId = pizzaForOrder.getBody().get_id();
    ResponseEntity <OrderPizza> orderWithPizza = orderController.addPizzaById(
        orderId,
        pizzaForOrderId,
        sizeId
    );

    ResponseEntity <Receipt> orderReceipt = storeController.processNewOrder(
        storeId,
        orderId
    );
    String invalideStoreId = "sldjbg";
    ResponseEntity <Store> storeCompletedOrder = storeController.completeOrder(
        invalideStoreId,
        orderId
    );
    assertTrue(storeCompletedOrder.getStatusCode().is4xxClientError());
  }

  @Test
  public void completeOrderInvalidOrder() {
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity<Customer> customer = customerController.createCustomer(newCustomer);
    String customerId = customer.getBody().get_id();
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 19;
    String CVV = "888";
    ResponseEntity<Customer> addedCustomerCard = customerController.addPaymentDetails(
        customerId,
        CARDNUM,
        EXPMONTH,
        EXPYEAR,
        CVV
    );

    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity <Order> order = orderController.createOrder(storeId);
    String orderId = order.getBody().get_id();
    ResponseEntity <Order> orderWithCustomer = orderController.setCustomerById(orderId, customerId);
    crust = new Crust(4.50, false, "thin crust");
    ResponseEntity<Crust> newCrust = crustController.saveCrust(crust);
    String crustId = newCrust.getBody().get_id();
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
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String NAME = "large";
    Double PRICE = 15.5;
    Size size = new Size(NAME, PRICE);
    ResponseEntity<Size> newSize = sizeController.saveSize(size);
    String sizeId = newSize.getBody().get_id();
    ResponseEntity <OrderPizza> pizzaForOrder = orderController.addCustomPizza(
        orderId,
        pizza.getName(),
        crustId,
        toppingIds,
        sizeId
    );
    String pizzaForOrderId = pizzaForOrder.getBody().get_id();
    ResponseEntity <OrderPizza> orderWithPizza = orderController.addPizzaById(
        orderId,
        pizzaForOrderId,
        sizeId
    );

    ResponseEntity <Receipt> orderReceipt = storeController.processNewOrder(
        storeId,
        orderId
    );
    String invalideOrderId = "sldjbg";
    ResponseEntity <Store> storeCompletedOrder = storeController.completeOrder(
        storeId,
        invalideOrderId
    );
    assertTrue(storeCompletedOrder.getStatusCode().is4xxClientError());
  }

  @Test
  public void processNewOrderStoreIdNotFound() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    String BAD_STORE_ID = "53";
    String message = "storeId " + BAD_STORE_ID + " not found.";
    ResponseEntity<Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity<Order> order = orderController.createOrder(
        storeId
    );
    ResponseEntity<Receipt> receipt = storeController.processNewOrder(
        BAD_STORE_ID,
        order.getBody().get_id()
    );
    assertTrue(receipt.getStatusCode().is4xxClientError());
    assertEquals(message,
        receipt.getHeaders().getFirst(OrderController.getMessageHeaderName()));
  }

  @Test
  public void processNewOrderOrderIdNotFound() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    String BAD_ORDER_ID = "53";
    String message = "orderId " + BAD_ORDER_ID + " not found.";
    ResponseEntity<Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity<Receipt> receipt = storeController.processNewOrder(
        storeId,
        BAD_ORDER_ID
    );
    assertTrue(receipt.getStatusCode().is4xxClientError());
    assertEquals(message,
        receipt.getHeaders().getFirst(OrderController.getMessageHeaderName()));
  }

  @Test
  public void processNewOrderEmptyCart() {
    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity<Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity<Order> order = orderController.createOrder(
        storeId
    );
    String message = "orderId " + order.getBody().get_id() + " has no pizza's in cart.";
    ResponseEntity<Receipt> receipt = storeController.processNewOrder(
        storeId,
        order.getBody().get_id()
    );
    assertTrue(receipt.getStatusCode().is4xxClientError());
    assertEquals(message,
        receipt.getHeaders().getFirst(OrderController.getMessageHeaderName()));
  }


  @Test
  public void processNewOrderInvalidCreditCard() {
    String message = "Invalid card number entered.";
    Customer newCustomer = new Customer(
        "Daniel English",
        "492-372-3714",
        "4256 2nd Ave Seattle, WA 98362"
    );
    ResponseEntity<Customer> customer = customerController.createCustomer(newCustomer);
    String customerId = customer.getBody().get_id();
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 4;
    Integer EXPYEAR = 19;
    String CVV = "888";
    ResponseEntity<Customer> addedCustomerCard = customerController.addPaymentDetails(
        customerId,
        CARDNUM,
        EXPMONTH,
        EXPYEAR,
        CVV
    );

    String STORENAME = "UptownGurl";
    String ADDRESS = "123 Jerry St.";
    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
    String storeId = store.getBody().get_id();
    ResponseEntity <Order> order = orderController.createOrder(storeId);
    String orderId = order.getBody().get_id();
    ResponseEntity <Order> orderWithCustomer = orderController.setCustomerById(orderId, customerId);
    crust = new Crust(4.50, false, "thin crust");
    ResponseEntity<Crust> newCrust = crustController.saveCrust(crust);
    String crustId = newCrust.getBody().get_id();
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
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String NAME = "large";
    Double PRICE = 15.5;
    Size size = new Size(NAME, PRICE);
    ResponseEntity<Size> newSize = sizeController.saveSize(size);
    String sizeId = newSize.getBody().get_id();
    ResponseEntity <OrderPizza> pizzaForOrder = orderController.addCustomPizza(
        orderId,
        pizza.getName(),
        crustId,
        toppingIds,
        sizeId
    );
    String pizzaForOrderId = pizzaForOrder.getBody().get_id();
    ResponseEntity <OrderPizza> orderWithPizza = orderController.addPizzaById(
        orderId,
        pizzaForOrderId,
        sizeId
    );

    ResponseEntity <Receipt> orderReceipt = storeController.processNewOrder(
        storeId,
        orderId
    );

    assertTrue(orderReceipt.getStatusCode().is4xxClientError());
    assertEquals(message,
        orderReceipt.getHeaders().getFirst(OrderController.getMessageHeaderName()));
  }
}