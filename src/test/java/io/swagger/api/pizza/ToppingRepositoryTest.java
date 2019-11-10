package io.swagger.api.pizza;

import io.swagger.repositories.ToppingRepository;
import io.swagger.model.pizza.Topping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class ToppingRepositoryTest {
  @Autowired
  private ToppingRepository toppingRepository;

  @Before
  public void setUp() {
    toppingRepository.deleteAll();
  }

  @After
  public void tearDown() {
    toppingRepository.deleteAll();
  }

  @Test
  public void addToppingToRepositoryTest() throws Exception {
    assertEquals(0, toppingRepository.count());
    Topping topping = new Topping(
        "mushrooms",
        .50
    );
    toppingRepository.insert(topping);
    assertEquals(1, toppingRepository.count());
  }
}
