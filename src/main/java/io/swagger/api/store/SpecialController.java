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
      @ApiParam("Required number of pizzas for special") @RequestParam(value = "requiredNumberPizzas", required = false) Integer requiredNumberPizzas,
      @ApiParam("Id of required size of pizzas for special") @RequestParam(value = "requiredSizeOfPizzas", required = false) String requiredSizeId) {
    Size requiredSize = null;
    if (requiredSizeId != null) {
      Optional<Size> size = sizeRepository.findById(requiredSizeId);
      if (!size.isPresent()) {
        return ResponseEntity.notFound()
            .header("message", "sizeId " + requiredSizeId + " not found.").build();
      }
      requiredSize = size.get();
    }
    Special newSpecial = new Special(specialName, priceRatio, requiredNumberPizzas, requiredSize);
    return ResponseEntity.ok(specialRepository.save(newSpecial));
  }
}