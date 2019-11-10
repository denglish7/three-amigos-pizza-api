package io.swagger.api.pizza;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Topping;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
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
public class PizzaControllerTest {

  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;

  @Autowired
  private SizeRepository sizeRepository;


  @Autowired
  private PizzaController pizzaController;
  @Autowired
  private CrustController crustController;
  @Autowired
  private ToppingController toppingController;

  private Crust crust;

  private Topping topping;
  private Topping topping2;
  private List<String> toppingIds;

  private Pizza pizza;

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
  }

  @After
  public void tearDown() {
    pizzaRepository.deleteAll();
  }

  @Test
  public void getAllPizzasNone() {
    assertTrue(pizzaController.getAllPizzas().getBody().isEmpty());
  }

  @Test
  public void getAllPizzasOne() {
    pizzaController.createPizza(
        "Pepperoni",
        crust.get_id(),
        toppingIds
    );
    assertTrue(pizzaController.getAllPizzas().getBody().size() == 1);
  }

  @Test
  public void createPizzaSuccess() {
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    assertEquals(pizza.getName(), response.getBody().getName());
    assertEquals(pizza.getCrust().get_id(), response.getBody().getCrust().get_id());
    assertEquals(pizza.getToppings().get(0).get_id(), response.getBody().getToppings().get(0).get_id());
    assertEquals(pizza.getToppings().get(1).get_id(), response.getBody().getToppings().get(1).get_id());
  }
  @Test
  public void createPizzaCrustIdNotFound() {
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        "5",
        toppingIds
    );
    String message = "crustId 5 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

  @Test
  public void createPizzaToppingIdNotFound() {
    toppingIds.add("3");
    ResponseEntity<Pizza> response = pizzaController.createPizza(
        pizza.getName(),
        crust.get_id(),
        toppingIds
    );
    String message = "toppingId 3 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }

}
