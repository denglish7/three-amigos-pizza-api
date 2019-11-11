package io.swagger.api.pizza;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.pizza.Topping;
import io.swagger.repositories.ToppingRepository;
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
public class ToppingControllerTest {

  @Autowired
  private ToppingRepository toppingRepository;
  @Autowired
  private ToppingController toppingController;

  @Before
  public void setUp() {
    toppingRepository.deleteAll();
  }

  @After
  public void tearDown() {
    toppingRepository.deleteAll();
  }

  @Test
  public void getAllToppingsNone() {
    assertTrue(toppingController.getAllToppings().getBody().isEmpty());
  }

  @Test
  public void getAllToppingsOne() {
    Topping newTopping = new Topping(
        "mushrooms",
        .50
    );
    toppingController.saveTopping(newTopping);
    assertTrue(toppingController.getAllToppings().getBody().size() == 1);
  }

  @Test
  public void saveTopping() {
    Topping newTopping = new Topping("mushrooms", .50);
    ResponseEntity response = toppingController.saveTopping(newTopping);
    assertEquals(newTopping, response.getBody());
  }

  @Test
  public void findById() {
    String NAME = "cheese-stuffed";
    Double PRICEPERUNIT = 4.50;
    Topping newTopping = new Topping(NAME, PRICEPERUNIT);
    ResponseEntity<Topping> saveResponse = toppingController.saveTopping(newTopping);
    String id = saveResponse.getBody().get_id();
    ResponseEntity<Topping> response = toppingController.findById(id);
    assertEquals(NAME, response.getBody().getName());
    assertEquals(PRICEPERUNIT, response.getBody().getPricePerUnit());
  }

  @Test
  public void findByIdToppingNotFound() {
    String id = "5";
    ResponseEntity<Topping> response = toppingController.findById(id);
    String message = "toppingId 5 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }
}
