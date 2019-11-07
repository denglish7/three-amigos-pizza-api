package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.pizza.Size;
import io.swagger.repositories.SizeRepository;
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
    return repository.save(size);
  }

  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a Size by name", response = Size.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Size.class),
      @ApiResponse(code = 404, message = "size not found")})
  public ResponseEntity searchSizeByName(@ApiParam("Name of size to get.") @PathVariable("name") String name) {
    Size size = repository.findByName(name);
    if (size == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return ResponseEntity.ok(size);
    }
  }
}
