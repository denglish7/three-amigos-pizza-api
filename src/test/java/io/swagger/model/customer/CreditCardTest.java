package io.swagger.model.customer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreditCardTest {
  private CreditCard creditCard;

  @Before
  public void setUp() throws Exception {
    String CARDNUM = "1234567891234567";
    Integer EXPMONTH = 12;
    Integer EXPYEAR = 19;
    String CVV = "888";
    creditCard = new CreditCard(CARDNUM, EXPMONTH, EXPYEAR, CVV);
  }

  @Test
  public void getCardNumber() {
    assertTrue(creditCard.getCardNumber().matches("[0-9]+")
        && creditCard.getCardNumber().length() == 16);
  }

  @Test
  public void getExpirationMonth() {
    assertTrue(creditCard.getExpirationMonth() <= 12);
  }

  @Test
  public void getExpirationYear() {
    assertTrue(creditCard.getExpirationYear() <= 19);
  }

  @Test
  public void getCvv() {
    assertTrue(creditCard.getCvv().matches("[0-9]+")
        && creditCard.getCvv().length() == 3);
  }
}