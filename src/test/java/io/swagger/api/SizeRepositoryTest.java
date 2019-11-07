package io.swagger.api;

import io.swagger.repositories.SizeRepository;
import io.swagger.model.pizza.Size;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/application-test.properties")
@SpringBootTest
public class SizeRepositoryTest {

    @Autowired
    private SizeRepository sizeRepository;

    @Before
    public void setUp() {
        sizeRepository.deleteAll();
    }

    @After
    public void tearDown() {
        sizeRepository.deleteAll();
    }

    @Test
    public void addSizeToRepositoryTest() throws Exception {
        assertEquals(0, sizeRepository.count());
        Size size = new Size("small", 1.00, 20, 30, 15);
        sizeRepository.insert(size);
        assertEquals(1, sizeRepository.count());
    }

}
