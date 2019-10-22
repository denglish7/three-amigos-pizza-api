package io.swagger.model.classes.UserDetails;

public class Administrator extends User {
  String adminName;
  Email adminEmail;


  public Administrator(String userID, String password, String adminName, Email adminEmail) {
    super(userID, password);
    adminName = this.adminName;
    adminEmail = this.adminEmail;
  }

  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }

  public Email getAdminEmail() {
    return adminEmail;
  }

  public void setAdminEmail(Email adminEmail) {
    this.adminEmail = adminEmail;
  }

  public Boolean updateCatalogue() {
    return true;
  }
}
