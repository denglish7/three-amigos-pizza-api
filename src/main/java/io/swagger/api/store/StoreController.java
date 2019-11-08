package io.swagger.api.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.menu.Menu;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Store;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.SpecialRepository;
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
@RequestMapping("/store")
public class StoreController {

  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private SpecialRepository specialRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all stores in the system.", response = Store.class, responseContainer = "List", tags = {
      "store",})
  public ResponseEntity<List<Store>> getAllStores() {
    return ResponseEntity.ok(storeRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a store", tags = {"store",})
  public ResponseEntity<Store> createStore(
      @ApiParam("Name for new store") @RequestParam(value = "storeName") String storeName,
      @ApiParam("Address for new store") @RequestParam(value = "storeAddress") String storeAddress,
      @ApiParam("Optional existing storeID to duplicate menu from") @RequestParam(value = "storeId", required = false) String storeId) {
    Menu storeMenu = new Menu();
    if (storeId != null) {
      Optional<Store> store = storeRepository.findById(storeId);
      if (!store.isPresent()) {
        return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
            .build();
      }
      storeMenu.addPizzas(store.get().getMenu().getPizzas());
      storeMenu.addSpecials(store.get().getMenu().getSpecials());
    }
    Store newStore = new Store(storeName, storeAddress, storeMenu);
    return ResponseEntity.ok(storeRepository.save(newStore));
  }

  @RequestMapping(path = "/{storeId}/menu", method = RequestMethod.GET)
  @ApiOperation(value = "Get a store's menu", tags = {"store",})
  public ResponseEntity<Menu> getMenu(
      @ApiParam("Store Id to get menu of.") @PathVariable("storeId") String storeId) {
    Optional<Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    return ResponseEntity.ok(storeToGet.get().getMenu());
  }

  @RequestMapping(path = "/{storeId}/menu/add", method = RequestMethod.PUT)
  @ApiOperation(value = "Add to a store's menu", tags = {"store",})
  public ResponseEntity<Menu> addPizzasToMenu(
      @ApiParam("Store Id to add pizza to menu of.") @PathVariable("storeId") String storeId,
      @ApiParam("List of Pizza ids to add to menu") @RequestParam(value = "pizzaIds", required = false) List<String> pizzaIds,
      @ApiParam("List of Special ids to add to menu") @RequestParam(value = "specialIds", required = false) List<String> specialIds) {
    Optional<Store> storeToGet = storeRepository.findById(storeId);
    if (!storeToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "storeId " + storeId + " not found.")
          .build();
    }
    Store store = storeToGet.get();
    Menu storeMenu = store.getMenu();
    if (pizzaIds != null) {
      List<Pizza> pizzasToAdd = new ArrayList<>();
      for (String pizzaId : pizzaIds) {
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
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
      List<Special> specialsToAdd = new ArrayList<>();
      for (String specialId : specialIds) {
        Optional<Special> special = specialRepository.findById(specialId);
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
}
