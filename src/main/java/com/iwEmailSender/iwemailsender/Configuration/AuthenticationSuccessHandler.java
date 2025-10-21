package com.iwEmailSender.iwemailsender.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority ->
                        "ROLE_ADMINISTRATOR".equals(grantedAuthority.getAuthority()));

//        String redirectUrl;       // if we want to test from postman and we want response there
//        if(isAdmin){
//
//            redirectUrl= "/api/v1/accounts";
//        }else {
//            redirectUrl="/api/v1/emailjobs";
//        }
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"redirectUrl\":\""+ redirectUrl + "\"}");

        if(isAdmin){
            setDefaultTargetUrl("/startPageAdmin.html");
        } else {
            setDefaultTargetUrl("/startPageUser.html");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
