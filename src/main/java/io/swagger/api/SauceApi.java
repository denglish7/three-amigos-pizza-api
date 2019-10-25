package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Sauce;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "sauce")
public interface SauceApi {

  @ApiOperation(value = "adds a sauce", tags={ "admins", })
  @RequestMapping(value = "/",
      method = RequestMethod.POST)
  ResponseEntity<Void> addSauce(@Valid @RequestBody Sauce sauce);

  @ApiOperation(value = "searches for a sauce", nickname = "searchSauce", response = Sauce.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Sauce.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  @RequestMapping(value = "/{name}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<Sauce> searchSauce(@PathVariable("name") String name);
}

