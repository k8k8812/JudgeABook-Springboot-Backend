package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filters.JwtRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.inMemoryAuthentication()
            .withUser("user")  
            .password(passwordEncoder().encode("password1")) 
            .roles("USER")
            .and()            
            .withUser("admin")
            .password(passwordEncoder().encode("password2"))
            .roles("ADMIN");
        
        auth.userDetailsService(userDetailsService);
    }
	
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
    }    
    
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/user").hasRole("ADMIN")						// only admin can access all users
            .antMatchers(HttpMethod.GET, "/api/user/current").permitAll()   // show the current user and related info
          //  .antMatchers(HttpMethod.PUT, "/api/user/update").permitAll()	// currently allows any user to update any user account.  fix in the controller
            .antMatchers(HttpMethod.GET, "/api/user/*").hasRole("ADMIN")    // get user by id
			.antMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMIN") // delete user by id
			.antMatchers(HttpMethod.GET, "/api/order").hasRole("ADMIN")		// get all orders
            .antMatchers(HttpMethod.POST, "/api/book/add").hasRole("ADMIN") // add new book 
            .antMatchers(HttpMethod.DELETE, "/api/book/delete").hasRole("ADMIN") // delete book 
			.antMatchers(HttpMethod.POST, "/api/register").permitAll()		// anyone can register an account
			.antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()	// anyone can login with their credentials
           // .antMatchers("/**").hasRole("ADMIN")							// match for any url
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
    
}
