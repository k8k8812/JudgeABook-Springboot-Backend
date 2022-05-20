package com.cognixia.jump;


import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetails;
import com.cognixia.jump.util.JwtUtil;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class JudgeABookApplicationTests {
	

	@Autowired
	private static MockMvc mockMvc;
	 
//	@Mock
//	private static JwtRequestFilter filter;
	
//	@Autowired
//	private UserController controller; 
	
	 @MockBean
	 private static UserRepository userRepo;
	
	 private static String token;
	 
	 @Mock
	 private static JwtUtil jwt;
	 
	 @Mock
	 private static User user;
	 
	 @Mock
	 private static MyUserDetails myUserDetails;
	 
	 @Autowired
	   private WebApplicationContext context;
	 
	 private static List<User> userList;
	 
	 @BeforeAll
	 static void init() {
		 
		 System.out.println(">>>>>>>>>>>>>>>>>>>>> start test .... ");
		 userList = new ArrayList<User>();
		
		userList.add(new User(1L, "admin", "password", true, Role.ROLE_ADMIN));
		userList.add(new User(2L, "Sarah", "sarah", true,Role.ROLE_ADMIN));
		
		myUserDetails = new MyUserDetails(userList.get(0));
		jwt = new JwtUtil();
		token = jwt.generateTokens(myUserDetails);	
		
	 }


//	 @Test
//	void contextLoads() {
//		
//	}
	 
//	 @Test 
//	 void repoShouldNotBeNull() throws Exception {
//		 
//		given(userRepo.findAll()).willReturn(userList);
//
//		assertNotNull(userRepo);
//	 }


	 
	@WithMockUser(roles = "ADMIN")
	@Test 
    void getAllusersAPI() throws Exception {
    	
//    	given(userRepo.findAll()).willReturn(userList);
    	
    	mockMvc = MockMvcBuilders
    	          .webAppContextSetup(context)
    	          .apply(springSecurity())
    	          .build();
    	
    	mockMvc.perform( 
    			
    			MockMvcRequestBuilders
    			.get("/api/user")
    			.accept(MediaType.ALL)
    			.header("Authorization", "Bearer " + token ))
    	
    			.andDo(print())
		    	.andExpect(status().isOk());
//		    	.andExpect(MockMvcResultMatchers.jsonPath("$.*").isNotEmpty());
		      		
    
    }
	 @Test 
	 void tokenShouldNotBeNull() {  //pass
		 assertNotNull(token);
	 }
	 
		

}
