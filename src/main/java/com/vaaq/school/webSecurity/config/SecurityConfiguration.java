package com.vaaq.school.webSecurity.config;


import com.vaaq.school.webSecurity.user.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    // URL patterns to be allowed without authentication.
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/userRegister/**",
            "/api/v1/auth/authenticate/**",
            //"/api/v1/auth/promote-to-admin",
            "/course/getCourses"
            };

    // JWT authentication filter.
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Custom authentication provider.
    private final AuthenticationProvider authenticationProvider;

    // Logout handler for handling user logout.
    private final LogoutHandler logoutHandler;


    // Configures the security filter chain.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Define authorization rules for different endpoints.
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(GET, "/course/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(POST, "/student/save/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(POST, "/course/save/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(POST, "/promote-to-admin/").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(PUT, "/student/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/student/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/course/**").hasAnyRole(Role.ADMIN.name())

                                .anyRequest()
                                .authenticated()
                )
                // Configure session management to be stateless.
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // Set the custom authentication provider.
                .authenticationProvider(authenticationProvider)
                // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure logout handling.
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                // Configure exception handling for access denied scenarios.
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")))
        ;

        // Build and return the configured security filter chain.
        return http.build();
    }
}
