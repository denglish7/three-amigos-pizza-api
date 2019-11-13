package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.api.exceptions.InvalidSpecialApplicationException;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.store.StoreController;
import io.swagger.model.customer.Customer;
import io.swagger.model.order.Order;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.specials.OrderSpecial;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Menu;
import io.swagger.model.store.Store;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.StoreRepository;
import java.util.ArrayList;
import java.util.List;
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

  private static final Double ORDER_BASE_PRICE = 0.0;
  private static final String INVALID_PIZZA_MESSAGE = "Pizza must have valid size, crust, and toppings fields to be added to an order.";
  private static final String NOT_ON_MENU_MESSAGE = "%s not found in menu associated with this order.";

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private PizzaController pizzaController;
  @Autowired
  private StoreController storeController;

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

  @RequestMapping(path = "/{orderId}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for an Order by id", response = Order.class, tags = {"order",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Order.class),
      @ApiResponse(code = 404, message = "order not found")})
  public ResponseEntity<Order> findById(
      @ApiParam("Id of size to get.") @PathVariable("orderId") String orderId) {
    Optional<Order> order = orderRepository.findById(orderId);
    if (order.isPresent()) {
      return ResponseEntity.of(order);
    }
    return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
        .build();
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

  @RequestMapping(path = "/{orderId}/addCustomPizza", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a custom pizza to an order", tags = {"order",})
  public ResponseEntity<Order> addCustomPizza(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza to add to order.") @RequestParam(value = "pizza") @Valid @RequestBody Pizza pizza) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    if (!pizza.isValidForOrder()) {
      return ResponseEntity.badRequest()
          .header("message", INVALID_PIZZA_MESSAGE).build();
    }
    pizza.setPrice();
    order.addPizza(pizza);
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/addPizzaById", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a pizza from store's menu to an order", tags = {"order",})
  public ResponseEntity<Order> addPizzaById(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Id to add to order.") @RequestParam(value = "pizzaId") String pizzaId,
      @ApiParam("Size Id for pizza.") @RequestParam(value = "sizeId") String sizeId) {
    ResponseEntity<Order> orderResponse = findById(orderId);
    Order order = orderResponse.getBody();
    if (order == null) {
      return ResponseEntity.notFound().header(orderResponse.getHeaders().getFirst("message"))
          .build();
    }
    ResponseEntity<Menu> menuResponse = storeController.getMenu(order.getStoreId());
    Menu orderMenu = menuResponse.getBody();
    if (orderMenu == null) {
      return ResponseEntity.notFound().header(menuResponse.getHeaders().getFirst("message"))
          .build();
    }
    Pizza pizzaToAdd = orderMenu.getPizza(pizzaId);
    if (pizzaToAdd == null) {
      return ResponseEntity.notFound().header("message",
          "pizzaId " + pizzaId + " not found in menu associated with this order.")
          .build();
    }
    Optional<Size> size = sizeRepository.findById(sizeId);
    if (!size.isPresent()) {
      return ResponseEntity.notFound().header("message",
          "sizeId " + sizeId + " not found.").build();
    }
    pizzaToAdd.setSize(size.get());
    pizzaToAdd.setPrice();
    order.addPizza(pizzaToAdd);
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
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/addSpecial", method = RequestMethod.GET)
  @ApiOperation(value = "Adds a special to an order", tags = {"order",})
  public ResponseEntity<Order> addSpecial(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId,
      @ApiParam("Special id to add to order.") @RequestParam("specialId") String specialId,
      @ApiParam("List of pizzaId's to apply to special.") @RequestParam("pizzaIds") List<String> pizzaIds) {
    ResponseEntity<Order> orderResponse = findById(orderId);
    Order order = orderResponse.getBody();
    if (order == null) {
      return ResponseEntity.notFound().header(orderResponse.getHeaders().getFirst("message"))
          .build();
    }
    try {
      OrderSpecial orderSpecial = validateSpecial(specialId, pizzaIds, order);
      order.addSpecial(orderSpecial);
    } catch (InvalidSpecialApplicationException e) {
      return ResponseEntity.badRequest().header("message", e.getMessage()).build();
    }
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/removeSpecialById", method = RequestMethod.DELETE)
  @ApiOperation(value = "Removes a special from an order", tags = {"order",})
  public ResponseEntity<Order> removeSpecialById(
      @ApiParam("Order Id to remove special from.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Special Id to remove from order.") @RequestParam(value = "specialId") String specialId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    OrderSpecial removedSpecial = order.removeSpecialById(specialId);
    if (removedSpecial == null) {
      return ResponseEntity.badRequest()
          .header("message", "specialId " + specialId + " not found in order with Id " + orderId)
          .build();
    }
    return ResponseEntity.ok(orderRepository.save(order));
  }

  /**
   * Helper method to validate a special before adding it to an order.
   */
  private OrderSpecial validateSpecial(String specialId, List<String> pizzaIds, Order order)
      throws InvalidSpecialApplicationException {
    ResponseEntity<Menu> menuResponse = storeController.getMenu(order.getStoreId());
    Menu orderMenu = menuResponse.getBody();
    if (orderMenu == null) {
      throw new InvalidSpecialApplicationException(menuResponse.getHeaders().getFirst("message"));
    }
    Special special = orderMenu.getSpecial(specialId);
    if (special == null) {
      throw new InvalidSpecialApplicationException(
          String.format(NOT_ON_MENU_MESSAGE, "specialId " + specialId));
    }
    Size requiredSize = special.getRequiredSizeOfPizzas();
    if (pizzaIds.size() != special.getRequiredNumberPizzas()) {
      throw new InvalidSpecialApplicationException(
          special.getName() + " requires " + special.getRequiredNumberPizzas() + " pizzas, but "
              + pizzaIds.size() + " pizzaIds were supplied");
    }
    List<Pizza> specialPizzas = new ArrayList<>();
    for (String pizzaId : pizzaIds) {
      Pizza pizza = orderMenu.getPizza(pizzaId);
      if (pizza == null) {
        throw new InvalidSpecialApplicationException(
            String.format(NOT_ON_MENU_MESSAGE, "pizzaId " + pizzaId));
      }
      pizza.setSize(requiredSize);
      specialPizzas.add(pizza);
    }
    return new OrderSpecial(special, specialPizzas);
  }
}
