package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Sauce;
import java.io.IOException;
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

@Api(value = "sauce")
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

  @ApiOperation(value = "adds a sauce", tags={ "admins", })
  @RequestMapping(value = "/",
      method = RequestMethod.POST)
  public Sauce createSauce(@Valid @RequestBody Sauce sauce) {
    repository.save(sauce);
    return sauce;
  }

  @ApiOperation(value = "searches for a sauce", nickname = "searchSauce", response = Sauce.class, tags = {"developers",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Sauce.class),
      @ApiResponse(code = 400, message = "bad input parameter")})
  @RequestMapping(value = "/{name}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  public Sauce searchSauce(@PathVariable("name") String name) {
    return repository.findByName(name);
  }
}

