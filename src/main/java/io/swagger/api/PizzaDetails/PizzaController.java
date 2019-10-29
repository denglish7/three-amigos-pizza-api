package io.swagger.api.PizzaDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.PizzaDetails.Pizza;
import io.swagger.model.PizzaDetails.Size;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/pizza")
public class PizzaController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> getAllPizzas() {
    return mongoTemplate.findAll(Pizza.class);
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
  public Pizza createPizza(@ApiParam("Pizza information") @Valid @RequestBody Pizza pizza) {
    return mongoTemplate.save(pizza);
  }

  @RequestMapping(path = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizzas by name", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> searchPizzasByName(@ApiParam("Name of pizza to get.") @PathVariable("name") String name) {
    Query query = new Query(Criteria.where("name").all(name));
    return mongoTemplate.find(query, Pizza.class);
  }

  @RequestMapping(path = "/id={_id}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizza by _id", response = Pizza.class, tags = {"developers",})
  public ResponseEntity searchPizzasById(@ApiParam("_id of pizza to get.") @PathVariable("_id") String _id) {
    Pizza pizza = mongoTemplate.findById(_id, Pizza.class);
    if (pizza == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return ResponseEntity.ok(pizza);
    }
  }

  @RequestMapping(path = "/id={_id}", method = RequestMethod.PUT)
  @ApiOperation(value = "Changes the size of a pizza", tags = {"admins",})
  public ResponseEntity updatePizzaSizeById(@PathVariable("_id") String _id, @RequestParam(value="sizeName") String sizeName) {
    Pizza pizza = mongoTemplate.findById(_id, Pizza.class);
    Size size = mongoTemplate.findById(sizeName, Size.class);
    if (pizza == null || size == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      pizza.setSize(size);
      return ResponseEntity.ok(mongoTemplate.save(pizza));
    }
  }
}
