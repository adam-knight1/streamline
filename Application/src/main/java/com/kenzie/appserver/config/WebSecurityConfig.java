
package com.kenzie.appserver.config;

import com.kenzie.appserver.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService dynamoDBUserDetailsService;
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    public WebSecurityConfig(UserDetailsService dynamoDBUserDetailsService) {
        this.dynamoDBUserDetailsService = dynamoDBUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/login", "/login.html", "/user/create" , "/user/**", "/user.html", "/index").permitAll()
                .anyRequest().authenticated()
                .and()

                // login
                .formLogin()
                .loginPage("/login.html")
                .successHandler(customLoginSuccessHandler)
                .permitAll()
                .and()

                // logout
                .logout()
                .permitAll();

    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return dynamoDBUserDetailsService;
    }
}
