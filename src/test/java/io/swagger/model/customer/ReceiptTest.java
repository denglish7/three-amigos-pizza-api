package io.swagger.model.customer;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ReceiptTest {
  private Receipt receipt;

  @Before
  public void setUp() throws Exception {
    String STORENAME = "UptownGurl";
    String CUSTOMERNAME = "DanDanJonah";
    List <String> PIZZASORDERED = new ArrayList <>();
    PIZZASORDERED.add("CHEESE");
    PIZZASORDERED.add("PEPPERONI");
    PIZZASORDERED.add("SAUSAGE-ONION");
    String SPECIALAPPLIED = "241";
    String CARDNUMLASTFOUR = "1234";
    Double PRICEPAID = 45.60;

    receipt = new Receipt(
        STORENAME,
        CUSTOMERNAME,
        "sskjdbf",
        PIZZASORDERED,
        SPECIALAPPLIED,
        CARDNUMLASTFOUR,
        PRICEPAID);
  }


  @Test
  public void testToString() {
  String printedReceipt = this.receipt.toString();
  String now = LocalDateTime.now().toLocalDate().toString();
  String top = "Time of Purchase: " + now + "'";
  assertEquals("" +
          "Receipt #: null'" +
          top +
          "Store: UptownGurl" +
          "'Customer: DanDanJonah" +
          "'Order Id: sskjdbf" +
          "'Pizzas: [CHEESE, PEPPERONI, SAUSAGE-ONION]" +
          "'Promotion Applied: 241" +
          "'Payment Method: Card Ending In 1234" +
          "'Total: $45.6",
      printedReceipt);
  }
}