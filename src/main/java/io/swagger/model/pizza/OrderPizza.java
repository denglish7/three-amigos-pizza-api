//package io.swagger.model.pizza;
//
//import io.swagger.model.Priceable;
//import java.util.List;
//import javax.validation.constraints.NotNull;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//
//public class OrderPizza extends Pizza implements Priceable {
//
//  @DBRef
//  private List<Size> sizes;
//
//  public OrderPizza(String name,
//      @NotNull Crust crust,
//      @NotNull List<Topping> toppings,
//      List<Size> sizes) {
//    super(name, crust, toppings);
//    this.sizes = sizes;
//  }
//}
