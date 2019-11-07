package io.swagger.api.pizza;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.pizza.Crust;
import io.swagger.repositories.CrustRepository;
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
@RequestMapping(path = "/crust")
public class CrustController {

  @Autowired
  private CrustRepository crustRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all crusts in the system.", response = Crust.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Crust>> getAllCrusts() {
    return ResponseEntity.ok(crustRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a crust", tags = {"pizza",})
  public ResponseEntity<Crust> saveCrust(
      @ApiParam("crust information") @Valid @RequestBody Crust crust) {
    return ResponseEntity.ok(crustRepository.save(crust));
  }

  @RequestMapping(path = "/{crustId}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a crust by id", response = Crust.class, tags = {
      "pizza",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Crust.class),
      @ApiResponse(code = 404, message = "crust not found")})
  public ResponseEntity<Crust> findById(
      @ApiParam("Id of crust to get.") @PathVariable("crustId") String crustId) {
    Optional<Crust> crust = crustRepository.findById(crustId);
    if (crust.isPresent()) {
      return ResponseEntity.of(crust);
    }
    return ResponseEntity.notFound().header("message", "crustId " + crustId + " not found.")
        .build();
  }
}
