package io.swagger.api.customer;

import io.swagger.model.customer.Receipt;
import io.swagger.repositories.ReceiptRepository;
import junit.framework.TestCase;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class ReceiptControllerTest {

  @Autowired
  private ReceiptRepository receiptRepository;
  @Autowired
  private ReceiptController receiptController;

  @Before
  public void setUp() throws Exception {
    receiptRepository.deleteAll();
  }

  @After
  public void tearDown() throws Exception {
    receiptRepository.deleteAll();
  }

  @Test
  public void testCreateReceipt() {
    String STORENAME = "UptownGurl";
    String CUSTOMERNAME = "DanDanJonah";
    List <String> PIZZASORDERED = new ArrayList <>();
    PIZZASORDERED.add("CHEESE");
    PIZZASORDERED.add("PEPPERONI");
    PIZZASORDERED.add("SAUSAGE-ONION");
    String SPECIALAPPLIED = "241";
    String CARDNUMLASTFOUR = "1234";
    Double PRICEPAID = 45.60;

    ResponseEntity <Receipt> savedReceipt = receiptController.createReceipt(
        STORENAME,
        CUSTOMERNAME,
        "sskjdbf",
        PIZZASORDERED,
        SPECIALAPPLIED,
        CARDNUMLASTFOUR,
        PRICEPAID);
    assertTrue(receiptRepository.count() > 0);
  }

  @Test
  public void testGetAllReceipts() {
    String STORENAME = "UptownGurl";
    String CUSTOMERNAME = "DanDanJonah";
    List <String> PIZZASORDERED = new ArrayList <>();
    PIZZASORDERED.add("CHEESE");
    PIZZASORDERED.add("PEPPERONI");
    PIZZASORDERED.add("SAUSAGE-ONION");
    String SPECIALAPPLIED = "241";
    String CARDNUMLASTFOUR = "1234";
    Double PRICEPAID = 45.60;

    ResponseEntity <Receipt> savedReceipt = receiptController.createReceipt(
        STORENAME,
        CUSTOMERNAME,
        "sskjdbf",
        PIZZASORDERED,
        SPECIALAPPLIED,
        CARDNUMLASTFOUR,
        PRICEPAID);
    TestCase.assertTrue(savedReceipt.getStatusCode().is2xxSuccessful());
  }
}