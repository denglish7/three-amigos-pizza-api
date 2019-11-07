//package io.swagger.api.pizza;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import io.swagger.model.pizza.Sauce;
//import io.swagger.repositories.CrustRepository;
//import java.util.List;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Sauces.")
//@RestController
//@RequestMapping("/sauce")
//public class SauceController {
//
//  @Autowired
//  private CrustRepository repository;
//
//  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//  @ApiOperation(value = "Returns list of all Sauces in the system.", response = Sauce.class, responseContainer = "List", tags = {"developers",})
//  public List getAllSauces() {
//    return repository.findAll();
//  }
//
//  @RequestMapping(path = "/", method = RequestMethod.POST)
//  @ApiOperation(value = "Creates a sauce", tags={ "admins", })
//  public Sauce createSauce(@ApiParam("Sauce information for a new sauce") @Valid @RequestBody Sauce sauce) {
//    return repository.save(sauce);
//  }
//
//  @RequestMapping(path = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
//  @ApiOperation(value = "Searches for a sauce by name", response = Sauce.class, tags = {"developers",})
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = "search results matching criteria", response = Sauce.class),
//      @ApiResponse(code = 404, message = "sauce not found")})
//  public ResponseEntity searchSauceByName(@ApiParam("Name of sauce to get.") @PathVariable("name") String name) {
//    Sauce sauce = repository.findByName(name);
//    if (sauce == null) {
//      return new ResponseEntity(HttpStatus.NOT_FOUND);
//    } else {
//      return ResponseEntity.ok(sauce);
//    }
//  }
//}
//
