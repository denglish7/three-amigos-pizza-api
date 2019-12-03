package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.model.customer.Receipt;
import io.swagger.repositories.ReceiptRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/order/receipt")
public class ReceiptController {

  @Autowired
  private ReceiptRepository receiptRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Receipts in the system.", response = Receipt.class, responseContainer = "List", tags = {
      "order",})
  public ResponseEntity<List<Receipt>> getAllReceipts() {
    return ResponseEntity.ok(receiptRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a Receipt", tags = {"order",})
  public ResponseEntity<Receipt> createReceipt(
      @ApiParam("Serving Store Name") @RequestParam(value = "storeName") String storeName,
      @ApiParam("Customer name") @RequestParam(value = "customerName") String customerName,
      @ApiParam("Order ID") @RequestParam(value = "orderId") String orderId,
      @ApiParam("Pizzas Ordered") @RequestParam(value = "pizzas") List<String> pizzas,
      @ApiParam("Specials Applied") @RequestParam(value = "specials") List<String> specialApplied,
      @ApiParam("Last 4 of Card") @RequestParam(value = "lastFourDigitsOfCard")  String paymentMethod,
      @ApiParam("Price Paid") @RequestParam(value = "price") Double pricePaid) {
    Receipt receipt = new Receipt(
            storeName, customerName, orderId, pizzas, specialApplied, paymentMethod, pricePaid);
    return ResponseEntity.ok(receiptRepository.save(receipt));
    }
}
