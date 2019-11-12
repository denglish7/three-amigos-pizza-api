package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.customer.Customer;
import io.swagger.model.menu.Menu;
import io.swagger.model.order.Order;
import io.swagger.model.order.OrderPrice;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.store.Store;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private CustomerRepository customerRepository;

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates an order", tags = {"order",})
  public ResponseEntity<Order> createOrder(
      @ApiParam("Store Id for order.") @RequestParam(value = "storeId") String storeId) {
    Optional<Store> store = storeRepository.findById(storeId);
    if (!store.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Order newOrder = new Order(storeId);
    return ResponseEntity.ok(orderRepository.save(newOrder));
  }

  @RequestMapping(path = "/{orderId}/add", method = RequestMethod.PUT)
  @ApiOperation(value = "Updates the contents of an order", tags = {"order",})
  public ResponseEntity<Order> addToOrder(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Ids to add to order.") @RequestParam(value = "pizzaIds", required = false) List<String> pizzaIds,
      @ApiParam("Customer Id to add to order") @RequestParam(value = "customerId", required = false) String customerId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    if (customerId != null) {
      Optional<Customer> customer = customerRepository.findById(customerId);
      if (!customer.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "customerId " + customerId + " not found.")
            .build();
      }
      order.setCustomer(customer.get());
    }
    if (pizzaIds != null) {
      String orderStoreId = order.getStoreId();
      Optional<Store> orderStore = storeRepository.findById(orderStoreId);
      if (!orderStore.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "storeId " + orderStoreId + " not found.")
            .build();
      }
      Menu orderMenu = orderStore.get().getMenu();
      List<Pizza> pizzasToAdd = new ArrayList<>();
      for (String pizzaId : pizzaIds) {
        Pizza pizza = orderMenu.getPizza(pizzaId);
        if (pizza == null) {
          return ResponseEntity.notFound().header("message",
              "pizzaId " + pizzaId + " is not on the menu of the store associated with this order!")
              .build();
        }
        pizzasToAdd.add(pizza);
      }
      order.addPizzas(pizzasToAdd);
    }
    return ResponseEntity.ok(orderRepository.save(order));
  }


  @RequestMapping(path = "/{orderId}/price", method = RequestMethod.GET)
  @ApiOperation(value = "Gets the price of an order", tags = {"order",})
  public ResponseEntity<OrderPrice> getOrderPrice(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId) {
    return null;
  }

  @RequestMapping(path = "/{orderId}/applySpecial", method = RequestMethod.GET)
  @ApiOperation(value = "Gets the price of an order", tags = {"order",})
  public ResponseEntity<OrderPrice> applySpecial(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId) {
    return null;
  }

}
