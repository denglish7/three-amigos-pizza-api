package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.api.pizza.PizzaController;
import io.swagger.model.customer.Customer;
import io.swagger.model.order.Order;
import io.swagger.model.order.OrderPrice;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.store.Menu;
import io.swagger.model.store.Store;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.StoreRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
  private SizeRepository sizeRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  PizzaController pizzaController;

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

  @RequestMapping(path = "/{orderId}/customer", method = RequestMethod.PUT)
  @ApiOperation(value = "Updates an order's customer", tags = {"order",})
  public ResponseEntity<Order> setCustomerById(
      @ApiParam("Order Id to set customer") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Id of customer.") @RequestParam(value = "customerId") String customerId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (!customer.isPresent()) {
      return ResponseEntity.notFound()
          .header("message", "customerId " + customerId + " not found.").build();
    }
    order.setCustomer(customer.get());
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/addPizzaById", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a pizza from store's menu to an order", tags = {"order",})
  public ResponseEntity<Order> addPizzaById(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Id to add to order.") @RequestParam(value = "pizzaId") String pizzaId,
      @ApiParam("Size Id for pizza.") @RequestParam(value = "pizzaId") String sizeId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    String orderStoreId = order.getStoreId();
    Optional<Store> orderStore = storeRepository.findById(orderStoreId);
    if (!orderStore.isPresent()) {
      return ResponseEntity.notFound()
          .header("message", "storeId " + orderStoreId + " not found.")
          .build();
    }
    Menu orderMenu = orderStore.get().getMenu();
    Pizza pizzaToAdd = orderMenu.getPizza(pizzaId);
    if (pizzaToAdd == null) {
      return ResponseEntity.notFound().header("message", "pizzaId " + pizzaId + " not found.")
          .build();
    }
    Optional<Size> size = sizeRepository.findById(sizeId);
    if (!size.isPresent()) {
      return ResponseEntity.notFound().header("message",
          "sizeId " + sizeId + " not found.").build();
    }
    pizzaToAdd.setSize(size.get());
    pizzaToAdd.setPrice(pizzaController.calculatePrice(pizzaToAdd));
    order.addPizza(pizzaToAdd);
    order.setPrice(order.getPrice() + pizzaToAdd.getPrice());
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/addCustomPizza", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a custom pizza to an order", tags = {"order",})
  public ResponseEntity<Order> addPizza(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza to add to order.") @RequestParam(value = "pizzas") @Valid @RequestBody Pizza pizza) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    if (pizza.getPrice() == null) {
      try {
        pizza.setPrice(pizzaController.calculatePrice(pizza));
      } catch (PizzaHasNoSizeException e) {
        return ResponseEntity.badRequest().header("message",
            "Custom pizza must either have price field, or valid size for price calculation. " + e
                .getMessage()).build();
      }
    }
    order.setPrice(order.getPrice() + pizza.getPrice());
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/removePizzaById", method = RequestMethod.DELETE)
  @ApiOperation(value = "Removes a pizza from an order", tags = {"order",})
  public ResponseEntity<Order> removePizzaById(
      @ApiParam("Order Id to remove pizza from.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Id to remove from order.") @RequestParam(value = "pizzaId") String pizzaId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    Pizza removedPizza = order.removePizzaById(pizzaId);
    if (removedPizza == null) {
      return ResponseEntity.badRequest()
          .header("message", "pizza with Id " + pizzaId + " not found in order with Id " + orderId)
          .build();
    }
    order.setPrice(order.getPrice() - removedPizza.getPrice());
    return ResponseEntity.ok(orderRepository.save(order));
  }


  @RequestMapping(path = "/{orderId}/price", method = RequestMethod.GET)
  @ApiOperation(value = "Gets the price of an order", tags = {"order",})
  public ResponseEntity<OrderPrice> getOrderPrice(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId) {
    return null;
  }

  @RequestMapping(path = "/{orderId}/applySpecial", method = RequestMethod.GET)
  @ApiOperation(value = "Applies a special to an order", tags = {"order",})
  public ResponseEntity<OrderPrice> applySpecial(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId) {
    return null;
  }

}
