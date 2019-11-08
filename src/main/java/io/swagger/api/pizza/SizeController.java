package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.pizza.Size;
import io.swagger.repositories.SizeRepository;
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
@RequestMapping("/pizza/size")
public class SizeController {

  @Autowired
  private SizeRepository sizeRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Sizes in the system.", response = Size.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Size>> getAllSizes() {
    return ResponseEntity.ok(sizeRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a Size", tags = {"pizza",})
  public ResponseEntity<Size> saveSize(
      @ApiParam("Size information") @Valid @RequestBody Size size) {
    return ResponseEntity.ok(sizeRepository.save(size));
  }

  @RequestMapping(path = "/{sizeId}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a Size by id", response = Size.class, tags = {"pizza",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Size.class),
      @ApiResponse(code = 404, message = "size not found")})
  public ResponseEntity<Size> findById(
      @ApiParam("Id of size to get.") @PathVariable("sizeId") String sizeId) {
    Optional<Size> size = sizeRepository.findById(sizeId);
    if (size.isPresent()) {
      return ResponseEntity.of(size);
    }
    return ResponseEntity.notFound().header("message", "sizeId " + sizeId + " not found.").build();
  }
}
