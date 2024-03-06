package com.kenzie.appserver;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

/**
 * CustomLoginSuccessHandler is a component utilized in Streamline that extends SimpleUrlAuthenticationSuccessHandler,
 * a Spring Security class that handles what happens after a user successfully authenticates.
 * This custom handler redirects the user to their task list page upon successful login.
 *
 * The redirection URL is built using the authenticated user's username (or another unique identifier)
 * to direct the user to a personalized page, enhancing the user experience by taking them directly
 * to their content.
 */

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * Handles the logic to be executed upon successful authentication.
     *
     * @param request The HttpServletRequest, which can be used to read the request to the servlet.
     * @param response The HttpServletResponse, which can be used to modify the response from the servlet.
     * @param authentication The Authentication object, which contains the principal representing the logged-in user.
     * @throws IOException If an input or output exception occurs.
     * @throws ServletException If a servlet-specific exception occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {

        String identifier = authentication.getName();
        String redirectUrl = "/taskList/" + identifier;
        response.sendRedirect(redirectUrl);
        System.out.println("Login success for user: " + identifier);
    }
}


