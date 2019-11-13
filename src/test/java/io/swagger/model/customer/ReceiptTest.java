package io.swagger.model.customer;

import org.junit.Before;
import org.junit.Test;

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
    Double PRICEPAID = 45.60;

    receipt = new Receipt(
        STORENAME,
        CUSTOMERNAME,
        "sskjdbf",
        PIZZASORDERED,
        SPECIALAPPLIED,
        PRICEPAID
    );
  }

  @Test
  public void testToString() {
    String printedReceipt = receipt.toString();
    assertEquals(
        "Store: UptownGurl" +
            "'Customer: DanDanJonah" +
            "'Order Id: sskjdbf" +
            "'Pizzas: [CHEESE, PEPPERONI, SAUSAGE-ONION]" +
            "'Promotion Applied: 241" +
            "'Total: $45.6",
        printedReceipt);
  }
}