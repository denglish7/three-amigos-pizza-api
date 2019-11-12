package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Topping;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.ToppingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/pizza")
public class PizzaController {

  private static final Double PIZZA_BASE_PRICE = 0.0;

  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Pizza>> getAllPizzas() {
    return ResponseEntity.ok(pizzaRepository.findAll());
  }

  // TODO: Add Size as optional parameter
  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza and adds it to the system.", response = Pizza.class, tags = {
      "pizza",})
  public ResponseEntity<Pizza> createPizza(
      @ApiParam("Name for new pizza") @RequestParam(value = "pizzaName") String pizzaName,
      @ApiParam("Crust ID for new pizza") @RequestParam(value = "crustId") String crustId,
      @ApiParam("Topping ID's for new pizza") @RequestParam(value = "toppingIds") List<String> toppingIds) {
    Optional<Crust> crust = crustRepository.findById(crustId);
    if (!crust.isPresent()) {
      return ResponseEntity.notFound().header("message", "crustId " + crustId + " not found.")
          .build();
    }
    List<Topping> toppings = new ArrayList<>();
    for (String toppingId : toppingIds) {
      Optional<Topping> topping = toppingRepository.findById(toppingId);
      if (!topping.isPresent()) {
        return ResponseEntity.notFound().header("message", "toppingId " + toppingId + " not found.")
            .build();
      } else {
        toppings.add(topping.get());
      }
    }
    Pizza newPizza = new Pizza(pizzaName, crust.get(), toppings);
    newPizza.setPrice(this.calculatePriceOfPizza(newPizza));
    return ResponseEntity.ok(pizzaRepository.save(newPizza));
  }

  /**
   * Returns the price of a pizza.  Can we move this to the Pizza model?
   * @param pizza pizza to calculate price of.
   * @return Double, null if pizza doesn't have a size or crust.
   */
  public Double calculatePriceOfPizza(Pizza pizza) {
    Double price = PIZZA_BASE_PRICE;
    if (pizza.getSize() != null) { price += pizza.getSize().getBasePrice(); }
    if (pizza.getCrust() != null) { price += pizza.getCrust().getPrice(); }
    if (pizza.getToppings() != null) {
      for (Topping topping : pizza.getToppings()) {
        price += topping.getPricePerUnit();
      }
    }
    return price;
  }
}
