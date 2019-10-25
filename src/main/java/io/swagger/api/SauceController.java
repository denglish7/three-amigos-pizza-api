package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sauce")
public class SauceController implements SauceApi {

  @Autowired
  private SauceRepository sauceRepository;

  private static final Logger log = LoggerFactory.getLogger(SauceController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @Autowired
  public SauceController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
    this.request = request;
  }

  public ResponseEntity<Void> addSauce(@ApiParam(value = "Sauce to add" ) @Valid @RequestBody Sauce body) {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Sauce> searchSauce(@Valid String searchString) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<Sauce>(objectMapper.readValue("[ { \n \"name\" :  } ]", Sauce.class), HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new ResponseEntity<Sauce>(HttpStatus.NOT_IMPLEMENTED);
  }
}

