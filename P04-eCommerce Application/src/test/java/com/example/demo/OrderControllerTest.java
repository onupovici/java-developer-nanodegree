package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private User user;
    private Item item;
    private List<Item> items;
    private Cart cart;

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

        user = new User();
        user.setUsername("test");

        item = new Item();
        item.setDescription("Test item");
        item.setId(Long.valueOf(1));
        item.setName("Test item");
        item.setPrice(BigDecimal.valueOf(1234));

        items = new ArrayList<>();
        items.add(item);

        cart = new Cart();
        cart.setId(Long.valueOf(1));
        cart.setItems(items);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(1234));
        user.setCart(cart);
    }

    @Test
    public void submit_Success() throws Exception {
        when(userRepository.findByUsername("test")).thenReturn(user);

        final ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserOrder actual = response.getBody();
        assertNotNull(actual);
        assertEquals(BigDecimal.valueOf(1234), actual.getTotal());
        assertEquals(1, actual.getItems().size());
    }

    @Test
    public void submit_Fail() throws Exception {
        when(userRepository.findByUsername("test")).thenReturn(null);

        final ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getOrdersForUser_Success() throws Exception {
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setTotal(BigDecimal.valueOf(1234));
        userOrder.setId(Long.valueOf(1));

        List<UserOrder> userOrders = new ArrayList<>();
        userOrders.add(userOrder);

        when(userRepository.findByUsername("test")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(userOrders);

        final ResponseEntity<UserOrder> response = orderController.submit("test");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserOrder actual = response.getBody();
        assertNotNull(actual);
        assertEquals(BigDecimal.valueOf(1234), actual.getTotal());
        assertEquals("test", actual.getUser().getUsername());
    }

    @Test
    public void getOrdersForUser_Fail() throws Exception {
        when(userRepository.findByUsername("test")).thenReturn(null);

        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
