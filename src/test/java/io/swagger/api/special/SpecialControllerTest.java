package io.swagger.api.special;


import io.swagger.api.menu.MenuController;
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
import io.swagger.repositories.MenuRepository;
import io.swagger.repositories.SpecialRepository;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class SpecialControllerTest {


  @Autowired
  private SpecialController specialController;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private SizeController sizeController;
  @Before

  public void setUp() throws Exception {
    specialRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    specialRepository.deleteAll();
  }

  @Test
  public void getAllSpecialsNone()
  {
    assertTrue(specialController.getAllSpecials().getBody().isEmpty());
  }
  @Test
  public void createSpecialSizeMinimum() {
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

    String specialId = newSpecial.getBody().get_id();
    Optional<Special> checkNewSpecial = specialRepository.findById(specialId);
    Special existingSpecial = checkNewSpecial.get();

    List<Special> currentSpecials = specialController.getAllSpecials().getBody();
    String testNewSpecialId = currentSpecials.get(0).get_id();

    assertEquals(specialId, testNewSpecialId);
  }

  @Test
  public void createSpecialNoSizeMinimum() {
    String SPECIALNAME = "2Large41";
    Double PRICERATIO = 0.5;

    String NAME = "large";
    Double PRICE = 15.5;
    Size newSize = new Size(NAME, PRICE);
    ResponseEntity<Size> saveSize = sizeController.saveSize(newSize);
    String sizeId = saveSize.getBody().get_id();

    ResponseEntity <Special> newSpecial = specialController.createSpecial(
        SPECIALNAME,
        PRICERATIO,
        null,
        sizeId);

    String specialId = newSpecial.getBody().get_id();
    Optional<Special> checkNewSpecial = specialRepository.findById(specialId);
    Special existingSpecial = checkNewSpecial.get();

    List<Special> currentSpecials = specialController.getAllSpecials().getBody();
    String testNewSpecialId = currentSpecials.get(0).get_id();

    assertEquals(specialId, testNewSpecialId);
  }

  @Test
  public void createSpecialFailure() {

    String SPECIALNAME = "2Large41";
    Double PRICERATIO = 0.5;
    String INVALIDSIZEID = "sjebfdlksa";

    ResponseEntity <Special> newSpecial = specialController.createSpecial(
        SPECIALNAME,
        PRICERATIO,
        null,
        INVALIDSIZEID);
    String message = "sizeId sjebfdlksa not found.";
    assertEquals(message, newSpecial.getHeaders().getFirst("message"));
  }
}