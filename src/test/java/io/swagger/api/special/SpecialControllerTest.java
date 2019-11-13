package io.swagger.api.special;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.api.store.SpecialController;
import io.swagger.model.pizza.Size;
import io.swagger.model.specials.Special;
import io.swagger.repositories.SizeRepository;
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

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class SpecialControllerTest {


  @Autowired
  private SpecialController specialController;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private SizeRepository sizeRepository;

  private Size sizeLarge;

  @Before
  public void setUp() throws Exception {
    sizeLarge = new Size("Large", 10.2);
    sizeRepository.insert(sizeLarge);
    specialRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    specialRepository.deleteAll();
    sizeRepository.deleteAll();
  }

  @Test
  public void getAllSpecialsNone() {
    assertTrue(specialController.getAllSpecials().getBody().isEmpty());
  }

  @Test
  public void createSpecialSuccess() {
    ResponseEntity<Special> response = specialController
        .createSpecial("2 Large half off", 0.5, 2, sizeLarge.get_id());
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals(1, specialController.getAllSpecials().getBody().size());
  }

  @Test
  public void createSpecialBadSize() {
    String BAD_SIZE_ID = "123";
    ResponseEntity<Special> response = specialController
        .createSpecial("2 Large half off", 0.5, 2, BAD_SIZE_ID);
    assertTrue(response.getStatusCode().is4xxClientError());
  }

  @Test
  public void createSpecialPriceRatioTooLow() {
    Double ratioTooLow = SpecialController.getMinimumPriceRatio() - 0.1;
    ResponseEntity<Special> response = specialController
        .createSpecial("2 Large half off", ratioTooLow, 2, sizeLarge.get_id());
    assertTrue(response.getStatusCode().is4xxClientError());
    assertEquals(SpecialController.getPriceRatioOutOfBoundsMessage(), response.getHeaders().getFirst("message"));
  }

  @Test
  public void createSpecialPriceRatioTooHigh() {
    Double ratioTooHigh = SpecialController.getMaximumPriceRatio() + 0.1;
    ResponseEntity<Special> response = specialController
        .createSpecial("2 Large half off", ratioTooHigh, 2, sizeLarge.get_id());
    assertTrue(response.getStatusCode().is4xxClientError());
    assertEquals(SpecialController.getPriceRatioOutOfBoundsMessage(), response.getHeaders().getFirst("message"));
  }

  @Test
  public void createSpecialTooFewPizzas() {
    Integer tooFewPizzas = SpecialController.getMinimumNumberRequiredPizzas() - 1;
    ResponseEntity<Special> response = specialController
        .createSpecial("2 Large half off", 0.5, tooFewPizzas, sizeLarge.get_id());
    assertTrue(response.getStatusCode().is4xxClientError());
    assertEquals(SpecialController.getTooFewRequiredPizzasMessage(), response.getHeaders().getFirst("message"));
  }
}