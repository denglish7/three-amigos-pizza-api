package io.swagger.repositories;

import io.swagger.model.store.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store, String> {

}
