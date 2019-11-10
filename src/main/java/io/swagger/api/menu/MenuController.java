package io.swagger.api.menu;

import io.swagger.annotations.*;
import io.swagger.model.menu.Menu;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.specials.Special;
import io.swagger.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api
@RestController
@RequestMapping("/menu")
public class MenuController {

  @Autowired
  private MenuRepository menuRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private SpecialRepository specialRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Sizes in the system.", response = Menu.class, responseContainer = "List", tags = {
      "pizza",})
  public ResponseEntity<List<Menu>> getAllMenus() {
    return ResponseEntity.ok(menuRepository.findAll());
  }

  @RequestMapping(path = "/{menuId}", produces = {"application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a Size by id", response = Menu.class, tags = {"menu",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Menu.class),
      @ApiResponse(code = 404, message = "menu not found")})
  public ResponseEntity<Menu> findById(
      @ApiParam("Id of size to get.") @PathVariable("menuId") String menuId) {
    Optional<Menu> menu = menuRepository.findById(menuId);
    if (menu.isPresent()) {
      return ResponseEntity.of(menu);
    }
    return ResponseEntity.notFound().header("message", "menuId " + menuId + " not found.").build();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates an menu", tags = {"menu",})
  public ResponseEntity<Menu> createMenu(){
    Menu newMenu = new Menu();
    return ResponseEntity.ok(menuRepository.save(newMenu));
  }

  @RequestMapping(path = "/{pizzaId}/add", method = RequestMethod.PUT)
  @ApiOperation(value = "Updates the pizzas on a menu", tags = {"menu",})
  public ResponseEntity<Menu> addPizzaToMenu(
      @ApiParam("Pizza Id to add to order.") @PathVariable(value = "pizzaId") String pizzaId,
      @ApiParam("Menu Id to add to.") @RequestParam(value = "menuId") String menuId) {
    Optional <Menu> menuToGet = menuRepository.findById(menuId);
    if (!menuToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "menuId " + menuId + " not found.")
          .build();
    }
    Menu menu = menuToGet.get();
    if (pizzaId != null) {
      Optional <Pizza> pizza = pizzaRepository.findById(pizzaId);
      if (!pizza.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "pizzaId " + pizzaId + " not found.")
            .build();
      }
      Pizza newPizza = pizza.get();
      List <Pizza> pizzaToAdd = new ArrayList <>();
      pizzaToAdd.add(newPizza);
      menu.addPizzas(pizzaToAdd);
    }
    return ResponseEntity.ok(menuRepository.save(menu));
  }

  @RequestMapping(path = "/{pizzaId}/remove", method = RequestMethod.PUT)
  @ApiOperation(value = "Removes a pizza from a menu", tags = {"menu",})
  public ResponseEntity<Menu> removePizzaTFromMenu(
      @ApiParam("Pizza Id to remove from order.") @PathVariable(value = "pizzaId") String pizzaId,
      @ApiParam("Menu Id to remove from.") @RequestParam(value = "menuId") String menuId) {
    Optional <Menu> menuToGet = menuRepository.findById(menuId);
    if (!menuToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "menuId " + menuId + " not found.")
          .build();
    }
    Menu menu = menuToGet.get();
    if (pizzaId != null) {
      Optional <Pizza> pizza = pizzaRepository.findById(pizzaId);
      if (!pizza.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "pizzaId " + pizzaId + " not found.")
            .build();
      }
      List <String> pizzaIdToRemove = new ArrayList <>();
      pizzaIdToRemove.add(pizzaId);
      menu.removePizzas(pizzaIdToRemove);
    }
    return ResponseEntity.ok(menuRepository.save(menu));
  }

  @RequestMapping(path = "/{specialId}/add", method = RequestMethod.PUT)
  @ApiOperation(value = "Updates the specials on a menu", tags = {"menu",})
  public ResponseEntity<Menu> addSpecial(
      @ApiParam("Special Id to add to order.") @PathVariable(value = "specialId") String specialId,
      @ApiParam("Menu Id to add to.") @RequestParam(value = "menuId") String menuId) {
    Optional <Menu> menuToGet = menuRepository.findById(menuId);
    if (!menuToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "menuId " + menuId + " not found.")
          .build();
    }
    Menu menu = menuToGet.get();
    if (specialId != null) {
      Optional <Special> special = specialRepository.findById(specialId);
      if (!special.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "specialId " + specialId + " not found.")
            .build();
      }
      Special newSpecial = special.get();
      List <Special> specialsOnMenu = new ArrayList <>();
      specialsOnMenu.add(newSpecial);
      menu.addSpecials(specialsOnMenu);
    }
    return ResponseEntity.ok(menuRepository.save(menu));
  }

  @RequestMapping(path = "/{specialId}/remove", method = RequestMethod.PUT)
  @ApiOperation(value = "Removes a special from a menu", tags = {"menu",})
  public ResponseEntity<Menu> removeSpecial(
      @ApiParam("special Id to remove from order.") @PathVariable(value = "specialId") String specialId,
      @ApiParam("Menu Id to remove from.") @RequestParam(value = "menuId") String menuId) {
    Optional <Menu> menuToGet = menuRepository.findById(menuId);
    if (!menuToGet.isPresent()) {
      return ResponseEntity.notFound().header("message", "menuId " + menuId + " not found.")
          .build();
    }
    Menu menu = menuToGet.get();
    if (specialId != null) {
      Optional <Special> special = specialRepository.findById(specialId);
      if (!special.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "pizzaId " + specialId + " not found.")
            .build();
      }
      List <String> specialIdToRemove = new ArrayList <>();
      specialIdToRemove.add(specialId);
      menu.removePizzas(specialIdToRemove);
    }
    return ResponseEntity.ok(menuRepository.save(menu));
  }
}
