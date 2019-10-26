package io.swagger.api.PizzaDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Pizza;
import io.swagger.model.PizzaDetails.Sauce;
import io.swagger.model.PizzaDetails.Size;
import io.swagger.model.PizzaDetails.Topping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Pizzas.")
@RestController
@RequestMapping("/pizza")
public class PizzaController {

  @Autowired
  private PizzaRepository repository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private SauceRepository sauceRepository;
  @Autowired
  private ToppingRepository toppingRepository;


  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List getAllPizzas() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
  public Pizza createPizza(@ApiParam("Pizza information") String name, String sizeName,
      String sauceName, List<String> toppingNames, Boolean isGlutenFree) {
    Size size = sizeRepository.findByName(sizeName);
    Sauce sauce = sauceRepository.findByName(sauceName);
    List<Topping> toppings = new ArrayList<>();
    for (String t : toppingNames) {
      toppings.add(toppingRepository.findByName(t));
    }
    Pizza pizza = new Pizza(name, size, sauce, toppings, isGlutenFree);
    repository.save(pizza);
    return pizza;
  }

  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a pizza by name", nickname = "searchPizza", response = Pizza.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Pizza.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  public Optional searchPizza(@ApiParam("Name of pizza to get. Cannot be empty.") @PathVariable("name") String name) {
    return repository.findById(name);
  }

}





//package io.swagger.api.PizzaDetails;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import io.swagger.model.PizzaDetails.Pizza;
//import io.swagger.model.PizzaDetails.Sauce;
//import java.util.List;
//import java.util.Optional;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Pizzas.")
//@RestController
//@RequestMapping("/pizza")
//public class PizzaController {
//
//  @Autowired
//  private PizzaRepository repository;
//
//  @Autowired
//  private SauceRepository sauceRepository;
//
//  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {"developers",})
//  public List getAllPizzas() {
//    return repository.findAll();
//  }
//
//  @RequestMapping(path = "/", method = RequestMethod.POST)
//  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
//  public Pizza createPizza(@ApiParam("Pizza information") @Valid @RequestBody Pizza pizza) {
//    repository.save(pizza);
//    return pizza;
//  }
//
//  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
//  @ApiOperation(value = "Searches for a pizza by name", nickname = "searchPizza", response = Pizza.class, tags = {"developers",})
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = "search results matching criteria", response = Pizza.class),
//      @ApiResponse(code = 400, message = "bad input parameter")})
//  public Optional searchPizza(@ApiParam("Name of pizza to get. Cannot be empty.") @PathVariable("name") String name) {
//    return repository.findById(name);
//  }
//
//}
