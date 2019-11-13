package io.swagger.api.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.pizza.Size;
import io.swagger.model.specials.Special;
import io.swagger.repositories.SizeRepository;
import io.swagger.repositories.SpecialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("special")
public class SpecialController {

  private static final Double MINIMUM_PRICE_RATIO = 0.0;
  private static final Double MAXIMUM_PRICE_RATIO = 1.0;
  private static final String PRICE_RATIO_OUT_OF_BOUNDS_MESSAGE =
      "Price ratio must be in range [" + MINIMUM_PRICE_RATIO + ", " + MAXIMUM_PRICE_RATIO + "]";
  private static final Integer MINIMUM_NUMBER_REQUIRED_PIZZAS = 1;
  private static final String TOO_FEW_REQUIRED_PIZZAS_MESSAGE =
      "Minimum number of required pizzas for a special is " + MINIMUM_NUMBER_REQUIRED_PIZZAS;

  @Autowired
  private SpecialRepository specialRepository;
  @Autowired
  private SizeRepository sizeRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Special.class, responseContainer = "List", tags = {
      "special",})
  public ResponseEntity<List<Special>> getAllSpecials() {
    return ResponseEntity.ok(specialRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a special", response = Special.class, tags = {"special",})
  public ResponseEntity<Special> createSpecial(
      @ApiParam("Name for new special") @RequestParam(value = "specialName") String specialName,
      @ApiParam("Price ratio for new special") @RequestParam(value = "priceRatio") Double priceRatio,
      @ApiParam("Required number of pizzas for special") @RequestParam(value = "requiredNumberPizzas") Integer requiredNumberPizzas,
      @ApiParam("Id of required size of pizzas for special") @RequestParam(value = "requiredSizeOfPizzas") String requiredSizeId) {
    if (requiredNumberPizzas < MINIMUM_NUMBER_REQUIRED_PIZZAS) {
      return ResponseEntity.badRequest()
          .header("message", TOO_FEW_REQUIRED_PIZZAS_MESSAGE).build();
    }
    if (priceRatio < MINIMUM_PRICE_RATIO || priceRatio > MAXIMUM_PRICE_RATIO) {
      return ResponseEntity.badRequest()
          .header("message", PRICE_RATIO_OUT_OF_BOUNDS_MESSAGE).build();
    }
    Optional<Size> size = sizeRepository.findById(requiredSizeId);
    if (!size.isPresent()) {
      return ResponseEntity.notFound()
          .header("message", "sizeId " + requiredSizeId + " not found.").build();
    }
    Special newSpecial = new Special(specialName, priceRatio, requiredNumberPizzas, size.get());
    return ResponseEntity.ok(specialRepository.save(newSpecial));
  }
}