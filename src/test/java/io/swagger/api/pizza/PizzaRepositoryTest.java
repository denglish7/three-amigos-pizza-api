package io.swagger.api.pizza;

import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.model.pizza.Pizza;

import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.ToppingRepository;
import java.util.ArrayList;
import java.util.List;
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
public class PizzaRepositoryTest {
  @Autowired
  private PizzaRepository pizzaRepository;

  @Autowired
  private CrustRepository crustRepository;

  @Autowired
  private ToppingRepository toppingRepository;

  @Autowired
  private SizeRepository sizeRepository;

  @Before
  public void setUp() {
    pizzaRepository.deleteAll();
  }

  @After
  public void tearDown() {
    pizzaRepository.deleteAll();
  }

  @Test
  public void addPizzaToRepositoryTest() throws Exception {
    assertEquals(0, pizzaRepository.count());
    Crust crust = new Crust(4.50, false, "thin crust");
    crustRepository.insert(crust);
    Topping topping = new Topping("pepperoni", .10);
    toppingRepository.insert(topping);
    Topping topping2 = new Topping("sausage", .10);
    toppingRepository.insert(topping2);
    List<Topping> toppings = new ArrayList<>();
    toppings.add(topping);
    toppings.add(topping2);
    Size size = new Size("small", 1.00, 20, 30, 15);
    sizeRepository.insert(size);
    Pizza pizza = new Pizza();
    pizza.setName("pepperoni");
    pizza.setCrust(crust);
    pizza.setToppings(toppings);
    pizzaRepository.insert(pizza);
    assertEquals(1, pizzaRepository.count());
  }
}
