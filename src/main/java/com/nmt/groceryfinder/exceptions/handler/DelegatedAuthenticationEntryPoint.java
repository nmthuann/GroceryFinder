package com.nmt.groceryfinder.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Custom {@link AuthenticationEntryPoint} that delegates authentication exception handling
 * to a {@link HandlerExceptionResolver}.
 *
 * This component uses the provided {@link HandlerExceptionResolver} to generate the appropriate
 * HTTP response when an authentication error occurs.
 */
@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;


    @Autowired
    public DelegatedAuthenticationEntryPoint(
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) {
        this.resolver = resolver;
    }

    /**
     * Handles authentication errors by delegating the exception resolution
     * to the `HandlerExceptionResolver`, generating the appropriate error response.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param authException the authentication error
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        resolver.resolveException(request, response, null, authException);
    }
}