package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.api.exceptions.InvalidPizzaException;
import io.swagger.api.exceptions.InvalidSpecialApplicationException;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.store.StoreController;
import io.swagger.model.customer.Customer;
import io.swagger.model.order.Order;
import io.swagger.model.order.OrderPizza;
import io.swagger.model.order.OrderSpecial;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
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

  private static final String MESSAGE_HEADER_NAME = "message";
  private static final String NOT_ON_MENU_MESSAGE = "%s not found in menu associated with this order.";
  private static final String CUSTOM_PIZZA_DEFAULT_NAME = "custom";

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
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "storeId " + storeId + " not found.")
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
    if (!order.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.")
          .build();
    }
    return ResponseEntity.ok(order.get());
  }

  @RequestMapping(path = "/{orderId}/customer", method = RequestMethod.PUT)
  @ApiOperation(value = "Updates an order's customer", tags = {"order",})
  public ResponseEntity<Order> setCustomerById(
      @ApiParam("Order Id to set customer") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Id of customer.") @RequestParam(value = "customerId") String customerId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (!customer.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "customerId " + customerId + " not found.").build();
    }
    order.setCustomer(customer.get());
    return ResponseEntity.ok(orderRepository.save(order));
  }

  @RequestMapping(path = "/{orderId}/addCustomPizza", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a custom pizza to an order", tags = {"order",})
  public ResponseEntity<OrderPizza> addCustomPizza(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Name for new pizza, default is 'custom'") @RequestParam(value = "name", required = false, defaultValue = CUSTOM_PIZZA_DEFAULT_NAME) String name,
      @ApiParam("Crust ID for new pizza") @RequestParam(value = "crustId") String crustId,
      @ApiParam("Topping ID's for new pizza") @RequestParam(value = "toppingIds") List<String> toppingIds,
      @ApiParam("Size ID for new pizza") @RequestParam(value = "sizeId") String sizeId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    try {
      OrderPizza customPizza = pizzaController
          .validateOrderPizza(name, crustId, toppingIds, sizeId);
      order.addPizza(customPizza);
      orderRepository.save(order);
      return ResponseEntity.ok(customPizza);
    } catch (InvalidPizzaException e) {
      return ResponseEntity.badRequest().header(MESSAGE_HEADER_NAME, e.getMessage()).build();
    }
  }


  @RequestMapping(path = "/{orderId}/addPizzaById", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a pizza from store's menu to an order", tags = {"order",})
  public ResponseEntity<OrderPizza> addPizzaById(
      @ApiParam("Order Id to add to.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Id to add to order.") @RequestParam(value = "pizzaId") String pizzaId,
      @ApiParam("Size Id for pizza.") @RequestParam(value = "sizeId") String sizeId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.").build();
    }
    Order order = orderToGet.get();
    ResponseEntity<Menu> menuResponse = storeController.getMenu(order.getStoreId());
    Menu orderMenu = menuResponse.getBody();
    if (orderMenu == null) {
      return ResponseEntity.notFound()
          .header(menuResponse.getHeaders().getFirst(MESSAGE_HEADER_NAME))
          .build();
    }
    Pizza pizzaToAdd = orderMenu.getPizza(pizzaId);
    if (pizzaToAdd == null) {
      return ResponseEntity.notFound().header(MESSAGE_HEADER_NAME,
          "pizzaId " + pizzaId + " not found in menu associated with this order.")
          .build();
    }
    Optional<Size> size = sizeRepository.findById(sizeId);
    if (!size.isPresent()) {
      return ResponseEntity.notFound().header(MESSAGE_HEADER_NAME,
          "sizeId " + sizeId + " not found.").build();
    }
    OrderPizza pizza = new OrderPizza(pizzaToAdd, size.get());
    order.addPizza(pizza);
    orderRepository.save(order);
    return ResponseEntity.ok(pizza);
  }

  @RequestMapping(path = "/{orderId}/addSpecial", method = RequestMethod.PUT)
  @ApiOperation(value = "Adds a special to an order", tags = {"order",})
  public ResponseEntity<OrderSpecial> addSpecial(
      @ApiParam("Order id to get price of.") @PathVariable("orderId") String orderId,
      @ApiParam("Special id to add to order.") @RequestParam("specialId") String specialId,
      @ApiParam("List of pizzaId's to apply to special.") @RequestParam("pizzaIds") List<String> pizzaIds) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.").build();
    }
    Order order = orderToGet.get();
    try {
      OrderSpecial orderSpecial = validateSpecial(specialId, pizzaIds, order);
      order.addSpecial(orderSpecial);
      orderRepository.save(order);
      return ResponseEntity.ok(orderSpecial);
    } catch (InvalidSpecialApplicationException e) {
      return ResponseEntity.badRequest().header(MESSAGE_HEADER_NAME, e.getMessage()).build();
    }
  }

  @RequestMapping(path = "/{orderId}/removePizza", method = RequestMethod.DELETE)
  @ApiOperation(value = "Removes a pizza from an order", tags = {"order",})
  public ResponseEntity<Order> removePizzaById(
      @ApiParam("Order Id to remove pizza from.") @PathVariable(value = "orderId") String orderId,
      @ApiParam("Pizza Id to remove from order.") @RequestParam(value = "pizzaId") String pizzaId) {
    Optional<Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.").build();
    }
    Order order = orderToGet.get();
    boolean removedPizza = order.removePizza(pizzaId);
    if (!removedPizza) {
      return ResponseEntity.badRequest()
          .header(MESSAGE_HEADER_NAME, "pizza with Id " + pizzaId + " not found in order").build();
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
      return ResponseEntity.notFound()
          .header(MESSAGE_HEADER_NAME, "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    boolean specialRemoved = order.removeSpecialById(specialId);
    if (!specialRemoved) {
      return ResponseEntity.badRequest()
          .header(MESSAGE_HEADER_NAME,
              "specialId " + specialId + " not found in order with Id " + orderId)
          .build();
    }
    return ResponseEntity.ok(orderRepository.save(order));
  }

  /**
   * Helper method to validate a special before adding it to an order.
   */
  private OrderSpecial validateSpecial(String specialId, List<String> pizzaIds, Order order)
      throws InvalidSpecialApplicationException {
    if (order.getOrderItems().getSpecials().containsKey(specialId)) {
      throw new InvalidSpecialApplicationException(
          "specialId " + specialId + " already exists in this order.");
    }
    ResponseEntity<Menu> menuResponse = storeController.getMenu(order.getStoreId());
    Menu orderMenu = menuResponse.getBody();
    if (orderMenu == null) {
      throw new InvalidSpecialApplicationException(
          menuResponse.getHeaders().getFirst(MESSAGE_HEADER_NAME));
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
    List<OrderPizza> specialPizzas = new ArrayList<>();
    for (String pizzaId : pizzaIds) {
      Pizza pizza = orderMenu.getPizza(pizzaId);
      if (pizza == null) {
        throw new InvalidSpecialApplicationException(
            String.format(NOT_ON_MENU_MESSAGE, "pizzaId " + pizzaId));
      }
      specialPizzas.add(new OrderPizza(pizza, requiredSize));
    }
    return new OrderSpecial(special, specialPizzas);
  }

  public static String getMessageHeaderName() {
    return MESSAGE_HEADER_NAME;
  }
}
