package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Sauce;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Sauces.")
@RestController
@RequestMapping("/sauce")
public class SauceController {

  @Autowired
  private SauceRepository repository;

//  private static final Logger log = LoggerFactory.getLogger(SauceController.class);
//
//  private final ObjectMapper objectMapper;
//
//  private final HttpServletRequest request;
//
//  @Autowired
//  public SauceController(ObjectMapper objectMapper, HttpServletRequest request) {
//    this.objectMapper = objectMapper;
//    this.request = request;
//  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Sauces in the system.", response = Sauce.class, responseContainer = "List", tags = {"developers",})
  public List getAllSauces() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a sauce", tags={ "admins", })
  public Sauce createSauce(@ApiParam("Sauce information for a new sauce") @Valid @RequestBody Sauce sauce) {
    repository.save(sauce);
    return sauce;
  }

  @RequestMapping(path = "/{name}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a sauce by name", nickname = "searchSauce", response = Sauce.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Sauce.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  public Sauce searchSauce(@ApiParam("Name of sauce to get. Cannot be empty.") @PathVariable("name") String name) {
    return repository.findByName(name);
  }
}

