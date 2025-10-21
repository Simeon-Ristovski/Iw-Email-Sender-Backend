package com.iwEmailSender.iwemailsender.Configuration;

import com.iwEmailSender.iwemailsender.Model.Role;
import com.iwEmailSender.iwemailsender.Repository.RoleRepository;
import com.iwEmailSender.iwemailsender.Service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private AccountService accountService;



    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        return httpSecurity.build();

//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry->{
//                    registry.requestMatchers(
//                            "/startPageAdmin.html",
//                            "/startPageUser.html",
//                            "/adminPage.html",
//                            "/userPage.html"
//                            ).permitAll();
//            registry.requestMatchers("/api/v1/emailjobs").hasAuthority("ROLE_ADMINISTRATOR");
//            registry.anyRequest().authenticated();
//        })
//                .exceptionHandling(exception ->
//                        exception.accessDeniedHandler(customAccessDeniedHandler()))
////                .exceptionHandling(exception ->
////                      exception.authenticationEntryPoint(customUnauthorized()))   //if you want to throw exception "First log in"
////
////                .formLogin(AbstractAuthenticationFilterConfigurer::disable)   //to disable login form
////                .httpBasic(Customizer.withDefaults())  //to enable login from postman
//                .formLogin(form->form.permitAll().successHandler( new AuthenticationSuccessHandler()))
//                .logout(LogoutConfigurer::permitAll)
//                .sessionManagement(session-> session.maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry())) //disabled login to one acc from two different browsers
////                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login").successHandler(new AuthenticationSuccessHandler()).permitAll()) // enabled login from new login form
//                .build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return  new SessionRegistryImpl();
    }
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(){
        return ((request, response, accessDeniedException) -> {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           response.setContentType("application/json");
           response.getWriter().write("""
                   {
                   "error": "Acces Denied",
                   "message": "You do not have permission to access this resource",
                   }""");
        });
    }
    @Bean
    public AuthenticationEntryPoint customUnauthorized(){
        return ((request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("""
                   {
                   "error": "Acces Denied",
                   "message": "You need to log in first!",
                   }""");
        });
    }
    @Bean
    public UserDetailsService userDetailsService() {
    return accountService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


}
