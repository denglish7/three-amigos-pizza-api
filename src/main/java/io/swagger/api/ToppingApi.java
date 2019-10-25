package io.swagger.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.InventoryItem;
import io.swagger.model.classes.PizzaDetails.Topping;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-27T20:56:20.347Z[GMT]")
public interface ToppingApi {

  @ApiOperation(value = "searches toppings", nickname = "searchToppings", notes = "By passing in the appropriate options, you can search for available toppings in the system ", response = Topping.class, responseContainer = "List", tags={ "developers", })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Topping.class, responseContainer = "List"),
      @ApiResponse(code = 400, message = "bad input parameter") })
  @RequestMapping(value = "/topping",
      produces = { "application/json" },
      method = RequestMethod.GET)
  ResponseEntity<List<Topping>> searchToppings(@ApiParam(value = "pass an optional search string for looking up inventory") @Valid @RequestParam(value = "searchString", required = false) String searchString);

}
