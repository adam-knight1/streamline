
package com.kenzie.appserver.config;

import com.kenzie.appserver.AjaxLoginProcessingFilter;
import com.kenzie.appserver.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * WebSecurityConfig configures the security settings for the web application.
 * It extends WebSecurityConfigurerAdapter to provide custom security configurations.
 *
 * The configuration includes setting up a custom user details service for authentication,
 * specifying password encoding schemes, and defining security filters such as CORS and CSRF protection.
 * It also configures access permissions for various endpoints within the application.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService dynamoDBUserDetailsService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    /**
     * Constructor for WebSecurityConfig.
     *
     * @param dynamoDBUserDetailsService A custom UserDetailsService implementation
     *                                   for loading user-specific data during authentication.
     */
    public WebSecurityConfig(UserDetailsService dynamoDBUserDetailsService) {
        this.dynamoDBUserDetailsService = dynamoDBUserDetailsService;
    }

    /**
     * Configures the HttpSecurity settings for the application.
     *
     * Disables CSRF protection (for API-centric applications where CSRF is not a risk).
     * Enables CORS with configuration.
     * Specifies URL patterns for public access and those requiring authentication.
     * Sets up a custom login filter and disables form login.
     * Configures logout to be permitted for all users.
     *
     * @param http The HttpSecurity to be modified.
     * @throws Exception if an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/css/**", "/error" ,  "/api/**" , "/pages/**", "/user/create", "/user/**", "/index", "/swagger-ui/**", "/v2/api-docs/", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/v3/api-docs/swagger-config", "/webjars/**").permitAll()
                .antMatchers("/task/user/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .logout().permitAll();
    }

    /**
     * Creates an AjaxLoginProcessingFilter bean.
     * This filter processes authentication requests for the custom AJAX-based login endpoint.
     *
     * @return AjaxLoginProcessingFilter configured with the login endpoint and authentication manager.
     * @throws Exception if an error occurs in creating the filter.
     */
    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        return new AjaxLoginProcessingFilter("/login", authenticationManagerBean());
    }

    /**
     * Declares the customUserDetailsService bean.
     *
     * @return An instance of UserDetailsService for authentication.
     */
    @Bean
    public UserDetailsService customUserDetailsService() {
        return dynamoDBUserDetailsService;
    }

    /**
     * Configures the authentication provider.
     *
     * @return DaoAuthenticationProvider configured with user details service and password encoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(dynamoDBUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures the password encoder.
     *
     * @return PasswordEncoder instance for encoding and decoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //This is a simple placeholder and will be updated
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Configures the CORS filter.
     *
     * Sets up allowed origins, headers, and methods for cross-origin requests.
     *
     * @return CorsFilter instance to handle CORS configuration.
     */
    @Bean
    public CorsFilter corsFilter() {
        //I added this cross-origin resource because of the separation of packages for this project.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}


