package io.swagger.api.store;

import io.swagger.api.order.OrderController;
import io.swagger.api.pizza.CrustController;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.pizza.SizeController;
import io.swagger.api.pizza.ToppingController;
import io.swagger.model.customer.Customer;
import io.swagger.model.customer.Receipt;
import io.swagger.model.order.Order;
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
  private SpecialRepository specialRepository;
  @Autowired
  private OrderController orderController;

  private Crust crust;
  private Topping topping;
  private Topping topping2;
  private List<Topping> toppings;
  private List<String> toppingIds;
  private Pizza pizzaOnMenu, pizzaNotOnMenu;
  private Store store;
  private Order order;
  private Customer customer;
  private List<String> pizzaIdsToAdd;
  private List<Pizza> pizzasToAdd;
  private Menu menu;
  private Size sizeLarge;
  private Special special;
  private List<Special> specialsToAdd;

  @Before
  public void setUp() throws Exception {
    storeRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    storeRepository.deleteAll();
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
    ResponseEntity <Pizza> newPizza = pizzaController.createPizza(PIZZANAME, crustId, newToppingIds, null);
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

//  @Test
//  public void testCompleteOrder() {
//    String STORENAME = "UptownGurl";
//    String ADDRESS = "123 Jerry St.";
//    ResponseEntity <Store> store = storeController.createStore(STORENAME, ADDRESS, null);
//    String storeId = store.getBody().get_id();
//    ResponseEntity <Order> order = orderController.createOrder(storeId);
//    String orderId = order.getBody().get_id();
//
//    ResponseEntity <Receipt> processOrder = storeController.processNewOrder(storeId, orderId);
//    ResponseEntity <Store> completeOrder = storeController.completeOrder(storeId, orderId);
//    assertEquals(1, completeOrder.getBody().getCompletedOrders().size(), 0);
//  }
}