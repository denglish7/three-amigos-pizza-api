//package io.swagger.api;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.annotations.ApiParam;
//
//import io.swagger.model.PizzaDetails.Topping;
//import java.io.IOException;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-27T20:56:20.347Z[GMT]")
//@Controller
//public class ToppingApiController implements ToppingApi {
//
//  private static final Logger log = LoggerFactory.getLogger(ToppingApiController.class);
//
//  private final ObjectMapper objectMapper;
//
//  private final HttpServletRequest request;
//
//  @org.springframework.beans.factory.annotation.Autowired
//  public ToppingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
//    this.objectMapper = objectMapper;
//    this.request = request;
//  }
//
//  public ResponseEntity<List<Topping>> searchToppings(@ApiParam(value = "pass an optional search string for looking up toppings") @Valid @RequestParam(value = "searchString", required = false) String searchString) {
//    String accept = request.getHeader("Accept");
//    if (accept != null && accept.contains("application/json")) {
//      try {
//        return new ResponseEntity<List<Topping>>(objectMapper.readValue("[ {\n  \"type\" : \"mushrooms\",\n } ]", List.class), HttpStatus.NOT_IMPLEMENTED);
//      } catch (IOException e) {
//        log.error("Couldn't serialize response for content type application/json", e);
//        return new ResponseEntity<List<Topping>>(HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
//
//    return new ResponseEntity<List<Topping>>(HttpStatus.NOT_IMPLEMENTED);
//  }
//
//}
