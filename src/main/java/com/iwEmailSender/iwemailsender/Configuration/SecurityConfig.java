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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
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
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()   // сите барања пуштени
//                )
//                .formLogin(AbstractHttpConfigurer::disable) // исклучен login form
//                .httpBasic(AbstractHttpConfigurer::disable); // исклучен basic auth
//        return httpSecurity.build();
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry->{
//            registry.requestMatchers("/api/v1/accounts").permitAll();
            registry.requestMatchers("/api/v1/emailjobs").hasAuthority("ROLE_ADMINISTRATOR");
            registry.anyRequest().authenticated();

        })
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(customAccessDeniedHandler()))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(customUnauthorized()))

                .formLogin(AbstractAuthenticationFilterConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login").successHandler(new AuthenticationSuccessHandler()).permitAll())
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.builder().username("admin").password("9876").roles("ADMINISTRATOR").build();
//        UserDetails normalUser= User.builder().username("gc").password("1234").roles("USER").build();
//        return  new InMemoryUserDetailsManager(admin,normalUser);
//    }
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
