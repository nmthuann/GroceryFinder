package com.nmt.groceryfinder.configs;

import com.nmt.groceryfinder.exceptions.handler.DelegatedAuthenticationEntryPoint;
import com.nmt.groceryfinder.filters.ApiVersionFilter;
import com.nmt.groceryfinder.filters.AuthMiddlewareFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/12/2024
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final DelegatedAuthenticationEntryPoint authEntryPoint;
    private final AuthenticationProvider authenticationProvider;
    private final ApiVersionFilter apiVersionFilter;
    private final AuthMiddlewareFilter authMiddlewareFilter;

    @Autowired
    public SpringSecurityConfig (
            DelegatedAuthenticationEntryPoint authEntryPoint,
            AuthenticationProvider authenticationProvider,
            ApiVersionFilter apiVersionFilter,
            AuthMiddlewareFilter authMiddlewareFilter
    ) {
        this.authEntryPoint = authEntryPoint;
        this.authenticationProvider = authenticationProvider;
        this.authMiddlewareFilter = authMiddlewareFilter;
        this.apiVersionFilter = apiVersionFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjar/**",
                                "/javainuse-openapi/**",
                                "/configuration-ui",
                                "/configuration-security",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET, "/",
                                "/v1/products/**",
                                "/v1/brands/**",
                                "/v1/categories/**",
                                "/v1/suppliers/**",
                                "/v1/skus/**",
                                "/v1/redis/**",
                                "/v1/health-check/**",
                                "/v1/actuator/**",
                                "/v1/inventories/**"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,"/redis/**",
                                "/v1/auth/**"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/v1/products/**",
                                "/v1/brands/**",
                                "/v1/categories/**",
                                "/v1/suppliers/**",
                                "/v1/inventories/**",
                                "/v1/skus/**"
                        ).hasRole("ADMIN")
                        .requestMatchers( HttpMethod.PUT,"/v1/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/v1/orders/**"
                        ).hasAnyRole("ADMIN", "USER")
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authMiddlewareFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiVersionFilter, AuthMiddlewareFilter.class)
                .logout((logout) -> logout.logoutUrl("/auth/user/logout/uri"))
                .exceptionHandling(
                        (exceptionHandling) ->
                                exceptionHandling

                                        .authenticationEntryPoint(authEntryPoint)
                );
        return httpSecurity.build();
    }
}
