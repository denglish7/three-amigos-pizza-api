package io.swagger.api.user;

import static org.junit.Assert.assertEquals;

import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.SpecialRepository;
import io.swagger.repositories.StoreRepository;
import io.swagger.repositories.ToppingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class AdminControllerTest {

  @Autowired
  private AdminController adminController;

  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private StoreRepository storeRepository;

  @Before
  public void setUp() throws Exception {
    crustRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    pizzaRepository.deleteAll();
    specialRepository.deleteAll();
    storeRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    crustRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    pizzaRepository.deleteAll();
    specialRepository.deleteAll();
    storeRepository.deleteAll();
  }

  @Test
  public void resetAll() {
    adminController.setDefaults();
    assertEquals(2, storeRepository.count());
  }
}
