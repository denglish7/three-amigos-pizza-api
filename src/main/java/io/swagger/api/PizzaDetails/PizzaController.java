package io.swagger.api.PizzaDetails;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.PizzaDetails.Pizza;
import io.swagger.model.PizzaDetails.Size;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/pizza")
public class PizzaController {

  @Autowired
  private PizzaRepository repository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Pizzas in the system.", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> getAllPizzas() {
    return repository.findAll();
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a pizza", tags={ "admins", })
  public Pizza createPizza(@ApiParam("Pizza information") @Valid @RequestBody Pizza pizza) {
    return repository.save(pizza);
  }

  @RequestMapping(path = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizzas by name", response = Pizza.class, responseContainer = "List", tags = {"developers",})
  public List<Pizza> searchPizzasByName(@ApiParam("Name of pizza to get.") @PathVariable("name") String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").all(name));
    return mongoTemplate.find(query, Pizza.class);
    //return repository.findAllByName(name);
  }

  @RequestMapping(path = "/id={_id}", method = RequestMethod.GET, produces = {"application/json"})
  @ApiOperation(value = "Searches for pizza by _id", response = Pizza.class, tags = {"developers",})
  public ResponseEntity searchPizzasById(@ApiParam("_id of pizza to get.") @PathVariable("_id") String _id) {
    Optional pizza = repository.findById(_id);
    if (!pizza.isPresent()) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return ResponseEntity.ok(pizza);
    }
  }

  @RequestMapping(path = "/id={_id}/{size}", method = RequestMethod.POST)
  @ApiOperation(value = "Updates size of a pizza given its _id", tags = {"admins",})
  public ResponseEntity updatePizzaSizeById(@ApiParam("_id of pizza to update.") @PathVariable("_id") String _id, @ApiParam("Name of size to set pizza to.") @PathVariable("size") String sizeName) {
    //Optional pizza = repository.findById(_id);
    Pizza pizza = mongoTemplate.findById(_id, Pizza.class);
    if (pizza == null) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
//    Query sizeQuery = new Query();
//    sizeQuery.addCriteria(Criteria.where("name").is(sizeName));
//    Size size = mongoTemplate.findOne(sizeQuery, Size.class);
    Size size = mongoTemplate.findById(sizeName, Size.class);
    if (pizza == null || size == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      pizza.setSize(size);
      return ResponseEntity.ok(mongoTemplate.save(pizza));
    }
  }
}
