package io.swagger.api.customer;

import io.swagger.annotations.*;
import io.swagger.model.customer.Receipt;
import io.swagger.repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

  @Autowired
  private ReceiptRepository receiptRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Receipts in the system.", response = Receipt.class, responseContainer = "List", tags = {
      "user",})
  public ResponseEntity<List<Receipt>> getAllReceipts() {
    return ResponseEntity.ok(receiptRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a receipt", tags = {"user",})
  public ResponseEntity<Receipt> createReceipt(
      @ApiParam("Order information for a new receipt") @Valid @RequestBody String storeName,
      @ApiParam("Customer's name.") @RequestParam(value = "customer name") String customerName,
      @ApiParam("Order Id.") @RequestParam(value = "order id") String orderId,
      @ApiParam("Pizzas in order.") @RequestParam(value = "pizzas ordered") List<String> pizzas,
      @ApiParam("Promotion applied to order.") @RequestParam(value = "promotion used") List <String> specialApplied,
      @ApiParam("Last 4 digits of the credit card charged.") @RequestParam(value = "last 4 digits of card") String paymentMethod,
      @ApiParam("Price paid.") @RequestParam(value = "price") Double pricePaid) {
    Receipt receipt = new Receipt(
        storeName,
        customerName,
        orderId,
        pizzas,
        specialApplied,
        paymentMethod,
        pricePaid
    );
    return ResponseEntity.ok(receiptRepository.save(receipt));
  }
}
