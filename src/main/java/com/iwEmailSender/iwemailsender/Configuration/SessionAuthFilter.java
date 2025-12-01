    package com.iwEmailSender.iwemailsender.Configuration;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    @Component
    public class SessionAuthFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String path = request.getRequestURI();
            if (path.startsWith("/api/v1/auth")||
                    path.startsWith("/api/v1/exceptions") ){
                filterChain.doFilter(request, response);
                return;
            }
            Object user = request.getSession().getAttribute("user");
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("""
                            {
                                "error": "Unauthorized",
                                "message": "Login required"
                            }
                        """);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
