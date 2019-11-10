package io.swagger.api.menu;


import io.swagger.api.pizza.CrustController;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.pizza.SizeController;
import io.swagger.api.pizza.ToppingController;
import io.swagger.api.store.SpecialController;
import io.swagger.model.menu.Menu;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import io.swagger.repositories.*;
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
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class MenuControllerTest {


  @Autowired
  private CrustController crustController;
  @Autowired
  private PizzaController pizzaController;
  @Autowired
  private ToppingController toppingController;
  @Autowired
  private SpecialController specialController;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private SizeController sizeController;
  @Autowired
  private MenuRepository menuRepository;
  @Autowired
  private MenuController menuController;
  @Before

  public void setUp() throws Exception {
    menuRepository.deleteAll();
    specialRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    menuRepository.deleteAll();
  }

  @Test
  public void getAllMenusNone()
  {
    assertTrue(menuController.getAllMenus().getBody().isEmpty());
  }
  @Test
  public void createMenu() {
    ArrayList<Pizza> PIZZAS = new ArrayList<>();
    ArrayList<Special> SPECIALS = new ArrayList<>();
    ResponseEntity <Menu> saveResponse = menuController.createMenu();
    String id = saveResponse.getBody().get_id();
    ResponseEntity<Menu> response = menuController.findById(id);
    assertEquals(PIZZAS, response.getBody().getPizzas());
    assertEquals(SPECIALS, response.getBody().getSpecials());
  }

  @Test
  public void addInvalidPizzaId() {

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();

    String INVALIDPIZZAID = "adsbfuiesd";
    ResponseEntity<Menu> addPizza = menuController.addPizza(INVALIDPIZZAID, testMenuId);
    String message = "pizzaId adsbfuiesd not found.";
    assertEquals(message, addPizza.getHeaders().getFirst("message"));
  }

  @Test
  public void addPizzaToMenu() {
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
    Pizza newPizza = new Pizza(PIZZANAME, newCrust, newToppings);
    ResponseEntity <Pizza> savePizza = pizzaController.createPizza(PIZZANAME, crustId, newToppingIds);
    String pizzaId = savePizza.getBody().get_id();

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();


    ResponseEntity<Menu> addPizza = menuController.addPizza(pizzaId, testMenuId);

    List<Pizza> currentPizzas = addPizza .getBody().getPizzas();
    Pizza currentPizza = currentPizzas.get(0);
    assertEquals(pizzaId, currentPizza.get_id());
  }

  @Test
  public void removePizzaTFromMenu() {
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
    Pizza newPizza = new Pizza(PIZZANAME, newCrust, newToppings);
    ResponseEntity <Pizza> savePizza = pizzaController.createPizza(PIZZANAME, crustId, newToppingIds);
    String pizzaId = savePizza.getBody().get_id();

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();


    ResponseEntity<Menu> addPizza = menuController.addPizza(pizzaId, testMenuId);
    ResponseEntity<Menu> removePizza = menuController.removePizza(pizzaId, testMenuId);

    List<Pizza> currentPizzas = removePizza.getBody().getPizzas();
    assertEquals(0, currentPizzas.size());
  }

  @Test
  public void addInvalidSpecialId() {

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();

    String INVALIDSPECIALID = "adsbfuiesd";
    ResponseEntity<Menu> addSpecial = menuController.addSpecial(INVALIDSPECIALID, testMenuId);
    String message = "specialId adsbfuiesd not found.";
    assertEquals(message, addSpecial.getHeaders().getFirst("message"));
  }
  @Test
  public void addSpecialToMenu() {
    String NAME = "small";
    Double PRICE = 3.5;
    Size newSize = new Size(NAME, PRICE);
    ResponseEntity<Size> saveSize = sizeController.saveSize(newSize);
    String sizeId = saveSize.getBody().get_id();

    ResponseEntity<Special> exampleSpecial = specialController.createSpecial("free", 0.0, 0, sizeId);
    String specialId = exampleSpecial.getBody().get_id();
    Optional<Special> getExpectedSpecial = specialRepository.findById(specialId);
    Special special = getExpectedSpecial.get();

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();

    ResponseEntity<Menu> addSpecial = menuController.addSpecial(specialId, testMenuId);

    List<Special> currentSpecials = addSpecial.getBody().getSpecials();
    Special currentSpecial = currentSpecials.get(0);
    assertEquals(special.get_id(), currentSpecial.get_id());
  }

  @Test
  public void removeSpecialFromMenu() {
    String NAME = "small";
    Double PRICE = 3.5;
    Size newSize = new Size(NAME, PRICE);
    ResponseEntity<Size> saveSize = sizeController.saveSize(newSize);
    String sizeId = saveSize.getBody().get_id();

    ResponseEntity<Special> exampleSpecial = specialController.createSpecial("free", 0.0, 0, sizeId);
    String specialId = exampleSpecial.getBody().get_id();
    Optional<Special> getExpectedSpecial = specialRepository.findById(specialId);
    Special special = getExpectedSpecial.get();

    ResponseEntity <Menu> testMenu = menuController.createMenu();
    String testMenuId = testMenu.getBody().get_id();

    ResponseEntity<Menu> addSpecial = menuController.addSpecial(specialId, testMenuId);
    ResponseEntity<Menu> removeSpecial = menuController.removeSpecial(specialId, testMenuId);
    List<Special> currentSpecials = removeSpecial.getBody().getSpecials();

    assertEquals(0, currentSpecials.size());
  }
}