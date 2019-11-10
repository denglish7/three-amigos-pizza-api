package io.swagger.api.store;


import io.swagger.model.pizza.Size;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Store;
import io.swagger.repositories.StoreRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class StoreControllerTest {


  @Autowired
  private StoreController storeController;
  @Autowired
  private StoreRepository storeRepository;

  @Before
  public void setUp() throws Exception {
    storeRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    storeRepository.deleteAll();
  }

  @Test
  public void getAllSpecialsNone()
  {
    assertTrue(storeController.getAllStores().getBody().isEmpty());
  }

}