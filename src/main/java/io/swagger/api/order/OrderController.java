package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

}
