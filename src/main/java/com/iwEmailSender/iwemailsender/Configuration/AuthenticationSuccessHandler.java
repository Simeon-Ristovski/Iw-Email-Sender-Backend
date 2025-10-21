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
        String redirectUrl;
        if(isAdmin){
//            setDefaultTargetUrl("/resources/HtmlViews/startPageAdmin.html");
            redirectUrl= "/api/v1/accounts";
        }else {
//            setDefaultTargetUrl("/resources/HtmlViews/startPageUser.html");
            redirectUrl="/api/v1/emailjobs";
        }

//        super.onAuthenticationSuccess(request, response, authentication);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"redirectUrl\":\""+ redirectUrl + "\"}");
    }
}
