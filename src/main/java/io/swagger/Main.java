package io.swagger;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Main {

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
