package io.swagger.api.pizza;

import io.swagger.repositories.CrustRepository;
import io.swagger.model.pizza.Crust;

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
public class CrustRepositoryTest {
  @Autowired
  private CrustRepository crustRepository;

  @Before
  public void setUp() {
    crustRepository.deleteAll();
  }

  @After
  public void tearDown() {
    crustRepository.deleteAll();
  }

  @Test
  public void addCrustToRepositoryTest() throws Exception {
    assertEquals(0, crustRepository.count());
    Crust crust = new Crust(4.50, true, "thin crust");
    crustRepository.insert(crust);
    assertEquals(1, crustRepository.count());
  }
}
