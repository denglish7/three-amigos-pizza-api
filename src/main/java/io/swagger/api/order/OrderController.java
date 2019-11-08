package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.customer.Customer;
import io.swagger.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates an order", tags = {"order",})
  public ResponseEntity<Customer> createOrder() {
    return null;
  }

  @RequestMapping(path = "/{orderId}/price", method = RequestMethod.GET)
  @ApiOperation(value = "Gets the price of an order", tags = {"order",})
  public ResponseEntity<Customer> getOrderPrice(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId
  ) {
    return null;
  }

}
