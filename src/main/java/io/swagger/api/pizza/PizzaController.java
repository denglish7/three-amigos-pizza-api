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
@RequestMapping("store/menu/pizza")
public class PizzaController {

  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {
      "pizza/za",})
  public ResponseEntity<List<Pizza>> getAllPizzas() {
    return ResponseEntity.ok(pizzaRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza", response = Pizza.class, tags = {"pizza.zaza",})
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
      } else {toppings.add(topping.get());}
    }
    Pizza newPizza = new Pizza(pizzaName, crust.get(), toppings);
    return ResponseEntity.ok(pizzaRepository.save(newPizza));
  }

//  @RequestMapping(path = "/", method = RequestMethod.POST)
//  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
//  public Pizza createPizza(@ApiParam("Pizza information") @Valid @RequestBody Pizza pizza) {
//    return null;
//  }

//  @RequestMapping(path = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
//  @ApiOperation(value = "Searches for pizzas by name", response = Pizza.class, responseContainer = "List", tags = {
//      "developers",})
//  public List<Pizza> searchPizzasByName(
//      @ApiParam("Name of pizza to get.") @PathVariable("name") String name) {
//    Query query = new Query(Criteria.where("name").all(name));
//    return null;
//    // return mongoTemplate.find(query, Pizza.class);
//  }
//
//  @RequestMapping(path = "/id={_id}", method = RequestMethod.GET, produces = {"application/json"})
//  @ApiOperation(value = "Searches for pizza by _id", response = Pizza.class, tags = {"developers",})
//  public ResponseEntity searchPizzasById(
//      @ApiParam("_id of pizza to get.") @PathVariable("_id") String _id) {
//    return null;
//    // Pizza pizza = mongoTemplate.findById(_id, Pizza.class);
//    // if (pizza == null) {
//    //   return new ResponseEntity(HttpStatus.NOT_FOUND);
//    // } else {
//    //   return ResponseEntity.ok(pizza);
//    // }
//  }
//
//  @RequestMapping(path = "/id={_id}", method = RequestMethod.PUT)
//  @ApiOperation(value = "Changes the size of a pizza", tags = {"admins",})
//  public ResponseEntity updatePizzaSizeById(@PathVariable("_id") String _id,
//      @RequestParam(value = "sizeName") String sizeName) {
//    return null;
//    // Pizza pizza = mongoTemplate.findById(_id, Pizza.class);
//    // Size size = mongoTemplate.findById(sizeName, Size.class);
//    // if (pizza == null || size == null) {
//    //   return new ResponseEntity(HttpStatus.NOT_FOUND);
//    // } else {
//    //   pizza.setSize(size);
//    //   return ResponseEntity.ok(mongoTemplate.save(pizza));
//    // }
//  }
}
