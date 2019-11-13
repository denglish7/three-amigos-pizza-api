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
    String customerName = "DanDanJonah";
    List <String> pizzasOrdered = new ArrayList <>();
    pizzasOrdered.add("CHEESE");
    pizzasOrdered.add("PEPPERONI");
    pizzasOrdered.add("SAUSAGE-ONION");
    Double PRICEPAID = 45.60;

    receipt = new Receipt(
        customerName,
        "sskjdbf",
        pizzasOrdered,
        PRICEPAID
    );
  }

  @Test
  public void testToString() {
    String printedReceipt = receipt.toString();
    assertEquals(
        "Customer: DanDanJonah'Order Id: sskjdbf'Pizzas: [CHEESE, PEPPERONI, SAUSAGE-ONION]'Total: $45.6",
        printedReceipt);
  }
}