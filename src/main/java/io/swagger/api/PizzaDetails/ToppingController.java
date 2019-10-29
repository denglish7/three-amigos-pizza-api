package io.swagger.api.PizzaDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Topping;
import java.util.List;
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
@RequestMapping(path="/topping")
public class ToppingController {

  @Autowired
  private ToppingRepository repository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Toppings in the system.", response = Topping.class, responseContainer = "List", tags = {"developers",})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "all toppings", response = Topping.class, responseContainer = "List")})
  public List getAllToppings() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a Topping", tags={ "admins", })
  public Topping createTopping(@ApiParam("Topping information") @Valid @RequestBody Topping topping) {
    return repository.save(topping);
  }

  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a topping by name", nickname = "searchTopping", response = Topping.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Topping.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  public ResponseEntity searchTopping(@ApiParam("Name of topping to get. Cannot be empty.") @PathVariable("name") String name) {
    Topping topping = repository.findByName(name);
    if (topping == null) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    } else {
      return ResponseEntity.ok(topping);
    }
  }

}
