package io.swagger.api.customer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import io.swagger.api.pizza.SizeController;
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
public class PizzaControllerTest {

  @Autowired
  private SizeRepository sizeRepository;

  @Autowired
  private SizeController sizeController;


}
