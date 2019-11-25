package io.swagger.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.api.pizza.PizzaController;
import io.swagger.api.store.StoreController;
import io.swagger.model.pizza.Crust;
import io.swagger.model.pizza.Pizza;
import io.swagger.model.pizza.Size;
import io.swagger.model.pizza.Topping;
import io.swagger.model.specials.Special;
import io.swagger.model.store.Store;
import io.swagger.repositories.CrustRepository;
import io.swagger.repositories.CustomerRepository;
import io.swagger.repositories.OrderRepository;
import io.swagger.repositories.PizzaRepository;
import io.swagger.repositories.ReceiptRepository;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.SpecialRepository;
import io.swagger.repositories.StoreRepository;
import io.swagger.repositories.ToppingRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/admin")
public class AdminController {

  private static final String adminPass = "3amigos";

  @Autowired
  private CrustRepository crustRepository;
  @Autowired
  private ToppingRepository toppingRepository;
  @Autowired
  private SizeRepository sizeRepository;
  @Autowired
  private PizzaRepository pizzaRepository;
  @Autowired
  private SpecialRepository specialRepository;

  @Autowired
  private StoreRepository storeRepository;
  @Autowired
  private StoreController storeController;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ReceiptRepository receiptRepository;

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Resets database to default values", tags = {"admin",})
  public ResponseEntity resetAll() {
    deleteAll();
    addThreeCrustsSevenToppingsThreeSizesFivePizzasTwoSpecials();
    return ResponseEntity.ok().build();
  }

  private void addThreeCrustsSevenToppingsThreeSizesFivePizzasTwoSpecials() {
    Crust crust1 = new Crust(5.0, false, "regular");
    Crust crust2 = new Crust(6.5, true, "gluten free");
    Crust crust3 = new Crust(5.5, false, "thin");
    crustRepository.save(crust1);
    crustRepository.save(crust2);
    crustRepository.save(crust3);

    Topping topping1 = new Topping("mushroom", 1.5);
    Topping topping2 = new Topping("onion", 1.0);
    Topping topping3 = new Topping("pepper", 1.0);
    Topping topping4 = new Topping("sausage", 2.5);
    Topping topping5 = new Topping("pineapple", 2.0);
    Topping topping6 = new Topping("ham", 2.0);
    Topping topping7 = new Topping("pepperoni", 1.5);
    toppingRepository.save(topping1);
    toppingRepository.save(topping2);
    toppingRepository.save(topping3);
    toppingRepository.save(topping4);
    toppingRepository.save(topping5);
    toppingRepository.save(topping6);
    toppingRepository.save(topping7);

    Size size1 = new Size("small", 1.0);
    Size size2 = new Size("medium", 1.5);
    Size size3 = new Size("large", 2.0);
    sizeRepository.save(size1);
    sizeRepository.save(size2);
    sizeRepository.save(size3);

    Pizza pizza1 = new Pizza("supreme", crust1,
        Arrays.asList(topping1, topping2, topping3, topping4));
    Pizza pizza2 = new Pizza("hawaiian", crust3, Arrays.asList(topping5, topping6));
    Pizza pizza3 = new Pizza("veg", crust2, Arrays.asList(topping1, topping2, topping3));
    Pizza pizza4 = new Pizza("pepperoni", crust1, Arrays.asList(topping7));
    Pizza pizza5 = new Pizza("meat lovers", crust1, Arrays.asList(topping4, topping6, topping7));
    pizzaRepository.save(pizza1);
    pizzaRepository.save(pizza2);
    pizzaRepository.save(pizza3);
    pizzaRepository.save(pizza4);
    pizzaRepository.save(pizza5);

    Special special1 = new Special("five five five deal", 0.5, 3, size2);
    Special special2 = new Special("2 large, 25% off", 0.75, 2, size3);
    specialRepository.save(special1);
    specialRepository.save(special2);

    ResponseEntity<Store> store1Response = storeController
        .createStore("Cheezus Crust", "123 Broadway Ave.", null);
    Store store1 = store1Response.getBody();
    storeController.addToStoreMenu(store1.get_id(), Arrays
        .asList(pizza1.get_id(), pizza2.get_id(), pizza3.get_id(), pizza4.get_id(),
            pizza5.get_id()), Arrays.asList(special1.get_id(), special2.get_id()));
    storeController.createStore("Uptown Girl", "MLK and Cherry", store1.get_id());
  }

  private void deleteAll() {
    crustRepository.deleteAll();
    toppingRepository.deleteAll();
    sizeRepository.deleteAll();
    pizzaRepository.deleteAll();
    specialRepository.deleteAll();
    storeRepository.deleteAll();
    orderRepository.deleteAll();
    customerRepository.deleteAll();
    receiptRepository.deleteAll();
  }
}
