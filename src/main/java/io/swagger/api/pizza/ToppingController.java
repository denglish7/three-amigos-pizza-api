package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.pizza.Topping;
import io.swagger.repositories.ToppingRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(path = "/topping")
public class ToppingController {

  @Autowired
  private ToppingRepository toppingRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Toppings in the system.", response = Topping.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Topping>> getAllToppings() {
    return ResponseEntity.ok(toppingRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a Topping", tags = {"pizza",})
  public ResponseEntity<Topping> saveTopping(
      @ApiParam("Topping information") @Valid @RequestBody Topping topping) {
    return ResponseEntity.ok(toppingRepository.save(topping));
  }

  @RequestMapping(path = "/{toppingId}", produces = {
      "application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a topping by id", response = Topping.class, tags = {"pizza",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Topping.class),
      @ApiResponse(code = 404, message = "item not found")})
  public ResponseEntity<Topping> findById(
      @ApiParam("Id of topping to get.") @PathVariable("toppingId") String toppingId) {
    Optional<Topping> topping = toppingRepository.findById(toppingId);
    if (topping.isPresent()) {
      return ResponseEntity.of(topping);
    }
    return ResponseEntity.notFound().header("message", "toppingId " + toppingId + " not found.")
        .build();
  }

}
