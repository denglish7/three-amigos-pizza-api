package io.swagger.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.model.customer.Receipt;
import io.swagger.repositories.ReceiptRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
}
