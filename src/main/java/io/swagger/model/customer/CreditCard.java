package io.swagger.model.customer;

public class CreditCard {
  private String cardNumber;
  private Integer expirationMonth;
  private Integer expirationYear;
  private String cvv;

  public CreditCard(String cardNumber, Integer expirationMonth, Integer expirationYear, String cvv) {
    this.cardNumber = cardNumber;
    this.expirationMonth = expirationMonth;
    this.expirationYear = expirationYear;
    this.cvv = cvv;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public Integer getExpirationMonth() {
    return expirationMonth;
  }

  public Integer getExpirationYear() {
    return expirationYear;
  }

  public String getCvv() {
    return cvv;
  }
}
