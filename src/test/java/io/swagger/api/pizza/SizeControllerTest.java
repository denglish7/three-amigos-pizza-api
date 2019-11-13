package io.swagger.api.pizza;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.model.pizza.Size;
import io.swagger.repositories.SizeRepository;
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
public class SizeControllerTest {

  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private SizeController sizeController;

  @Before
  public void setUp() {
    sizeRepository.deleteAll();
  }

  @After
  public void tearDown() {
    sizeRepository.deleteAll();
  }


  @Test
  public void getAllSizesNone() {
    assertTrue(sizeController.getAllSizes().getBody().isEmpty());
  }

  @Test
  public void saveSize() {
    Size newSize = new Size("large", 5.0);
    ResponseEntity response = sizeController.saveSize(newSize);
    assertEquals(newSize, response.getBody());
  }

  @Test
  public void findById() {
    String NAME = "small";
    Double PRICE = 3.5;
    Size newSize = new Size(NAME, PRICE);
    ResponseEntity<Size> saveResponse = sizeController.saveSize(newSize);
    String id = saveResponse.getBody().get_id();
    ResponseEntity<Size> response = sizeController.findById(id);
    assertEquals(NAME, response.getBody().getName());
    assertEquals(PRICE, response.getBody().getPriceMultiplier());
  }
}
