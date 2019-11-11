package io.swagger.api.pizza;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.pizza.Crust;
import io.swagger.repositories.CrustRepository;
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
public class CrustControllerTest {

  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private CrustController crustController;

  @Before
  public void setUp() {
    crustRepository.deleteAll();
  }

  @After
  public void tearDown() {
    crustRepository.deleteAll();
  }

  @Test
  public void getAllCrustsNone() {
    assertTrue(crustController.getAllCrusts().getBody().isEmpty());
  }

  @Test
  public void getAllCrustsOne() {
    Crust newCrust = new Crust(
        4.50,
        false,
        "thin crust"
    );
    crustController.saveCrust(newCrust);
    assertTrue(crustController.getAllCrusts().getBody().size() == 1);
  }
  @Test
  public void saveCrust() {
    Crust newCrust = new Crust(4.50, false, "cheese-stuffed");
    ResponseEntity response = crustController.saveCrust(newCrust);
    assertEquals(newCrust, response.getBody());
  }

  @Test
  public void findById() {
    Double PRICE = 4.50;
    String NAME = "cheese-stuffed";
    Crust newCrust = new Crust(PRICE, false, NAME);
    ResponseEntity<Crust> saveResponse = crustController.saveCrust(newCrust);
    String id = saveResponse.getBody().get_id();
    ResponseEntity<Crust> response = crustController.findById(id);
    assertEquals(PRICE, response.getBody().getPrice());
    assertEquals(false, response.getBody().getGlutenFree());
    assertEquals(NAME, response.getBody().getName());
  }

  @Test
  public void findByIdCrustNotFound() {
    String id = "5";
    ResponseEntity<Crust> response = crustController.findById(id);
    String message = "crustId 5 not found.";
    assertEquals(message, response.getHeaders().getFirst("message"));
  }
}
