//package io.swagger.api.customer;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import io.swagger.model.customer.Address;
//import io.swagger.repositories.AddressRepository;
//import java.util.Optional;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api
//@RestController
//@RequestMapping
//public class AddressController {
//
//  @Autowired
//  private AddressRepository addressRepository;
//
//  @RequestMapping(path = "/", method = RequestMethod.POST)
//  @ApiOperation(value = "Creates a address", tags = {"user",})
//  public ResponseEntity<Address> createAddress(
//      @ApiParam("Address information for a new address") @Valid @RequestBody Address address) {
//    return ResponseEntity.ok(addressRepository.save(address));
//  }
//
//  @RequestMapping(path = "/{addressId}", produces = {
//      "application/json"}, method = RequestMethod.GET)
//  @ApiOperation(value = "Searches for a address by id", response = Address.class, tags = {
//      "user",})
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = "search results matching criteria", response = Address.class),
//      @ApiResponse(code = 404, message = "address not found")})
//  public ResponseEntity<Address> findById(
//      @ApiParam("Id of crust to get.") @PathVariable("addressId") String addressId) {
//    Optional<Address> address = addressRepository.findById(addressId);
//    if (address.isPresent()) {
//      return ResponseEntity.of(address);
//    }
//    return ResponseEntity.notFound().header("message", "addressId " + addressId + " not found.")
//        .build();
//  }
//
//}
