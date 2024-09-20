package com.nmt.groceryfinder.filters;


import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.utils.JwtServiceUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class AuthMiddlewareFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtServiceUtil jwtServiceUtil;

    @Autowired
    public AuthMiddlewareFilter(
            UserDetailsService userDetailsService,
            JwtServiceUtil jwtServiceUtil
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtServiceUtil = jwtServiceUtil;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        /*
         * recommend in YouTube:
         * - Place the following code inside a try-catch block.
         * - Create an API endpoint list that allows access without requiring a token.
         */
        String requestURI = request.getRequestURI();
        if (authHeader == null || authHeader.isBlank()) {
            if (requestURI.startsWith("/v1/orders")) {
                filterChain.doFilter(request, response);

            }
            request.setAttribute("message", AuthExceptionMessages.AUTH_MISSING_INFORMATION.getMessage());
            filterChain.doFilter(request, response);
            return;
        }




        try {
            final String token = authHeader.substring(7);
            final String email = this.jwtServiceUtil.extractUsername(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtServiceUtil.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            throw new ServletException(AuthExceptionMessages.AUTH_ERROR.getMessage(), ex);
        }
    }
}