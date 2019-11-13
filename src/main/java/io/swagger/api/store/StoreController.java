
package io.swagger.api.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.api.customer.ReceiptController;
import io.swagger.model.customer.CreditCard;
import io.swagger.model.customer.Receipt;
import io.swagger.model.specials.Special;
import io.swagger.model.order.Order;
import io.swagger.model.store.Menu;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.store.Store;
import io.swagger.repositories.*;

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
@RequestMapping("/store")
public class StoreController {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private ReceiptController receiptController;
  @Autowired
  private ReceiptRepository receiptRepository;


  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all stores in the system.", response = Store.class, responseContainer = "List", tags = {
      "store",})
  public ResponseEntity <List <Store>> getAllStores() {
    return ResponseEntity.ok(storeRepository.findAll());
  }


  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a store", tags = {"store",})
  public ResponseEntity <Store> createStore(
      @ApiParam("Name for new store") @RequestParam(value = "storeName") String storeName,
      @ApiParam("Location for new store") @RequestParam(value = "storeAddress") String storeLocation,
      @ApiParam("Optional existing storeID to duplicate menu from") @RequestParam(value = "storeId", required = false) String storeId) {
    Menu storeMenu = new Menu();
    if (storeId != null) {
      Optional <Store> store = storeRepository.findById(storeId);
      if (!store.isPresent()) {
        return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
            .build();
      }
      storeMenu.addPizzas(store.get().getMenu().getPizzas());
      storeMenu.addSpecials(store.get().getMenu().getSpecials());
    }
    Store newStore = new Store(storeName, storeLocation, storeMenu);
    return ResponseEntity.ok(storeRepository.save(newStore));
  }


  @RequestMapping(path = "/{storeId}/menu", method = RequestMethod.GET)
  @ApiOperation(value = "Get a store's menu", tags = {"store",})
  public ResponseEntity <Menu> getMenu(
      @ApiParam("Store Id to get menu of.") @PathVariable("storeId") String storeId) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    return ResponseEntity.ok(storeToGet.get().getMenu());
  }

  @RequestMapping(path = "/{storeId}/menu/add", method = RequestMethod.PUT)
  @ApiOperation(value = "Add to a store's menu", tags = {"store",})
  public ResponseEntity <Menu> addToStoreMenu(
      @ApiParam("Store Id to add pizza to menu of.") @PathVariable("storeId") String storeId,
      @ApiParam("List of Pizza ids to add to menu") @RequestParam(value = "pizzaIds", required = false) List <String> pizzaIds,
      @ApiParam("List of Special ids to add to menu") @RequestParam(value = "specialIds", required = false) List <String> specialIds) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Store store = storeToGet.get();
    Menu storeMenu = store.getMenu();
    if (pizzaIds != null) {
      List <Pizza> pizzasToAdd = new ArrayList <>();
      for (String pizzaId : pizzaIds) {
        Optional <Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (!pizza.isPresent()) {
          return ResponseEntity.notFound().header("message", "pizzaId " + pizzaId + " not found.")
              .build();
        } else {
          pizzasToAdd.add(pizza.get());
        }
      }
      storeMenu.addPizzas(pizzasToAdd);
    }
    if (specialIds != null) {
      List <Special> specialsToAdd = new ArrayList <>();
      for (String specialId : specialIds) {
        Optional <Special> special = specialRepository.findById(specialId);
        if (!special.isPresent()) {
          return ResponseEntity.notFound()
              .header("message", "specialId " + specialId + " not found.").build();
        } else {
          specialsToAdd.add(special.get());
        }
      }
      storeMenu.addSpecials(specialsToAdd);
    }
    store.setMenu(storeMenu);
    storeRepository.save(store);
    return ResponseEntity.ok(storeMenu);
  }

  @RequestMapping(path = "/{storeId}/menu/remove", method = RequestMethod.PUT)
  @ApiOperation(value = "Remove items from a store's menu", tags = {"store",})
  public ResponseEntity <Menu> removeFromStoreMenu(
      @ApiParam("Store Id to remove menu items from.") @PathVariable("storeId") String storeId,
      @ApiParam("List of Pizza ids to remove from menu") @RequestParam(value = "pizzaIds", required = false) List <String> pizzaIds,
      @ApiParam("List of Special ids to remove from menu") @RequestParam(value = "specialIds", required = false) List <String> specialIds) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Store store = storeToGet.get();
    Menu storeMenu = store.getMenu();
    if (pizzaIds != null) {
      for (String pizzaId : pizzaIds) {
        Optional <Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (!pizza.isPresent()) {
          return ResponseEntity.notFound().header("message", "pizzaId " + pizzaId + " not found.")
              .build();
        }
      }
      storeMenu.removePizzas(pizzaIds);
    }
    if (specialIds != null) {
      for (String specialId : specialIds) {
        Optional <Special> special = specialRepository.findById(specialId);
        if (!special.isPresent()) {
          return ResponseEntity.notFound()
              .header("message", "specialId " + specialId + " not found.").build();
        }
      }
      storeMenu.removePizzas(specialIds);
    }
    store.setMenu(storeMenu);
    storeRepository.save(store);
    return ResponseEntity.ok(storeMenu);
  }


  @RequestMapping(path = "/{storeId}/Location", method = RequestMethod.GET)
  @ApiOperation(value = "Get a store's location", tags = {"store",})
  public ResponseEntity <String> getLocation(
      @ApiParam("Store Id to get the location of.") @PathVariable("storeId") String storeId) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " location not found.")
          .build();
    }
    return ResponseEntity.ok(storeToGet.get().getAddress());
  }


  @RequestMapping(path = "/{storeId}/UpdateLocation", method = RequestMethod.PUT)
  @ApiOperation(value = "Store Id to change the location of.", tags = {"store",})
  public ResponseEntity <Store> changeLocation(
      @ApiParam("Store Id to get menu of.") @PathVariable("storeId") String storeId,
      @ApiParam("New store location.") @RequestParam(value = "location") String location) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " location not found.")
          .build();
    }
    Store store = storeToGet.get();
    store.setAddress(location);
    return ResponseEntity.ok(storeRepository.save(store));
  }

  @RequestMapping(path = "/{storeId}/checkout", method = RequestMethod.PUT)
  @ApiOperation(value = "Submit your order.", tags = {"store",})
  public ResponseEntity <Receipt> processNewOrder(
      @ApiParam("Store Id of the store processing the order.") @PathVariable("storeId") String storeId,
      @ApiParam("Order Id to process.") @RequestParam(value = "OrderId", required = true) String orderId) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Store store = storeToGet.get();

    Optional <Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    Order order = orderToGet.get();
    if (order.isEmpty()) {
      return ResponseEntity.badRequest().header("message", "orderId " + orderId + " has no pizza's in cart.")
          .build();
    }
    CreditCard customerCreditCard = order.getCustomer().getCreditCard();
    if (!store.validateCard(customerCreditCard)) {
      return ResponseEntity.badRequest().header("message", "Invalid card number entered.")
          .build();
    }
    List<String> pizzaNames = order.getOrderItems().getPizzaNames();
    if (pizzaNames.size() < 1) {
      return ResponseEntity.badRequest().header("message", "No pizzas in order to process.")
          .build();
    }

    String paymentDetails = customerCreditCard.getCardNumber().substring(customerCreditCard.getCardNumber().length()-4);

    ResponseEntity <Receipt> receipt = receiptController.createReceipt(
        store.getName(),
        order.getCustomer().getName(),
        orderId,
        pizzaNames,
        order.getOrderItems().getSpecialNames(),
        paymentDetails,
        order.getOrderItems().getPrice(order.getPrice())
    );
    store.processOrder(orderId);
    ResponseEntity.ok(storeRepository.save(store));
    return ResponseEntity.ok(receipt.getBody());
  }

  @RequestMapping(path = "/{storeId}/complete", method = RequestMethod.PUT)
  @ApiOperation(value = "Submit your order.", tags = {"store",})
  public ResponseEntity <Store> completeOrder(
      @ApiParam("Store Id of the Store completing the order.") @PathVariable("storeId") String storeId,
      @ApiParam("Order Id of the completed Order.") @RequestParam(value = "OrderId", required = true) String orderId) {
    Optional <Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Store store = storeToGet.get();

    Optional <Order> orderToGet = orderRepository.findById(orderId);
    if (!orderToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "orderId " + orderId + " not found.")
          .build();
    }
    store.completeOrder(orderId);
    return ResponseEntity.ok(storeRepository.save(store));
  }
}