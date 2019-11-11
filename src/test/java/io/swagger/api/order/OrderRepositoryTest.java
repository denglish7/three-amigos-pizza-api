package io.swagger.api.order;

import io.swagger.model.order.Order;
import io.swagger.repositories.OrderRepository;

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
public class OrderRepositoryTest {
  @Autowired
  private OrderRepository orderRepository;

  @Before
  public void setUp() {
    orderRepository.deleteAll();
  }

  @After
  public void tearDown() {
    orderRepository.deleteAll();
  }

  @Test
  public void addOrderToRepositoryTest() throws Exception {
    assertEquals(0, orderRepository.count());
    Order order = new Order(
        "5"
    );
    orderRepository.insert(order);
    assertEquals(1, orderRepository.count());
  }
}
