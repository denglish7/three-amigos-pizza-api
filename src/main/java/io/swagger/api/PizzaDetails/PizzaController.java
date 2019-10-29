package io.swagger.api.PizzaDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Pizza;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/pizza")
public class PizzaController {

  @Autowired
  private PizzaRepository repository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> getAllPizzas() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
  public Pizza createPizza(@ApiParam("Pizza information") @Valid @RequestBody Pizza pizza) {
    return repository.save(pizza);
  }

  @RequestMapping(path = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizzas by name", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> searchPizzasByName(@ApiParam("Name of pizza to get.") @PathVariable("name") String name) {
    return repository.findAllByName(name);
  }

  @RequestMapping(path = "/{_id}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizza by _id", response = Pizza.class, tags = {"developers",})
  public ResponseEntity searchPizzasById(@ApiParam("_id of pizza to get.") @PathVariable("_id") String _id) {
    Optional pizza = repository.findById(_id);
    if (pizza.isPresent()) {
      return ResponseEntity.ok(pizza);
    } else {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }
}
