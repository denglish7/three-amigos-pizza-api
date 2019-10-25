package io.swagger.model.StoreDetails;

public class DeliveryDriver {
  String driverName;
  Integer driverPhone;

  public DeliveryDriver(String driverName, Integer driverPhone) {
    this.driverName = driverName;
    this.driverPhone = driverPhone;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public Integer getDriverPhone() {
    return driverPhone;
  }

  public void setDriverPhone(Integer driverPhone) {
    this.driverPhone = driverPhone;
  }
}
