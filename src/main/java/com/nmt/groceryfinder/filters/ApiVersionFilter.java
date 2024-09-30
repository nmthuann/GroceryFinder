package com.nmt.groceryfinder.filters;

import com.nmt.groceryfinder.common.enums.ApiVersionEnum;
import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiVersionFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException {

        String timestampVersion = request.getHeader("X-Rest-Api-Version");
        try {
            String requestURI = request.getRequestURI();
            if (timestampVersion != null) {
                String newURI;
                if (timestampVersion.equals(ApiVersionEnum.API_VERSION_V1.getTimestamp())) {
                    newURI = "/v1" + requestURI;
                } else if (timestampVersion.equals(ApiVersionEnum.API_VERSION_V2.getTimestamp())) {
                    newURI = "/v2" + requestURI;
                } else {
                    request.setAttribute(
                        "message",
                        AuthExceptionMessages.AUTH_INVALID_API_VERSION.getMessage()
                    );
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            AuthExceptionMessages.AUTH_INVALID_API_VERSION.getMessage()
                    );
                    return;
                }

                if (newURI != null) {
                String finalNewURI = newURI;
                    HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                        @Override
                        public String getRequestURI() {
                            return finalNewURI;
                        }

                        @Override
                        public String getServletPath() {
                            return finalNewURI;
                        }
                    };
                    filterChain.doFilter(wrappedRequest, response);
                }
            } else {
                request.setAttribute(
                        "message",
                        AuthExceptionMessages.AUTH_MISSING_API_VERSION.getMessage()
                );
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        AuthExceptionMessages.AUTH_MISSING_API_VERSION.getMessage()
                );
            }
        }
        catch (ServletException ex) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return requestURI.equals("/") ||
                requestURI.equals("/swagger-ui.html") ||
                requestURI.equals("/swagger-ui/index.html") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith( "/webjar") ||
                requestURI.startsWith("/javainuse-openapi") ||
                requestURI.startsWith( "/configuration-ui") ||
                requestURI.startsWith("/configuration-security") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs")
                ;
    }
}