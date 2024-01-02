package com.kenzie.appserver.config;

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

    public WebSecurityConfig(UserDetailsService dynamoDBUserDetailsService) {
        this.dynamoDBUserDetailsService = dynamoDBUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return dynamoDBUserDetailsService;
    }
}
