package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private Item item;
    private Long id = Long.valueOf(1);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);

        item = new Item();
        item.setDescription("Test item");
        item.setId(Long.valueOf(1));
        item.setName("Test item");
        item.setPrice(BigDecimal.valueOf(1234));
    }

    @Test
    public void getItems_Success() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findAll()).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Item> actual = response.getBody();
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    public void getItemById_Success() throws Exception {
        Optional<Item> optionalItem = Optional.of(item);

        when(itemRepository.findById(id)).thenReturn(optionalItem);

        final ResponseEntity<Item> response = itemController.getItemById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Item item = response.getBody();
        assertNotNull(item);
        assertEquals(Long.valueOf(1), item.getId());
        assertEquals("Test item", item.getName());
        assertNotNull(item.hashCode());
        assertTrue(item.equals(item));
    }

    @Test
    public void getItemsByName_Success() throws Exception {
        Item item2 = new Item();

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        when(itemRepository.findByName("Test item")).thenReturn(items);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName("Test item");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test item", response.getBody().get(0).getName());
        assertFalse(item.equals(null));
        assertFalse(item.equals(response));
        assertFalse(item.equals(item2));
    }

    @Test
    public void getItemsByName_Fail() throws Exception {
        when(itemRepository.findByName("Test item")).thenReturn(null);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName("Test item");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
