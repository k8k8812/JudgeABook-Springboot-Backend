package com.cognixia.jump.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;
    
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;
	
	
    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "jessica", "jessica", true, Role.ROLE_USER));
        this.userList.add(new User(1L, "rachel", "rachel", true, Role.ROLE_USER));
        this.userList.add(new User(1L, "nathan", "nathan", true, Role.ROLE_USER));
        this.userList.add(new User(1L, "james", "james", true, Role.ROLE_USER));
        this.userList.add(new User(1L, "harper", "harper", true, Role.ROLE_ADMIN));

    }
    
    
    @Test
    void shouldFetchAllUsers() throws Exception {

        given(userRepo.findAll()).willReturn(userList);

        this.mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }
    
    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long userId = -1L;
        final User user = new User(-1L, "peter", "peter", true, Role.ROLE_USER);

        given(userRepo.findById(userId).get()).willReturn(user);

        this.mockMvc.perform(get("/api/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.enabled", is(user.isEnabled())))
                .andExpect(jsonPath("$.role", is(user.getRole())));
    }
    
    
    
    @Test
    void shouldCreateNewuser() throws Exception {
        given(userRepo.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User(-1L, "peter", "peter", true, Role.ROLE_USER);
        
        this.mockMvc.perform(post("/api/add/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.enabled", is(user.isEnabled())))
                .andExpect(jsonPath("$.role", is(user.getRole())));
        ;
    }

    @Test
    void shouldUpdateuser() throws Exception {
        Long userId = 1L;
        User user = new User(1L, "peter", "peter", true, Role.ROLE_USER);
        
        given(userRepo.findById(userId).get()).willReturn(user);
        given(userService.updateUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/api/update/user")
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.enabled", is(user.isEnabled())))
                .andExpect(jsonPath("$.role", is(user.getRole())));
                ;

    }

    
    // need to change return type of delete method in order for test to work
//    @Test
//    void shouldDeleteuser() throws Exception {
//    	Long userId = 1L;
//    	User user = new User(1L, "peter", "peter", true, Role.ROLE_USER);
//    	
//        given(userRepo.findById(userId).get()).willReturn(user);
//        given(userRepo.deleteById(user.getId())).willAnswer((invocation) -> invocation.getArgument(0));
//
//        this.mockMvc.perform(delete("/api/delete/user")
//        		.contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is(user.getUsername())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.enabled", is(user.isEnabled())))
//                .andExpect(jsonPath("$.role", is(user.getRole())));
//                ;
//
//    }

    
}
