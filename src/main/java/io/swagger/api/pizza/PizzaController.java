package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.api.exceptions.InvalidPizzaException;
import io.swagger.model.order.OrderPizza;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
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

  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;
  @Autowired
  private SizeRepository sizeRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Pizza>> getAllPizzas() {
    return ResponseEntity.ok(pizzaRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza and adds it to the system.", response = Pizza.class, tags = {
      "pizza",})
  public ResponseEntity<Pizza> createPizza(
      @ApiParam("Name for new pizza") @RequestParam(value = "pizzaName") String pizzaName,
      @ApiParam("Crust ID for new pizza") @RequestParam(value = "crustId") String crustId,
      @ApiParam("Topping ID's for new pizza") @RequestParam(value = "toppingIds") List<String> toppingIds) {
    try {
      Pizza newPizza = validatePizza(pizzaName, crustId, toppingIds);
      return ResponseEntity.ok(pizzaRepository.save(newPizza));
    } catch (InvalidPizzaException e) {
      return ResponseEntity.badRequest().header("message", e.getMessage()).build();
    }
  }

  /**
   * Verifies input parameters and returns a pizza object.
   *
   * @param pizzaName String name for pizza
   * @param crustId String crustId for crust of pizza
   * @param toppingIds List of toppingIds for toppings of pizza
   * @return Pizza object
   * @throws InvalidPizzaException if any of the input Id's are not found in respective repository.
   */
  public Pizza validatePizza(String pizzaName, String crustId, List<String> toppingIds)
      throws InvalidPizzaException {
    Optional<Crust> crust = crustRepository.findById(crustId);
    if (!crust.isPresent()) {
      throw new InvalidPizzaException("crustId " + crustId + " not found.");
    }
    List<Topping> toppings = new ArrayList<>();
    for (String toppingId : toppingIds) {
      Optional<Topping> topping = toppingRepository.findById(toppingId);
      if (!topping.isPresent()) {
        throw new InvalidPizzaException("toppingId " + toppingId + " not found.");
      } else {
        toppings.add(topping.get());
      }
    }
    return new Pizza(pizzaName, crust.get(), toppings);
  }

  public OrderPizza validateOrderPizza(String pizzaName, String crustId, List<String> toppingIds,
      String sizeId) throws InvalidPizzaException {
    Pizza pizza = validatePizza(pizzaName, crustId, toppingIds);
    Optional<Size> size = sizeRepository.findById(sizeId);
    if (!size.isPresent()) {
      throw new InvalidPizzaException("sizeId " + sizeId + " not found.");
    }
    return new OrderPizza(pizza, size.get());
  }
}
