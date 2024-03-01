package com.kenzie.appserver;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

/**
 * CustomLoginSuccessHandler is a component that extends SimpleUrlAuthenticationSuccessHandler,
 * a Spring Security class that handles what happens after a user successfully authenticates.
 * This custom handler redirects the user to their task list page upon successful login.
 *
 * The redirection URL is built using the authenticated user's username (or another unique identifier)
 * to direct the user to a personalized page, enhancing the user experience by taking them directly
 * to their content.
 */

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {

        String identifier = authentication.getName();
        String redirectUrl = "/taskList/" + identifier;
        response.sendRedirect(redirectUrl);
        System.out.println("Login success for user: " + identifier);
    }
}


