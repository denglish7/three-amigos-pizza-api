package io.swagger.model.menu;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.specials.Special;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.store.Store;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menus")
public class Menu {

  @ApiModelProperty(hidden = true)
  private String _id;

  @DBRef
  private List<Pizza> pizzas;
  private String storeId;

  public Menu(List<Pizza> pizzas, String storeId) {
    this.pizzas = pizzas;
    this.storeId = storeId;
  }

  public String get_id() {
    return _id;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public List<Pizza> getPizzas() {
    return pizzas;
  }

  public void setPizzas(List<Pizza> pizzas) {
    this.pizzas = pizzas;
  }

  public void addPizzas(List<Pizza> pizzas) {
    this.pizzas.addAll(pizzas);
  }
}
