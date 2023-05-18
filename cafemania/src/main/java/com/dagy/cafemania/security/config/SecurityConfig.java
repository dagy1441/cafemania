package com.dagy.cafemania.security.config;

import com.dagy.cafemania.security.jwt.JwtAuthenticationFilter;
import com.dagy.cafemania.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/cafemania/api/v1/users/**",
                        "/cafemania/api/v1/users/signin",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html")
                .permitAll()

//                .requestMatchers("/api/v1/manager/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//
//                .requestMatchers(GET, "/api/v1/manager/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                .requestMatchers(POST, "/api/v1/manager/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                .requestMatchers(PUT, "/api/v1/manager/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                .requestMatchers(DELETE, "/api/v1/manager/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

//                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
//
//                .requestMatchers(GET,"/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
//                .requestMatchers(POST,"/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
//                .requestMatchers(PUT,"/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
//                .requestMatchers(DELETE,"/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name())


                .anyRequest()
                .authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout()
//                .logoutUrl("/api/v1/auth/logout")
////                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler(
//                        (request, response, authentication) -> SecurityContextHolder.clearContext()
  //              );

        return http.build();
    }

}
