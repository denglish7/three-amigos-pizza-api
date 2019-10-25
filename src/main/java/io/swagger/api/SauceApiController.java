package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.classes.PizzaDetails.Sauce;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class SauceApiController implements SauceApi {

  private static final Logger log = LoggerFactory.getLogger(SauceApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @Autowired
  public SauceApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
    this.request = request;
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

