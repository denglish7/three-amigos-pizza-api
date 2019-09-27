package io.swagger.api;

import io.swagger.model.InventoryItem;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApiControllerIntegrationTest {

    @Autowired
    private InventoryApi api;

    @Test
    public void addInventoryTest() throws Exception {
        InventoryItem body = new InventoryItem();
        ResponseEntity<Void> responseEntity = api.addInventory(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void searchInventoryTest() throws Exception {
        String searchString = "searchString_example";
        Integer skip = 56;
        Integer limit = 56;
        ResponseEntity<List<InventoryItem>> responseEntity = api.searchInventory(searchString, skip, limit);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
