package io.swagger.api.PizzaDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Size;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of pizza Sizes.")
@RestController
@RequestMapping("/size")
public class SizeController {

  @Autowired
  private SizeRepository repository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Sizes in the system.", response = Size.class, responseContainer = "List", tags = {"developers",})
  public List getAllSizes() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a Size", tags={ "admins", })
  public Size createSize(@ApiParam("Size information") @Valid @RequestBody Size size) {
    repository.save(size);
    return size;
  }

  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a Size by name", nickname = "searchSize", response = Size.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Size.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  public Optional searchSize(@ApiParam("Name of size to get. Cannot be empty.") @PathVariable("name") String name) {
    return repository.findById(name);
  }

}
