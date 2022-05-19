package com.cognixia.jump.ontrollertest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cognixia.jump.controller.UserController;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
//@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;

    @Autowired
    private ObjectMapper objectMapper;
    
   
    private List<User> userList;
    
	
	
    void setUp() {
//        this.userList = new ArrayList<>();
//        this.userList.add(new User(1L, "jessica", "jessica", true, Role.ROLE_USER));
//        this.userList.add(new User(1L, "rachel", "rachel", true, Role.ROLE_USER));
//        this.userList.add(new User(1L, "nathan", "nathan", true, Role.ROLE_USER));
//        this.userList.add(new User(1L, "james", "james", true, Role.ROLE_USER));
//        this.userList.add(new User(1L, "harper", "harper", true, Role.ROLE_ADMIN));

    }
    
    @Test 
    public void getAllusersAPI() throws Exception {
    	mockMvc.perform( 
    			MockMvcRequestBuilders
    			.get("/api/user")
    			.accept(MediaType.APPLICATION_JSON))
    	
		        .andDo(print())	
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.user.user[*].username").isNotEmpty());
    			
    }
//	
//    @Test
//    void shouldFetchAllUsers() throws Exception {
//
//        given(userRepo.findAll()).willReturn(userList);
//
//        this.mockMvc.perform(get("/api/user"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(userList.size())));
//    }
//    
//    @Test
//    void shouldCreateNewuser() throws Exception {
//        given(userRepo.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));
//
//        User user = new User(-1L, "bill", "bill", true, Role.ROLE_USER);
//        
//        this.mockMvc.perform(post("/api/add/user")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.username", is(user.getUsername())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.enabled", is(user.isEnabled())))
//                .andExpect(jsonPath("$.role", is(user.getRole())));
//        ;
//    }
	

}
