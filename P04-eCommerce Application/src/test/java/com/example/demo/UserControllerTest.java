package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void createUser_Success() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("test");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("password");

        final ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
    }

    @Test
    public void createUser_Fail() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("test");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("wrongpassword");

        final ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findByUserName_Success() throws Exception {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(user);

        final ResponseEntity<User> response = userController.findByUserName("test");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findByUserName_Fail() throws Exception {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(user);

        final ResponseEntity<User> response = userController.findByUserName("testwrong");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findById_Success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("test");

        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(Long.valueOf(1))).thenReturn(optionalUser);

        final ResponseEntity<User> response = userController.findById(Long.valueOf(1));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", response.getBody().getUsername());
    }
}
