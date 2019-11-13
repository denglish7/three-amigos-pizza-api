package io.swagger.repositories;

import io.swagger.model.customer.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceiptRepository extends MongoRepository<Receipt, String> {

}
