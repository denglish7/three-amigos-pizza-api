package io.swagger.model.store;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.menu.Menu;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stores")
public class Store {

  @ApiModelProperty(hidden = true)
  private String _id;
  @NotNull
  private String name;
  @NotNull
  private String address;
  @NotNull
  private Menu menu;

  public Store(@NotNull String name, @NotNull String address,
      @NotNull Menu menu) {
    this.name = name;
    this.address = address;
    this.menu = menu;
  }

  public String get_id() {
    return _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
